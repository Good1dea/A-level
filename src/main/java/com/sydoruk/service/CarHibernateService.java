package com.sydoruk.service;

import com.sydoruk.model.*;
import com.sydoruk.repository.CarHibernateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.Random;

public class CarHibernateService {

    private final CarHibernateRepository repository;
    private final Random random = new Random();
    final static Logger logger = LoggerFactory.getLogger(CarHibernateService.class);
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
        } else {
            logger.error("Incorrect car`s type: {}", type);
        }
        if (car != null) {
            car.setEngine(createEngine());
            car.setManufacturer(Manufacturer.randomManufacturer());
            car.setColor(Color.randomColor());
            car.setPrice(random.nextInt(1000, 100001));
            car.setCount(random.nextInt(1, 6));
        } else {
            logger.warn("car is not created");
        }
        return car;
    }

    private EngineHibernate createEngine (){
        EngineHibernate engine = new EngineHibernate();
        engine.setPower(random.nextInt(0,1001));
        engine.setType(types[random.nextInt(types.length)]);
        logger.info("Create {} engine {} power", engine.getType(), engine.getPower());
        return engine;
    }

    public CarHibernate createAndSave() {
        final CarHibernate car = createCar(Type.randomType());
        if(car != null){
            repository.save(car);
        } else {
            logger.error("Car cannot be saved");
        }
        return car;
    }

    public Optional<CarHibernate> getById(final String id) {
        logger.info("Search for car with id {}", id);
        return repository.getById(id);
    }

    public List<CarHibernate> getAll() {
        List<CarHibernate> cars = repository.getAll();
        if (cars.isEmpty()){
            logger.info("There are no cars in DB");
        }
        return cars;
    }

    public void delete(final String id) {
        logger.info("Delete car with id {}", id);
        if (!(id == null || id.isEmpty())) {
            repository.delete(id);
        } else {
            logger.warn("Incorrect id for deleting car");
        }

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
