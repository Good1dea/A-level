package com.sydoruk;

import com.sydoruk.model.Car;
import com.sydoruk.model.Type;
import com.sydoruk.repository.CarJdbcRepository;
import com.sydoruk.repository.OrderJdbcRepository;
import com.sydoruk.util.JdbcManager;
import com.sydoruk.model.Order;
import com.sydoruk.service.CarService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {

    public static void main(String[] args) throws SQLException {
        Random random = new Random();
        final Connection connection = JdbcManager.getConnection();
        connection.setAutoCommit(false);
        final OrderJdbcRepository orderJdbcRepository = OrderJdbcRepository.getInstance(connection);
        final CarJdbcRepository carJdbcRepository = CarJdbcRepository.getInstance(connection);
        final CarService carService = new CarService();
        final List<Order> orders = new ArrayList<>(5);
        final List<String> carId = new ArrayList<>();
        final List<String> orderId = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            Order order = new Order();
            int rand = random.nextInt(1,5);
            final List<Car> cars = new ArrayList<>(rand);
            for(int j = 0; j <= rand; j++){
                Car car = carService.createCar(Type.randomType());
                carId.add(car.getId());
                cars.add(car);
            }
            orderId.add(order.getId());
            order.setCars(cars);
            Order.printOrder(order);
            orderJdbcRepository.save(order);
        }

        System.out.println("All orders in BD: " + orderJdbcRepository.getAll());
        System.out.println("All cars in BD: " + carJdbcRepository.getAll());
        System.out.println("Get car by id: " + carJdbcRepository.getById(carId.get(random.nextInt(0,carId.size()))));
        System.out.println("Get order by id: " + orderJdbcRepository.getById(orderId.get(random.nextInt(0, orderId.size()))));
        carJdbcRepository.delete(carId.get(random.nextInt(carId.size())));
        orderJdbcRepository.delete(orderId.get(random.nextInt(orderId.size())));
        System.out.println("All orders in BD: " + orderJdbcRepository.getAll());
        System.out.println("All cars in BD: " + orderJdbcRepository.getAll());
        connection.commit();
        connection.close();

    }
}
