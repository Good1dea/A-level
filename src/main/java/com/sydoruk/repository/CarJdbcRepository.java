package com.sydoruk.repository;

import com.sydoruk.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CarJdbcRepository implements InterfaceRepository <Car> {

    private Connection connection;
    private static CarJdbcRepository instance;
    private ResultSet resultSet;
    private Statement statement;

    private CarJdbcRepository(final Connection connection){
        this.connection = connection;
    }

    public static CarJdbcRepository getInstance(final Connection connection) {
        if (instance == null) {
            instance = new CarJdbcRepository(connection);
        }
        return instance;
    }

    public void save(final Car car, final String idOrder) throws SQLException {
        connection.setAutoCommit(false);
        final String insertCar = "INSERT INTO cars (idCar, manufacturer, color, count, price, typeCar, engineID, orderID)" +
                " VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement saveCar = connection.prepareStatement(insertCar)) {
            saveCar.setString(1, car.getId());
            saveCar.setString(2, car.getManufacturer());
            saveCar.setString(3, car.getColor().toString());
            saveCar.setInt(4, car.getCount());
            saveCar.setInt(5, car.getPrice());
            saveCar.setString(6, car.getType().toString());
            saveCar.setString(7, car.getEngine().getId());
            saveCar.setString(8, idOrder);
            saveCar.executeUpdate();
        }
        if (car.getType() == Type.CAR) {
            final String insertPass = "INSERT INTO passengerCars (idCar, passengerCount) VALUES (?, ?)";
            try (PreparedStatement savePassCar = connection.prepareStatement(insertPass)) {
                savePassCar.setString(1, car.getId());
                savePassCar.setInt(2, ((PassengerCar) car).getPassengerCount());
                savePassCar.executeUpdate();
            }
        } else if (car.getType() == Type.TRUCK) {
            final String insertTruck = "INSERT INTO truckCars (idCar, loadCapacity) VALUES (?, ?)";
            try (PreparedStatement saveTruckCar = connection.prepareStatement(insertTruck)) {
                saveTruckCar.setString(1, car.getId());
                saveTruckCar.setInt(2, ((Truck) car).getLoadCapacity());
                saveTruckCar.executeUpdate();
            }
        }
        connection.commit();
    }

    @Override
    public void save(final Car object) {
        try {
            save(object, null);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Car> getAll() {
        List<Car> cars = new ArrayList<>();
        final String selectAll = "SELECT * FROM cars " +
                "FULL JOIN engines ON cars.engineID = engines.idEngine " +
                "FULL JOIN passengerCars ON cars.idCar = passengerCars.idCar " +
                "FULL JOIN truckCars ON cars.idCar = truckCars.idCar";
        try{
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            resultSet = statement.executeQuery(selectAll);
            while (resultSet.next()) {
                cars.add(createCarFromBD(resultSet.getRow()));
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cars;
    }

    @Override
    public Optional<Car> getById(String id) {
        return Optional.empty();
    }

    public Car carGetById(final String id) {
        Car car = null;
        final String getById = "SELECT * FROM cars " +
                "FULL JOIN engines ON cars.engineID = engines.idEngine " +
                "FULL JOIN passengerCars ON cars.idCar = passengerCars.idCar " +
                "FULL JOIN truckCars ON cars.idCar = truckCars.idCar " +
                "WHERE cars.idCar = ?;";
        try {
            PreparedStatement getCarById = connection.prepareStatement(getById, ResultSet.TYPE_SCROLL_INSENSITIVE,
                                        ResultSet.CONCUR_READ_ONLY);
            getCarById.setString(1, id);
            resultSet = getCarById.executeQuery();
            while(resultSet.next()) {
                car = createCarFromBD(resultSet.getRow());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return car;
    }

    @Override
    public void delete(final String id) {
        try{
            connection.setAutoCommit(false);
            final String deleteCarById = "DELETE FROM cars WHERE cars.idCar = ?;";
            final String delOrderAndEngineIfNotCar = "DELETE FROM engines WHERE idengine not IN (SELECT engineid FROM cars);" +
                    "DELETE FROM orders WHERE idorder not IN (SELECT orderid FROM cars);";
            try (PreparedStatement deleteCar = connection.prepareStatement(deleteCarById);
                 PreparedStatement delOrderAndEngine = connection.prepareStatement(delOrderAndEngineIfNotCar)) {
                deleteCar.setString(1, id);
                deleteCar.execute();
                delOrderAndEngine.execute();
                connection.commit();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Delete car by id: " + id);
    }

    public Car getCarFromDb(final ResultSet resultSet, final int row) {
        this.resultSet = resultSet;
        return createCarFromBD(row);
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


}