package com.sydoruk.repository;

public interface Repository <T>{

    void save(final T obj);

    T[] getAll();

    T getById(final String id);

    void delete(final String id);

}
