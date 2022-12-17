package com.sydoruk.service;

import com.sydoruk.exception.UserInputException;
import com.sydoruk.model.*;
import com.sydoruk.repository.CarArrayRepository;
import com.sydoruk.util.RandomGenerator;

import java.util.Arrays;
import java.util.Optional;
import java.util.Random;

public class CarService {

    private final CarArrayRepository carArrayRepository;
    private final Random random = new Random();
    private final String[] manufacturers = {"Suzuki", "Audi", "ZAZ", "Ford", "Toyota", "Fiat", "Volvo", "Tesla",
            "Volkswagen", "Subaru", "Dodge", "Ferrari", "Cadillac", "BMW", "Bugatti", "Jaguar"};

    public CarService(final CarArrayRepository carArrayRepository) {
        this.carArrayRepository = carArrayRepository;
    }

    public void printManufacturerAndCount(final Car car){
        Optional.ofNullable(car).ifPresent(manufacturerAndCount ->
            System.out.println("Manufacturer: " + car.getManufacturer() + "; Count: " + car.getCount()));
    }

    public void printColor (final Car car){
        System.out.println("Color: " + Optional.ofNullable(car).orElse(createCar(Type.CAR)).getColor());
    }

    public void checkCount(final Car car)throws UserInputException {
        Car carOptional = Optional.ofNullable(car)
                .filter(c -> c.getCount() > 10)
                .orElseThrow(UserInputException::new);
        printManufacturerAndCount(carOptional);
    }

    public void printEngineInfo (final Car car){
        Car carOptional = Optional.ofNullable(car).orElseGet(() -> {
                    System.out.println("Create new car");
                    return createCar(Type.CAR);
                });
        Optional.ofNullable(carOptional).map(Car::getEngine).ifPresent(power ->
                System.out.println("Engine power: " + carOptional.getEngine().getPower()));
    }

    public void printInfo(final Car car){
        Optional.ofNullable(car).ifPresentOrElse(print -> printCar(car), () -> printCar(createCar(Type.CAR)));
    }

    public Car createCar(final Type type) {
        Car car = null;
        if (type == Type.CAR) {
                car = new PassengerCar();
                ((PassengerCar) car).setPassengerCount(random.nextInt(9));
        } else if (type == Type.TRUCK) {
                car = new Truck();
                ((Truck) car).setLoadCapacity(random.nextInt(500, 10000));
        }
            Engine engine = new Engine();
            car.setType(type);
            car.setEngine(engine);
            car.setManufacturer(manufacturers[random.nextInt(0, manufacturers.length)]);
            car.setColor(Color.randomColor());
            car.setPrice(random.nextInt(1000, 100001));
            car.restore();
            carArrayRepository.save(car);
            return car;
    }

    public void printCar(Car car) {
        if (car == null) {
            System.out.println("null");
        } else {
            System.out.println("ID: " + car.getId());
            System.out.println("Manufacturer: " + car.getManufacturer());
            System.out.println("Type: " + car.getType());
            System.out.println("Engine: " + car.getEngine().toString());
            System.out.println("Color: " + car.getColor());
            System.out.println("Count: " + car.getCount());
            if (car.getType() == Type.CAR) {
                System.out.println("Passengers: " + ((PassengerCar) car).getPassengerCount());
            } else {
                System.out.println("Load capacity: " + ((Truck) car).getLoadCapacity());
            }
            System.out.println("Price: " + car.getPrice() + " $");
            System.out.println();
        }
    }

    public boolean carEquals(Car carFirst, Car carSecond) {
        if (carFirst == null || carSecond == null) return false;
        if (carFirst == carSecond) return true;
        if (carSecond.getType() != carFirst.getType()) return false;
        if (carFirst.hashCode() != carSecond.hashCode()) return false;
        return carFirst.equals(carSecond);
    }

    public static void check(Car car) {
        if (car == null) {
            return;
        }
        if (car.getCount() >= 1 && car.getEngine().getPower() > 200) {
            System.out.println("The car is ready for sale");
        } else if (car.getCount() < 1 && car.getEngine().getPower() > 200) {
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
                printCar(createCar(Type.randomType()));
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