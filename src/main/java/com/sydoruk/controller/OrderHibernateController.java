package com.sydoruk.controller;

import com.sydoruk.model.OrderHibernate;
import com.sydoruk.service.OrderHibernateService;

import java.util.List;
import java.util.Optional;

public class OrderHibernateController {
    private final OrderHibernateService orderHibernateService;

    public OrderHibernateController(final OrderHibernateService orderHibernateService) {
        this.orderHibernateService = orderHibernateService;
    }

    public String create() {
        final OrderHibernate order = orderHibernateService.create();
        return order.getId_order();
    }

    public List<OrderHibernate> getAll() {
        return orderHibernateService.getAll();
    }

    public Optional<OrderHibernate> getOrderById(final String id) {
        return orderHibernateService.getById(id);
    }


    public void delete(final String id) {
        orderHibernateService.delete(id);
    }
}
