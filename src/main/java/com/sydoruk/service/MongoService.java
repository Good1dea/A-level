package com.sydoruk.service;

import com.sydoruk.model.*;
import com.sydoruk.repository.CarMongoRepository;
import com.sydoruk.repository.OrderMongoRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MongoService {

    private static MongoService instance;

    final Random random = new Random();
    private final  OrderMongoRepository orderRepository = OrderMongoRepository.getInstance();
    private final CarMongoRepository carRepository = CarMongoRepository.getInstance();
    private final CarFactory factory = new CarFactory();
    List<String> carIdForFind = new ArrayList<>();
    List<String> orderIdForFind = new ArrayList<>();

    private MongoService(){
    }

      public static synchronized MongoService getInstance() {
        if (instance == null) {
            instance = new MongoService();
        }
        return instance;
    }

     public void execute() {
        fillMongoRepository();
        findOrderById(orderIdForFind.get(random.nextInt(orderIdForFind.size()-1)));
        findAllOrders();
        findCarById(carIdForFind.get(random.nextInt(orderIdForFind.size()-1)));
        findAllCars();
        deleteCar(carIdForFind.get(random.nextInt(orderIdForFind.size()-1)));
        deleteOrder(orderIdForFind.get(random.nextInt(orderIdForFind.size()-1)));
        carRepository.closeConnection();
        orderRepository.closeConnection();
   }

    private void fillMongoRepository() {
       List<String> carId = new ArrayList<>();
       for (int j = 0; j < 3; j++) {
           final int count = random.nextInt(1, 5);
           for (int i = 0; i < count; i++) {
               Car car = factory.createCar(Type.randomType())
                       .addManufacturer(String.valueOf(Manufacturer.randomManufacturer()))
                       .addColor(Color.randomColor())
                       .addEngine(new Engine())
                       .addCount(random.nextInt(1,4))
                       .addPrice(random.nextInt(500,1000))
                       .build();

               carRepository.save(car);
               carId.add(car.getId());
               carIdForFind.add(car.getId());
           }
           Order order = new Order();
           order.setCarId(carId);
           orderIdForFind.add(order.getId());
           order.printPreview();
           orderRepository.save(order);
           carId.clear();
       }
   }

    private void findOrderById(final String id) {
        System.out.println("========== Find order by id: " + id +"==========");
        orderRepository.getById(id).ifPresent(Order::printPreview);
    }

    private void findAllOrders() {
        System.out.println("========== Print ALL orders ==========");
        List<Order> orders = orderRepository.getAll();
        for (Order oneOfOrder : orders) {
           oneOfOrder.printPreview();
       }
   }

    private void deleteOrder(final String id){
        orderRepository.delete(id);
        System.out.println("========== Delete order by id: " + id +"==========");
    }

    private void findCarById(final String id) {
        System.out.println("========== Find car by id: " + id +"==========");
        carRepository.getById(id).ifPresent(CarService::printCar);
    }

    private void findAllCars(){
        System.out.println("========== Print ALL auto ==========");
        carRepository.getAll().forEach(CarService::printCar);
    }

    private void deleteCar(final String id){
        carRepository.delete(id);
        System.out.println("========== Delete car by id: " + id +"==========");
    }
}