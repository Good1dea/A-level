package com.sydoruk.service;

import com.sydoruk.model.Car;
import com.sydoruk.model.Color;
import com.sydoruk.model.Engine;
import com.sydoruk.repository.CarArrayRepository;
import com.sydoruk.util.RandomGenerator;

import java.util.Arrays;
import java.util.Random;

public class CarService {

    private final CarArrayRepository carArrayRepository;
    private final Random random = new Random();
    private final String[] manufacturers = {"Suzuki", "Audi", "ZAZ", "Ford", "Toyota", "Fiat", "Volvo", "Tesla",
            "Volkswagen", "Subaru", "Dodge", "Ferrari", "Cadillac", "BMW", "Bugatti", "Jaguar"};

    public CarService(final CarArrayRepository carArrayRepository) {
        this.carArrayRepository = carArrayRepository;
    }

    public static void check(Car car){
        if(car == null){
            return;
        }
        if(car.getCount() >= 1 && car.getEngine().getPower() > 200){
            System.out.println("The car is ready for sale");
        } else if (car.getCount() < 1 && car.getEngine().getPower() > 200){
            System.out.println("Power is more than 200, but car is not available, because count is less than 1");
        } else if (car.getCount() >= 1 && car.getEngine().getPower() < 200) {
            System.out.println("The car is available, but engine power is less than 200");
        } else {
            System.out.println("Engine power is less than 200 and count is less than 1");
        }
    }

    public int create(RandomGenerator rand) {
        final int counter = rand.random();
        if (counter == 0) {
            return -1;
        } else {
            for (int i = 1; i <= counter; i++) {
                print(createCar());
            }
            return counter;
        }
    }

    public Car createCar(){
       Car car = new Car();
       Engine engine = new Engine();
       car.setManufacturer(manufacturers[random.nextInt(0, manufacturers.length)]);
       car.setEngine(engine);
       car.setColor(Color.randomColor());
       car.setCount(random.nextInt(2));
       car.setPrice(random.nextInt(1000, 100001));
       carArrayRepository.save(car);
       return car;
    }

   public void print(Car car){
       if(car == null){
           return;
       }
       System.out.println("Manufacturer: " + car.getManufacturer());
       System.out.println("Engine: " + car.getEngine().toString());
       System.out.println("Color: " + car.getColor());
       System.out.println("Count: " + car.getCount());
       System.out.println("Price: " + car.getPrice() + " $");
   }

    public void printAll() {
        final Car[] all = carArrayRepository.getAll();
        System.out.println(Arrays.toString(all));
    }

    public Car[] getAll() {
        return carArrayRepository.getAll();
    }

    public Car find(final String id) {
        if (id == null || id.isEmpty()) {
            return null;
        }
        return carArrayRepository.getById(id);
    }

    public void delete(final String id) {
        if (id != null && !id.isEmpty()) {
           carArrayRepository.delete(id);
        }
    }
}