package com.sydoruk.repository;

import com.sydoruk.model.Car;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class CarMap implements InterfaceRepository<Car> {
        private final Map<String, Car> cars = new HashMap<>();
        private static CarMap instance;

        private CarMap() {
        }

        public static CarMap getInstance() {
            if (instance == null) {
                instance = new CarMap();
            }
            return instance;
        }

        @Override
        public void save(Car car) {
            if (cars.get(car.getId()) != null) {
                cars.get(car.getId()).setCount(car.getCount());
            } else {
                cars.put(car.getId(), car);
            }
        }

        @Override
        public List<Car> getAll() {
            return cars.values().stream().toList();
        }

        @Override
        public Optional<Car> getById(final String id) {
            return Optional.ofNullable(cars.get(id));
        }

        @Override
        public void delete(final String id) {
            if (cars.containsKey(id)) {
                cars.remove(id);
                System.out.println("Car with id " + id + " delete");
            } else {
                System.out.println("Car with id " + id + " not find");
            }
        }
}