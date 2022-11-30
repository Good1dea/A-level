package com.sydoruk.service;

import com.sydoruk.model.*;
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

    public PassengerCar createPassengerCar() {
        PassengerCar passengerCar = new PassengerCar();
        Engine engine = new Engine();
        passengerCar.setType(Type.CAR);
        passengerCar.setManufacturer(manufacturers[random.nextInt(0, manufacturers.length)]);
        passengerCar.setEngine(engine);
        passengerCar.setColor(Color.randomColor());
        passengerCar.setPrice(random.nextInt(1000, 100001));
        passengerCar.setPassengerCount(random.nextInt(9));
        passengerCar.restore();
        carArrayRepository.save(passengerCar);
        return passengerCar;
    }

    public Truck createTruck() {
        Truck truck = new Truck();
        Engine engine = new Engine();
        truck.setType(Type.TRUCK);
        truck.setManufacturer(manufacturers[random.nextInt(0, manufacturers.length)]);
        truck.setEngine(engine);
        truck.setColor(Color.randomColor());
        truck.setPrice(random.nextInt(1000, 100001));
        truck.setLoadCapacity(random.nextInt(500, 10000));
        truck.restore();
        carArrayRepository.save(truck);
        return truck;
    }

    public void printPass(PassengerCar passengerCar) {
        if (passengerCar == null) {
            return;
        }
        System.out.println("Manufacturer: " + passengerCar.getManufacturer());
        System.out.println("Type: " + passengerCar.getType());
        System.out.println("Engine: " + passengerCar.getEngine().toString());
        System.out.println("Color: " + passengerCar.getColor());
        System.out.println("Passengers: " + passengerCar.getPassengerCount());
        System.out.println("Price: " + passengerCar.getPrice() + " $");
        System.out.println();
    }

    public void printTruck(Truck truck) {
        if (truck == null) {
            return;
        }
        System.out.println("Manufacturer: " + truck.getManufacturer());
        System.out.println("Type: " + truck.getType());
        System.out.println("Engine: " + truck.getEngine().toString());
        System.out.println("Color: " + truck.getColor());
        System.out.println("Load capacity: " + truck.getLoadCapacity());
        System.out.println("Price: " + truck.getPrice() + " $");
        System.out.println();
    }

    public static void check(PassengerCar passengerCar) {
        if (passengerCar == null) {
            return;
        }
        if (passengerCar.getCount() >= 1 && passengerCar.getEngine().getPower() > 200) {
            System.out.println("The car is ready for sale");
        } else if (passengerCar.getCount() < 1 && passengerCar.getEngine().getPower() > 200) {
            System.out.println("Power is more than 200, but car is not available, because count is less than 1");
        } else if (passengerCar.getCount() >= 1 && passengerCar.getEngine().getPower() < 200) {
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
                printPass(createPassengerCar());
            }
            return counter;
        }
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