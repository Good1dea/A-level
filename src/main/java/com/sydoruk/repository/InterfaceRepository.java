package com.sydoruk.repository;

import java.util.List;

public interface InterfaceRepository<T>{

    void save(final T car);

    List<T> getAll();

    T getById(final String id);

    void delete(final String id);
}