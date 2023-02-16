package com.sydoruk;

import com.sydoruk.model.*;
import com.sydoruk.service.CarService;

public class Main {

    public static void main(String[] arg) {
        CarFactory factory = new CarFactory();
        Car car = factory.createCar(Type.randomType())
                .addManufacturer(String.valueOf(Manufacturer.SUZUKI))
                .addColor(Color.TURQUOISE)
                .addEngine(new Engine())
                .addCount(3)
                .addPrice(1001)
                .build();
        CarService.printCar(car);
}