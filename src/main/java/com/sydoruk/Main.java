package com.sydoruk;

import com.sydoruk.model.Car;
import com.sydoruk.model.Color;
import com.sydoruk.model.Type;
import com.sydoruk.repository.CarArrayRepository;
import com.sydoruk.service.CarService;

import java.util.*;

public class Main {
    public static void main(final String[] args) {
        CarService carService = new CarService(new CarArrayRepository());
        List<Car> cars = new LinkedList<>();
        for(int i = 0; i < 500; i++) {
            Car car = carService.createCar(Type.randomType());
            cars.add(car);
        }
        System.out.println("findManufacturerByPrice > 90000");
        carService.findManufacturerByPrice(cars, 90000);
        System.out.println("Sum of count of all cars: " + carService.countSum(cars));
        System.out.println("Sorted Map(id, type): " + carService.mapToMap(cars));
        System.out.println("Statistic: " + carService.statistic(cars));
        System.out.println("Price of all cars is higher 10000$: " + carService.priceCheck(cars, 10000));


        Map<String, Object> mapConfig = new HashMap<>();
        mapConfig.put("Type", Type.CAR);
        mapConfig.put("Manufacturer", "Suzuki" );
        mapConfig.put("Color", Color.TURQUOISE);
        mapConfig.put("Price", 155258);

        Car newMegaCar = carService.mapToObject(mapConfig);
        carService.printCar(newMegaCar);

        List<List<Car>> listsOfCars = new ArrayList<>();
        listsOfCars.add(cars);
        listsOfCars.add(cars);
        listsOfCars.add(cars);
        Map<Color, Long> mapFiltered = carService.innerList(listsOfCars, 28500);
        System.out.println(mapFiltered);
    }
}