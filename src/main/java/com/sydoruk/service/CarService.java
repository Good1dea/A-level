package com.sydoruk.service;

import com.sydoruk.model.Car;
import java.util.Random;

public class CarService {

    private Random random = new Random();
    private final String[] manufacturers = {"Suzuki", "Audi", "ZAZ", "Ford", "Toyota", "Fiat", "Volvo", "Tesla",
            "Volkswagen", "Subaru", "Dodge", "Ferrari", "Cadillac", "BMW", "Bugatti", "Jaguar"};
    private final String[] engines = {"electric", "1.4L", "1.6L", "2L", "1L", "0.9L turbo"};
    private final String[] colors = {"green", "red", "blue", "black", "grey", "white", "yellow","turquoise"};

    public Car create(){
       Car car = new Car();
       car.setManufacturer(manufacturers[random.nextInt(0, manufacturers.length)]);
       car.setEngine(engines[random.nextInt(0, engines.length)]);
       car.setColor(colors[random.nextInt(0, colors.length)]);
       car.setCount(1);
       car.setPrice(random.nextInt(1000, 100001));
       return car;
    }

   public void print(Car car){
       System.out.println("Manufacturer: " + car.getManufacturer());
       System.out.println("Engine: " + car.getEngine());
       System.out.println("Color: " + car.getColor());
       System.out.println("Count: " + car.getCount());
       System.out.println("Price: " + car.getPrice() + " $");
       System.out.println();
   }
}