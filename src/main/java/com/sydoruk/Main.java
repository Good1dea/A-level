package com.sydoruk;

import com.sydoruk.service.CarService;

public class Main {

    public static void main(String[] args) {
        CarService car = new CarService();
        for(int i = 0; i < 3; i++) {
            car.print(car.create());
        }
    }
}
