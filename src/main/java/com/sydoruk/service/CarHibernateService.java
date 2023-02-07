package com.sydoruk.service;

import com.sydoruk.model.*;
import com.sydoruk.repository.CarHibernateRepository;

import java.util.List;
import java.util.Optional;
import java.util.Random;

public class CarHibernateService {

    private final CarHibernateRepository repository;
    private final Random random = new Random();
    private final String[] types = {"GASOLINE","ELECTRIC","DIESEL"};

    public CarHibernateService(final  CarHibernateRepository repository) {
        this.repository = repository;
    }

    public CarHibernate createCar(final Type type) {
        CarHibernate car = null;
        if (type == Type.CAR) {
            car = new PassengerCarHibernate();
        } else if (type == Type.TRUCK) {
            car = new TruckHibernate();
        }
        car.setEngine(createEngine());
        car.setManufacturer(Manufacturer.randomManufacturer());
        car.setColor(Color.randomColor());
        car.setPrice(random.nextInt(1000, 100001));
        car.setCount(random.nextInt(1, 6));
        return car;
    }

    private EngineHibernate createEngine (){
        EngineHibernate engine = new EngineHibernate();
        engine.setPower(random.nextInt(0,1001));
        engine.setType(types[random.nextInt(types.length)]);
        return engine;
    }

    public CarHibernate createAndSave() {
        final CarHibernate car = createCar(Type.randomType());
        repository.save(car);
        return car;
    }

    public Optional<CarHibernate> getById(final String id) {
        return repository.getById(id);
    }

    public List<CarHibernate> getAll() {
        return repository.getAll();
    }

    public void delete(final String id) {
        repository.delete(id);
    }

    public void printCar(CarHibernate car) {
        if (car == null) {
            System.out.println("null");
        } else {
            System.out.println("ID: " + car.getId_car());
            System.out.println("Manufacturer: " + car.getManufacturer());
            System.out.println("Type: " + car.getType());
            System.out.println("Engine: " + car.getEngine().toString());
            System.out.println("Color: " + car.getColor());
            System.out.println("Count: " + car.getCount());
            if (car.getType() == Type.CAR) {
                System.out.println("Passengers: " + ((PassengerCarHibernate) car).getPassengerCount());
            } else {
                System.out.println("Load capacity: " + ((TruckHibernate) car).getLoadCapacity());
            }
            System.out.println("Price: " + car.getPrice() + " $");
            System.out.println();
        }
    }
}
