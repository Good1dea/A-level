package com.sydoruk.service;

import com.sydoruk.model.CarHibernate;
import com.sydoruk.model.OrderHibernate;
import com.sydoruk.model.Type;
import com.sydoruk.repository.OrderHibernateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderHibernateService {
    private final OrderHibernateRepository repository;
    private final CarHibernateService service;
    final static Logger logger = LoggerFactory.getLogger(OrderHibernateService.class);

    public OrderHibernateService(final  OrderHibernateRepository repository, final CarHibernateService carHibernateService) {
        this.repository = repository;
        this.service = carHibernateService;
    }

    public OrderHibernate create() {
        final OrderHibernate order = new OrderHibernate();
        order.setCars(createListCars(order));
        logger.debug("Create order with {} cars", order.getCars().size());
        repository.save(order);
        logger.debug("Save order with id {}", order.getId_order());
        return order;
    }

    private List<CarHibernate> createListCars(final OrderHibernate order) {
        final List<CarHibernate> cars = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            final CarHibernate car = service.createCar(Type.randomType());
            car.setOrder(order);
            cars.add(car);
        }
        return cars;
    }

    public Optional<OrderHibernate> getById(final String id) {
        Optional<OrderHibernate> order = repository.getById(id);
        if (order.isEmpty() || order == null) {
            logger.info("Can't find order with id {}", id);
        }
        return order;
    }

    public List<OrderHibernate> getAll() {
        logger.info("View all orders");
        List<OrderHibernate> orders = repository.getAll();
        if (orders.isEmpty() || orders == null){
            logger.warn("There are no orders in DB");
        }
        return orders;
    }

    public void delete (final String id){
        if (!(id == null || id.isEmpty())) {
            repository.delete(id);
            logger.debug("Delete order with id {}", id);
        } else {
            logger.warn("Incorrect id for deleting order");
        }
    }
}