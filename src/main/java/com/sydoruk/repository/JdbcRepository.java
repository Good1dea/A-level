package com.sydoruk.repository;

import com.sydoruk.model.*;

import java.sql.*;
import java.util.*;

public class JdbcRepository implements InterfaceRepository<Car> {

    private static JdbcRepository instance;
    private final Connection connection;
    private ResultSet resultSet;
    private Statement statement;
    private List <Order> orders;
    private final Map < Object, List<Car>> mapOrder;

    private static final String[] createTablesSQL = {"CREATE TABLE IF NOT EXISTS engines (idEngine VARCHAR(255) " +
            "NOT NULL PRIMARY KEY, typeEngine VARCHAR(255) NOT NULL, power INTEGER NOT NULL)", "CREATE TABLE " +
            "IF NOT EXISTS orders (idOrder VARCHAR(255) NOT NULL PRIMARY KEY, time TIMESTAMP NOT NULL)",
            "CREATE TABLE IF NOT EXISTS cars (idCar VARCHAR(255) NOT NULL PRIMARY KEY, manufacturer VARCHAR(255) NOT NULL, " +
            "color VARCHAR(255) NOT NULL, count INTEGER NOT NULL, price INTEGER NOT NULL, typeCar VARCHAR(255) NOT NULL, " +
            "engineID VARCHAR(255) NOT NULL, orderID VARCHAR(255), FOREIGN KEY (engineID) REFERENCES engines (idEngine) " +
            "ON DELETE CASCADE, FOREIGN KEY (orderID) REFERENCES orders (idOrder) ON DELETE CASCADE)", "CREATE TABLE " +
            "IF NOT EXISTS passengerCars (idCar VARCHAR(255) NOT NULL PRIMARY KEY, passengerCount INTEGER NOT NULL, " +
            "FOREIGN KEY (idCar) REFERENCES cars (idCar) ON DELETE CASCADE)", "CREATE TABLE IF NOT EXISTS truckCars " +
            "(idCar VARCHAR(255) NOT NULL PRIMARY KEY, loadCapacity INTEGER NOT NULL, FOREIGN KEY (idCar) REFERENCES " +
            "cars (idCar) ON DELETE CASCADE)" };

    private JdbcRepository(Connection connection){
        this.connection = connection;
        mapOrder = new HashMap<Object, List<Car>>();
        createTables();
    }

    public static JdbcRepository getInstance(Connection connection) {
        if (instance == null) {
            instance = new JdbcRepository(connection);
        }
        return instance;
    }

    private void createTables() {
        boolean bWithoutFalse = true;
        for (String createTable : createTablesSQL) {
            try (PreparedStatement createTables = connection.prepareStatement(createTable)) {
                createTables.execute();
            } catch (SQLException e) {
                bWithoutFalse = false;
                e.printStackTrace();
            }
        }
        if(bWithoutFalse) {
            saveCommit();
        }
    }

