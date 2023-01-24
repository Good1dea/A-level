package com.sydoruk.repository;

public interface InterfaceRepository<T>{

    void save(final T car);

    T[] getAll();

    T getById(final String id);

    void delete(final String id);
}