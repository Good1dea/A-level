package com.sydoruk;

import com.sydoruk.model.Car;
import com.sydoruk.model.Type;
import com.sydoruk.repository.CarArrayRepository;
import com.sydoruk.service.CarService;

public class Main {
    public static void main(final String[] args) {
        CarService carService = new CarService(new CarArrayRepository());
        Car carFirst = carService.createCar(Type.randomType());
        carService.printCar(carFirst);
        Car carSecond = carService.createCar(Type.randomType());
        carService.printCar(carSecond);
        System.out.println(carService.carEquals(carFirst, carSecond));
    }
}