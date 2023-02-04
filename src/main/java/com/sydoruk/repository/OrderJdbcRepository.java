package com.sydoruk.repository;

import com.sydoruk.model.*;


import java.sql.*;

import java.sql.Date;
import java.util.*;

public class OrderJdbcRepository implements InterfaceRepository<Order> {

    private static OrderJdbcRepository instance;
    private final Connection connection;
    private ResultSet resultSet;
    private Statement statement;
    private Order order;
    private List <Order> orders;
    private final Map< Object, List<Car>> mapOrder;


    private static final String[] createTablesSQL = {"CREATE TABLE IF NOT EXISTS engines (idEngine VARCHAR(255) " +
            "NOT NULL PRIMARY KEY, typeEngine VARCHAR(255) NOT NULL, power INTEGER NOT NULL)", "CREATE TABLE " +
            "IF NOT EXISTS orders (idOrder VARCHAR(255) NOT NULL PRIMARY KEY, time DATE NOT NULL)",
            "CREATE TABLE IF NOT EXISTS cars (idCar VARCHAR(255) NOT NULL PRIMARY KEY, manufacturer VARCHAR(255) NOT NULL, " +
            "color VARCHAR(255) NOT NULL, count INTEGER NOT NULL, price INTEGER NOT NULL, typeCar VARCHAR(255) NOT NULL, " +
            "engineID VARCHAR(255) NOT NULL, orderID VARCHAR(255), FOREIGN KEY (engineID) REFERENCES engines (idEngine) " +
            "ON DELETE CASCADE, FOREIGN KEY (orderID) REFERENCES orders (idOrder) ON DELETE CASCADE)", "CREATE TABLE " +
            "IF NOT EXISTS passengerCars (idCar VARCHAR(255) NOT NULL PRIMARY KEY, passengerCount INTEGER NOT NULL, " +
            "FOREIGN KEY (idCar) REFERENCES cars (idCar) ON DELETE CASCADE)", "CREATE TABLE IF NOT EXISTS truckCars " +
            "(idCar VARCHAR(255) NOT NULL PRIMARY KEY, loadCapacity INTEGER NOT NULL, FOREIGN KEY (idCar) REFERENCES " +
            "cars (idCar) ON DELETE CASCADE)" };

    private OrderJdbcRepository(Connection connection){
        this.connection = connection;
        mapOrder = new HashMap<Object, List<Car>>();
        createTables();
    }

    public static OrderJdbcRepository getInstance(Connection connection) {
        if (instance == null) {
            instance = new OrderJdbcRepository(connection);
        }
        return instance;
    }

    private void createTables() {
        try {
            connection.setSavepoint();
            connection.setAutoCommit(false);
            for (String createTable : createTablesSQL) {
                try (PreparedStatement createTables = connection.prepareStatement(createTable)) {
                    createTables.execute();
                }
            }
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            e.printStackTrace();
        }
    }

    @Override
    public void save(final Order order) {
        try {
            connection.setAutoCommit(false);
            String insertOrder = "INSERT INTO orders (idOrder, time ) VALUES (? ,?)";

            try (PreparedStatement saveOrder = connection.prepareStatement(insertOrder)) {
                saveOrder.setString(1, order.getId());
                saveOrder.setDate(2, order.getDate());
                saveOrder.executeUpdate();
                final CarJdbcRepository carJdbcRepository = CarJdbcRepository.getInstance(connection);
                final EngineJdbcRepository engineJdbcRepository = EngineJdbcRepository.getInstance(connection);
                for (Car car : order.getCars()) {
                    engineJdbcRepository.save(car.getEngine());
                    carJdbcRepository.save(car, order.getId());

                }
            }
            connection.commit();
        } catch (SQLException e) {
             throw new RuntimeException(e);
        }
    }

    @Override
    public List<Order> getAll() {
        List <Order> orders = null;
        final String selectOrders = "SELECT * FROM orders FULL JOIN cars ON orders.idOrder = cars.orderID " +
                "FULL JOIN engines ON cars.engineID = engines.idEngine " +
                "FULL JOIN passengerCars ON cars.idCar = passengerCars.idCar " +
                "FULL JOIN truckCars ON cars.idCar = truckCars.idCar ORDER BY orders.idOrder ASC";
        try{
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            resultSet = statement.executeQuery(selectOrders);
            while (resultSet.next()) {
                createOrderFromBD(resultSet.getRow());
            }
            orders = new ArrayList<>();
            generateOrder(orders);
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        mapOrder.clear();
        return orders;
    }

    @Override
    public Order getById(final String id) {
        Order order = null;
        final String getById = "SELECT * FROM orders " +
                "FULL JOIN cars ON orders.idOrder = cars.orderID " +
                "FULL JOIN engines ON cars.engineID = engines.idEngine " +
                "FULL JOIN passengerCars ON cars.idCar = passengerCars.idCar " +
                "FULL JOIN truckCars ON cars.idCar = truckCars.idCar " +
                "WHERE orders.idOrder = ?;";

        try (PreparedStatement getOrderById = connection.prepareStatement(getById, ResultSet.TYPE_SCROLL_INSENSITIVE,
                                                                                    ResultSet.CONCUR_READ_ONLY)) {
            getOrderById.setString(1, id);
            resultSet = getOrderById.executeQuery();
            while (resultSet.next()) {
                createOrderFromBD(resultSet.getRow());
            }
            order = generateOrder(null);
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if(order == null) {
            System.out.println("Order not found");
        }
        mapOrder.clear();
        return order;
    }

    @Override
    public void delete(final String id) {
        final String deleteOrderById = "DELETE FROM orders WHERE orders.idOrder = ?; " +
                "DELETE FROM cars WHERE orderId not IN (SELECT idOrder FROM orders); " +
                "DELETE FROM engines WHERE idengine not IN (SELECT engineid FROM cars);";
        try {
            connection.setAutoCommit(false);
            try (PreparedStatement deleteOrder = connection.prepareStatement(deleteOrderById)) {
                deleteOrder.setString(1, id);
                deleteOrder.execute();
                connection.commit();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Delete order by id: " + id);
    }

    private Order generateOrder(List <Order> orders){
        Order order = null;
        for(Map.Entry<Object, List<Car>> entry: mapOrder.entrySet()){
            Map<String, Date> key = (HashMap<String, Date>) entry.getKey();
            for(Map.Entry<String, Date> entryKey: key.entrySet()){
                order = new Order(entryKey.getKey(), entryKey.getValue());
            }
            order.setCars(entry.getValue());
            if(orders != null)
                orders.add(order);
        }
        return order;
    }

    private void createOrderFromBD(final int row) {
        if(row > 0) {
            try {
                resultSet.absolute(row);
                Map key = new HashMap<String, Date>();
                key.put(resultSet.getString("idOrder"), resultSet.getDate("time"));
                if (!mapOrder.containsKey(key)) {
                    mapOrder.put(key, new ArrayList<Car>());
                }
                final CarJdbcRepository carJdbcRepository = CarJdbcRepository.getInstance(connection);
                Car currentCar = carJdbcRepository.getCarFromDb(resultSet, row);
                mapOrder.get(key).add(currentCar);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}