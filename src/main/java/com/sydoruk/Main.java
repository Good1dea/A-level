package com.sydoruk;

import com.sydoruk.model.Car;
import com.sydoruk.service.CarService;
import com.sydoruk.util.ReadFromJsonXml;

import java.io.IOException;

public class Main {

    public static void main(String[] arg) throws IOException {
        final Car carOne = CarService.getInstance().createCarFromFile(ReadFromJsonXml.getInstance().readFromJsonFile());
        System.out.println("Car from json");
        CarService.printCar(carOne);
        final Car carTwo = CarService.getInstance().createCarFromFile(ReadFromJsonXml.getInstance().readFromXmlFile());
        System.out.println("Car from xml");
        CarService.printCar(carTwo);
    }
}