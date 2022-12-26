package com.sydoruk;

import com.sydoruk.container.CarList;
import com.sydoruk.model.Car;
import com.sydoruk.model.Type;
import com.sydoruk.repository.CarArrayRepository;
import com.sydoruk.service.CarService;

public class Main {
    public static void main(final String[] args) {
        CarService carService = new CarService(new CarArrayRepository());
        Car car = carService.createCar(Type.randomType());
        System.out.println("============== addNodeInHead ====================");
        CarList <? extends Car> carList = new CarList<>();
        carList.addNodeInHead(car);
        carList.printNodes();
        System.out.println();
        System.out.println("============== addNodeInTail x10 ================");
        for (int i = 0; i < 10;i++) {
            Car newCar = carService.createCar(Type.randomType());
            carList.addNodeInTail(newCar);
        }
        carList.printNodes();
        System.out.println();
        System.out.println("============== insertPosition 4 ==================");
        Car car2 = carService.createCar(Type.randomType());
        carList.insertPosition(4, car2);
        carList.printNodes();
        System.out.println();
        System.out.println("============== getPosition 3 ====================");
        Car[] carArray = carService.getAll();
        int position = carList.getPosition(carArray[3]);
        System.out.println(position);
        System.out.println();
        System.out.println("============== deleteByPosition ================");
        carList.deleteByPosition(position);
        carList.printNodes();
        carService.delete(carArray[3].getId());
        System.out.println();
        System.out.println("============== getTotalCarCount ==================");
        System.out.println(carList.getTotalCarCount());
    }
}