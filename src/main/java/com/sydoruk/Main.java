package com.sydoruk;

import com.sydoruk.controller.CarHibernateController;
import com.sydoruk.controller.OrderHibernateController;
import com.sydoruk.repository.CarHibernateRepository;
import com.sydoruk.repository.OrderHibernateRepository;
import com.sydoruk.service.CarHibernateService;
import com.sydoruk.service.OrderHibernateService;

public class Main {

    public static void main(String[] args) {
        final CarHibernateService carService = new CarHibernateService(new CarHibernateRepository());
        final CarHibernateController carController = new CarHibernateController(carService);
        final OrderHibernateRepository orderRepository = new OrderHibernateRepository();
        final OrderHibernateService orderService = new OrderHibernateService(orderRepository, carService);
        final OrderHibernateController orderController = new OrderHibernateController(orderService);
        carOperations(carController);
        orderOperations(orderController);
    }

    private static void carOperations(final CarHibernateController carController) {
        String idCar = carController.create();
        System.out.println(carController.getAll());
        carController.getCarById(idCar).ifPresentOrElse(
                System.out::println,
                () -> System.out.println("Can't find car with id " + idCar)
        );
        carController.delete(idCar);
        carController.getCarById(idCar).ifPresentOrElse(
                System.out::println,
                () -> System.out.println("Can't find car with id " + idCar)
        );
    }

    public static void orderOperations(final OrderHibernateController orderController) {
        String idOrder = orderController.create();
        System.out.println(orderController.getAll());
        orderController.getOrderById(idOrder).ifPresentOrElse(
                System.out::println,
                () -> System.out.println("Can't find order with id " + idOrder)
        );
        orderController.delete(idOrder);
        orderController.getOrderById(idOrder).ifPresentOrElse(
                System.out::println,
                () -> System.out.println("Can't find order with id " + idOrder)
        );
    }
}