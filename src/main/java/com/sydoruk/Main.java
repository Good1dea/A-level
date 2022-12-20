package com.sydoruk;

import com.sydoruk.container.GenericContainer;
import com.sydoruk.model.Car;
import com.sydoruk.model.Type;
import com.sydoruk.repository.CarArrayRepository;
import com.sydoruk.service.CarService;

public class Main {
    public static void main(final String[] args) {
        CarService carService = new CarService(new CarArrayRepository());
        Car car = carService.createCar(Type.randomType());
        carService.printCar(car);

        System.out.println("==============Original====================");

        GenericContainer<? extends Car> genContainer= new GenericContainer<>(car);
        genContainer.print();

        System.out.println("================Random===================");
        genContainer.increaseCount();
        genContainer.print();

        System.out.println("=================Number==================");
        genContainer.increaseCount(28.3);
        genContainer.print();

    }
}