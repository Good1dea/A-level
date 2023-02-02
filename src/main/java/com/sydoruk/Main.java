package com.sydoruk;

import com.sydoruk.model.Car;
import com.sydoruk.model.Type;
import com.sydoruk.repository.JdbcRepository;
import com.sydoruk.repository.JdbcManager;
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
        final JdbcRepository repository = JdbcRepository.getInstance(connection);
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
            orders.add(i, order);
        }

        repository.saveOrders(orders);
        System.out.println("All orders in BD: " + repository.getAllOrders());
        System.out.println("All cars in BD: " + repository.getAll());
        System.out.println("Get car by id: " + repository.getById(carId.get(random.nextInt(0,carId.size()))));
        System.out.println("Get order by id: " + repository.getOrderById(orderId.get(random.nextInt(0, orderId.size()))));
        repository.delete(carId.get(random.nextInt(carId.size())));
        repository.deleteOrder(orderId.get(random.nextInt(orderId.size())));
        System.out.println("All orders in BD: " + repository.getAllOrders());
        System.out.println("All cars in BD: " + repository.getAll());
        connection.commit();
        connection.close();

    }
}
