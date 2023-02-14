package com.sydoruk;

import com.sydoruk.controller.OrderController;
import com.sydoruk.repository.CarHibernateRepository;
import com.sydoruk.repository.OrderHibernateRepository;
import com.sydoruk.service.CarHibernateService;
import com.sydoruk.service.OrderHibernateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

    final static Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] arg) {
        logger.info("Start program");
        final OrderHibernateRepository orderRepository = new OrderHibernateRepository();
        final OrderHibernateService orderService = new OrderHibernateService(orderRepository, new CarHibernateService(new CarHibernateRepository()));
        final OrderController orderController = new OrderController(orderService);
        orderOperations(orderController);
        logger.info("End program");
    }

    private static void orderOperations(final OrderController orderController) {
        String idOrder = orderController.create();
        System.out.println(orderController.getAll());
        System.out.println(orderController.getOrderById(idOrder));
        orderController.delete(idOrder);
        orderController.getOrderById(idOrder);
        orderController.getAll();
    }
}