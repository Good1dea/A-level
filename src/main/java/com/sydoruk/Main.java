package com.sydoruk;

import com.sydoruk.model.Car;
import com.sydoruk.model.Type;
import com.sydoruk.repository.CarArrayRepository;
import com.sydoruk.service.CarService;

public class Main {
    public static void main(final String[] args) {
        CarService carService = new CarService(new CarArrayRepository());
        Car car = carService.createCar(Type.randomType());
        carService.printCar(car);
        carService.printManufacturerAndCount(car);
        carService.printColor(car);
        carService.checkCount(car);
        carService.printEngineInfo(car);
        carService.printInfo(car);
    }
}