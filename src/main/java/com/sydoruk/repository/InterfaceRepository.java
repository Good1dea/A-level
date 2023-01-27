package com.sydoruk.repository;

public interface InterfaceRepository<Car>{

    void save(final Car car);

    Car[] getAll();

    Car getById(final String id);

    void delete(final String id);
}