    public void saveOrders(List<Order> orders){
        this.orders = orders;
        for(Order order : orders){
            String insertOrder = "INSERT INTO orders (idOrder, time ) VALUES ( \'" + order.getId() +
                    "\' , \'" + order.getDate() + " \' )";
            try ( PreparedStatement saveOrder = connection.prepareStatement(insertOrder)) {
                saveOrder.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            for(Car car : order.getCars()){
                save(car, order.getId());
            }
        }
        saveCommit();
        System.out.println("All orders save in BD");
    }

    private void save(final Car car, String idOrder) {
        int capacity = 0;
        String capacityName = "";
        String tableName = "";

        switch (car.getType()) {
            case CAR:
                capacity = ((PassengerCar) car).getPassengerCount();
                capacityName = "passengerCount";
                tableName = "passengerCars";
                break;

            case TRUCK:
                capacity = ((Truck) car).getLoadCapacity();
                capacityName = "loadCapacity";
                tableName = "truckCars";
                break;

            default: {
                return;
            }
        }
        final String insertCar = "INSERT INTO cars (idCar, manufacturer, color, count, price, typeCar, engineID, orderID)" +
                " VALUES ( \'" + car.getId() + "\',\'" + car.getManufacturer() + "\',\'" + car.getColor() + "\',"
                + car.getCount() + "," + car.getPrice() + ",\'" + car.getType() + "\',\'" + car.getEngine().getId() +
                "\',\'" + idOrder + "\')";
        final String insertEngine = "INSERT INTO engines (idEngine, typeEngine, power) VALUES (\'" + car.getEngine().getId() +
                "\',\'" + car.getEngine().getType() + "\'," + car.getEngine().getPower() + ")";

        final String savePassOrTruck = "INSERT INTO " + tableName + " (idCar, " + capacityName + " ) VALUES ( \'" + car.getId() +
                "\' , " + capacity + " )";

        try (PreparedStatement saveCar = connection.prepareStatement(insertCar);
             PreparedStatement saveEngine = connection.prepareStatement(insertEngine);
             PreparedStatement savePassOrTruckCar = connection.prepareStatement(savePassOrTruck)) {
            saveEngine.execute();
            saveCar.execute();
            savePassOrTruckCar.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        saveCommit();
    }

    @Override
    public void save(Car car) {
        save(car, null);
    }

    @Override
    public List<Car> getAll() {
        List<Car> cars = new ArrayList<>();
        final String selectAll = "SELECT * FROM cars " +
                "FULL OUTER JOIN engines ON cars.engineID = engines.idEngine " +
                "FULL OUTER JOIN passengerCars ON cars.idCar = passengerCars.idCar " +
                "FULL OUTER JOIN truckCars ON cars.idCar = truckCars.idCar";

        initResultSet(selectAll);
        try{
            while (resultSet.next()) {
               cars.add(createCarFromBD(resultSet.getRow()));
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cars;
    }

    public List<Order> getAllOrders() {
        List <Order> orders = null;
        final String selectOrders = "SELECT * FROM orders " +
                "FULL OUTER JOIN cars ON orders.idOrder = cars.orderID " +
                "FULL OUTER JOIN engines ON cars.engineID = engines.idEngine " +
                "FULL OUTER JOIN passengerCars ON cars.idCar = passengerCars.idCar " +
                "FULL OUTER JOIN truckCars ON cars.idCar = truckCars.idCar " +
                "ORDER BY orders.idOrder ASC";

        initResultSet(selectOrders);
        try {
            while (resultSet.next()) {
                createOrderFromBD(resultSet.getRow());
            }

            orders = new ArrayList<>();
            generateOrder(orders);
            statement.close();
        } catch (SQLException e) { throw new RuntimeException(e); }

        mapOrder.clear();
        return orders;
    }

    @Override
    public Car getById(final String id) {
        Car car = null;
        final String getCarById = "SELECT * FROM cars " +
                "FULL OUTER JOIN engines ON cars.engineID = engines.idEngine " +
                "FULL OUTER JOIN passengerCars ON cars.idCar = passengerCars.idCar " +
                "FULL OUTER JOIN truckCars ON cars.idCar = truckCars.idCar " +
                "WHERE cars.idCar LIKE \'" + id + "\'";

        initResultSet(getCarById);
        try {
           while(resultSet.next()) {
                car = createCarFromBD(resultSet.getRow());
            }
           statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if(car == null) {
            System.out.println("Car not found");
        }

        return car;
    }

    public Order getOrderById(String id) {
        Order order = null;
        final String getById = "SELECT * FROM orders " +
                "FULL OUTER JOIN cars ON orders.idOrder = cars.orderID " +
                "FULL OUTER JOIN engines ON cars.engineID = engines.idEngine " +
                "FULL OUTER JOIN passengerCars ON cars.idCar = passengerCars.idCar " +
                "FULL OUTER JOIN truckCars ON cars.idCar = truckCars.idCar " +
                "WHERE orders.idOrder LIKE \'" + id +"\'";

        initResultSet(getById);
        try {
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
        final String deleteCarById = "DELETE FROM cars WHERE cars.idCar LIKE \'" + id + "\'; " +
                "DELETE FROM engines WHERE idengine not IN (SELECT engineid FROM cars)";
        try (PreparedStatement deleteCar = connection.prepareStatement(deleteCarById)) {
            deleteCar.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        saveCommit();
        System.out.println("Delete car by id: " + id);
    }

    public void deleteOrder(final String id) {
        final String deleteOrderById = "DELETE FROM orders WHERE orders.idOrder LIKE \'" + id + "\'; " +
                "DELETE FROM engines WHERE idengine not IN (SELECT engineid FROM cars)";
        try (PreparedStatement deleteOrder = connection.prepareStatement(deleteOrderById)) {
            deleteOrder.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        saveCommit();
        System.out.println("Delete order by id: " + id);
    }

    private void saveCommit(){
        try {
            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void initResultSet(final String sqlQuery){
        try{
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            resultSet = statement.executeQuery(sqlQuery);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Car createCarFromBD(final int row){
        Car car = null;
        if(row > 0) {
            try {
                 resultSet.absolute(row);
                if (Type.valueOf(resultSet.getString("typeCar")).equals(Type.CAR)) {
                    car = new PassengerCar(resultSet.getString("idCar"), resultSet.getInt("passengerCount"));
                } else {
                    car = new Truck(resultSet.getString("idCar"), resultSet.getInt("loadCapacity"));
                }
                Engine engine = new Engine(resultSet.getString("idEngine"),
                        resultSet.getInt("power"), resultSet.getString("typeEngine"));
                car.setEngine(engine);
                car.setManufacturer(resultSet.getString("manufacturer"));
                car.setPrice(resultSet.getInt("price"));
                car.setCount(resultSet.getInt("count"));
                car.setColor(Color.valueOf(resultSet.getString("color")));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return car;
    }

    private Order generateOrder(List <Order> orders){
        Order order = null;
        for(Map.Entry<Object, List<Car>> entry: mapOrder.entrySet()){
            Map<String, String> key = (HashMap<String, String>) entry.getKey();
            for(Map.Entry<String, String> entryKey: key.entrySet()){
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
                Map key = new HashMap<String, String>();
                key.put(resultSet.getString("idOrder"), resultSet.getString("time"));
                if (!mapOrder.containsKey(key)) {
                    mapOrder.put(key, new ArrayList<Car>());
                }
                Car currentCar = createCarFromBD(row);
                mapOrder.get(key).add(currentCar);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}