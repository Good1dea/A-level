package com.sydoruk.repository;

import java.util.List;

public interface InterfaceRepository<T>{

    void save(final Car car);

    List<T> getAll();

    Car getById(final String id);

    void delete(final String id);
}