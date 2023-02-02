package com.sydoruk.repository;

import com.sydoruk.annotation.Singleton;
import com.sydoruk.model.Car;

import java.util.List;

@Singleton
public class CarArrayRepository implements InterfaceRepository<Car> {
    private static Car[] cars = new Car[10];
    private static CarArrayRepository instance;

    public static CarArrayRepository getInstance() {
        if (instance == null) {
            instance = new CarArrayRepository();
        }
        return instance;
    }

    @Override
    public void save(final Car car) {
        final int index = putCar(car);
        if (index == cars.length) {
            final int oldLength = cars.length;
            increaseArray();
            cars[oldLength] = car;
        }
    }

    @Override
    public List<Car> getAll() {
        return null;
    }


    public Car[] getAllCars() {
        final int newLength = foundLength();
        final Car[] newCars = new Car[newLength];
        System.arraycopy(cars, 0, newCars, 0, newLength);
        return newCars;
    }

    @Override
    public Car getById(final String id) {
        for (Car car : cars) {
            if (car.getId().equals(id)) {
                return car;
            }
        }
        return null;
    }

    @Override
    public void delete(final String id) {
        int index = 0;
        for (; index < cars.length; index++) {
            if (cars[index].getId().equals(id)) {
                break;
            }
        }
        if (index != cars.length) {
            System.arraycopy(cars, index + 1, cars, index,
                    cars.length - (index + 1));
        }
    }

    private int foundLength() {
        int newLength = 0;
        for (Car car : cars) {
            if (car != null) {
                newLength++;
            } else {
                break;
            }
        }
        return newLength;
    }

    private int putCar(final Car car) {
        int index = 0;
        for (; index < cars.length; index++) {
            if (cars[index] == null) {
                cars[index] = car;
                break;
            }
        }
        return index;
    }

    private void increaseArray() {
        Car[] newCars = new Car[cars.length * 2];
        System.arraycopy(cars, 0, newCars, 0, cars.length);
        cars = newCars;
    }
}