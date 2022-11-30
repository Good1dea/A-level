package com.sydoruk;

import com.sydoruk.repository.CarArrayRepository;
import com.sydoruk.service.CarService;

public class Main {
    public static void main(final String[] args) {
        CarService carService = new CarService(new CarArrayRepository());
        carService.printPass(carService.createPassengerCar());
        carService.printTruck(carService.createTruck());
    }
}