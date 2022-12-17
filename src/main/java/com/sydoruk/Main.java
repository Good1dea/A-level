package com.sydoruk;

import com.sydoruk.model.Car;
import com.sydoruk.model.Type;
import com.sydoruk.repository.CarArrayRepository;
import com.sydoruk.service.CarService;
import com.sydoruk.util.AlgorithmUtil;

public class Main {
    public static void main(final String[] args) {
        CarService carService = new CarService(new CarArrayRepository());
        String [] arrUUID = new String[10];
        AlgorithmUtil util = new AlgorithmUtil();
        for(int i = 0; i < 10; i++) {
            Car car = carService.createCar(Type.randomType());
            carService.printCar(car);
            arrUUID[i] = car.getId();
        }
        Car []carSort = util.bubbleSort(carService.getAll());
        System.out.println("Search by ID: " + arrUUID[7]);
        int index = util.binarySearch(carSort, arrUUID[7]);
        carService.printCar(carSort[index]);
    }
}