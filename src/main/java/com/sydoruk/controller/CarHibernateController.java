package com.sydoruk.controller;

import com.sydoruk.model.CarHibernate;
import com.sydoruk.service.CarHibernateService;

import java.util.List;
import java.util.Optional;

public class CarHibernateController {

    private final CarHibernateService carHibernateService;

    public CarHibernateController(final CarHibernateService carHibernateService) {
        this.carHibernateService = carHibernateService;
    }

    public String create() {
        final CarHibernate car = carHibernateService.createAndSave();
        return car.getId_car();
    }

    public List<CarHibernate> getAll() {
        return carHibernateService.getAll();
    }

    public Optional<CarHibernate> getCarById(final String id) {
        return carHibernateService.getById(id);
    }

    public void delete(final String id) {
        carHibernateService.delete(id);
    }
}