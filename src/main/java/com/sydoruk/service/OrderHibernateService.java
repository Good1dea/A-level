package com.sydoruk.service;

import com.sydoruk.model.CarHibernate;
import com.sydoruk.model.OrderHibernate;
import com.sydoruk.model.Type;
import com.sydoruk.repository.OrderHibernateRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderHibernateService {
    private final OrderHibernateRepository repository;

    private final CarHibernateService service;

    public OrderHibernateService(final  OrderHibernateRepository repository, final CarHibernateService carHibernateService) {
        this.repository = repository;
        this.service = carHibernateService;
    }

    public OrderHibernate create() {
        final OrderHibernate order = new OrderHibernate();
        order.setCars(createListCars(order));
        repository.save(order);
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
        return repository.getById(id);
    }

    public List<OrderHibernate> getAll() {
        return repository.getAll();
    }

    public void delete (final String id){
        if (!(id == null || id.isEmpty())) {
            repository.delete(id);
        }
    }
}