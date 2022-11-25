package com.sydoruk;

import com.sydoruk.model.Car;
import com.sydoruk.repository.CarArrayRepository;
import com.sydoruk.service.CarService;
import com.sydoruk.util.RandomGenerator;

public class Main {

    public static void main(final String[] args) {

        CarService carService = new CarService(new CarArrayRepository());
        for (int i = 0; i < 3; i++) {
            Car car = carService.create();
            carService.print(car);
            CarService.check(car);
            System.out.println();
        }
    }
}