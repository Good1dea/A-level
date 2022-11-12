package com.sydoruk;

import com.sydoruk.model.Car;
import com.sydoruk.service.CarService;

public class Main {

    public static void main(String[] args) {
        CarService carService = new CarService();
        for(int i = 0; i < 3; i++) {
            Car car = carService.create();
            carService.print(car);
            CarService.check(car);
            System.out.println();
        }
    }
}
