package com.sydoruk;

import com.sydoruk.container.CarComparator;
import com.sydoruk.container.CarTree;
import com.sydoruk.model.Car;
import com.sydoruk.model.Type;
import com.sydoruk.repository.CarArrayRepository;
import com.sydoruk.service.CarService;

import java.util.HashMap;
import java.util.LinkedList;

public class Main {
    public static void main(final String[] args) {
        CarService carService = new CarService(new CarArrayRepository());
        Car carA = carService.createCar(Type.randomType());
        carService.printCar(carA);
        Car carB = carService.createCar(Type.randomType());
        carService.printCar(carB);
        System.out.println("============== compare ====================");
        CarComparator <Car> carComparator = new CarComparator<>();
        System.out.println(carComparator.compare(carA, carB));

        System.out.println("============== tree ====================");
        System.out.println();
        CarTree<Car> carTree = new CarTree<>();
        for(int i = 0; i < 5000; i++) {
            Car car = carService.createCar(Type.randomType());
            carTree.insertNode(car);
            System.out.print(car.getCount() + "  ");
        }

        System.out.println();
        System.out.println("============== Count ====================");
        System.out.println("Left count = " + carTree.getLeftCarCount());
        System.out.println("Right count = " + carTree.getRightCarCount());

        System.out.println("============== Manufacturer MAP ====================");
        System.out.println();
        HashMap<String, Integer> mapManufacturer = carService.getMapManufacturer(carService.getAll());
        System.out.println(mapManufacturer.toString());

        System.out.println("============== Engine MAP ====================");
        System.out.println();
        HashMap<Integer, LinkedList> mapEngine = carService.getMapEngineType(carService.getAll());
        System.out.println(mapEngine.toString());
    }
}