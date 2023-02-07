package com.sydoruk.service;

import com.sydoruk.annotation.Singleton;
import com.sydoruk.exception.UserInputException;
import com.sydoruk.model.*;
import com.sydoruk.repository.CarArrayRepository;
import com.sydoruk.repository.InterfaceRepository;
import com.sydoruk.util.RandomGenerator;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Singleton
public class CarService {

    private final CarArrayRepository carArrayRepository = new CarArrayRepository();

    private final Random random = new Random();
    private final String[] manufacturers = {"Suzuki", "Audi", "ZAZ", "Ford", "Toyota", "Fiat", "Volvo", "Tesla",
            "Volkswagen", "Subaru", "Dodge", "Ferrari", "Cadillac", "BMW", "Bugatti", "Jaguar"};

  /*  @Autowired(classImplementation = CarArrayRepository.class)
    public CarService(final CarArrayRepository carArrayRepository) {
        this.carArrayRepository = carArrayRepository;
    } */

    public CarService(){

    }

    public CarService(CarArrayRepository repository) {

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
        } else if (type == Type.TRUCK) {
                car = new Truck();
        }
        Engine engine = new Engine();
        car.setEngine(engine);
        car.setManufacturer(manufacturers[random.nextInt(0, manufacturers.length)]);
        car.setColor(Color.randomColor());
        car.setPrice(random.nextInt(1000, 100001));
            //car.restore();
        car.setCount(random.nextInt(1, 6));
        //System.out.println(car.toString());
           // carArrayRepository.save(car);
        return car;
    }

    public static void printCar(Car car) {
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
        final Car[] all = carArrayRepository.getAllCars();
        System.out.println(Arrays.toString(all));
    }

    public Car[] getAll() {
        return carArrayRepository.getAllCars();
    }

    public Car find(final String id) {
        if (id == null || id.isEmpty()) {
            return null;
        }
        return carRepository.getById(id);
    }

    public void delete(final String id) {
        if (id != null && !id.isEmpty()) {
            carRepository.delete(id);
        }
    }

    public HashMap<String, Integer> getMapManufacturer(final Car[] cars){
        HashMap<String, Integer> map = null;
        if(cars != null) {
            map = new HashMap<>();
            for (Car car : cars) {
                if(map.containsKey(car.getManufacturer())){
                    map.put(car.getManufacturer(), map.get(car.getManufacturer()) + car.getCount());
                } else {
                    map.put(car.getManufacturer(), car.getCount());
                }
            }
        }
        return map;
    }

    public HashMap<Integer, LinkedList> getMapEngineType(final Car[] cars){
        HashMap<Integer, LinkedList> map = null;
        LinkedList<Car> listCar;
        if(cars != null) {
            map = new HashMap<>();
            for (Car car : cars) {
                int power = car.getEngine().getPower();
                if(map.containsKey(power)){
                    listCar = map.get(power);
                    listCar.add(car);
                } else {
                    map.put(power, new LinkedList<>());
                    listCar = map.get(power);
                    listCar.add(car);
                }
            }
        }
        return map;
    }

    public void findManufacturerByPrice (final List<? extends Car> cars, int price){
        cars.stream()
                .filter(p -> p.getPrice() > price)
                .map(Car::getManufacturer)
                .collect(Collectors.toList())
                .forEach(System.out::println);
    }

    public int countSum (final List<? extends Car> cars) {
        int sum = cars.stream()
                .map(Car::getCount)
                .reduce(0, Integer::sum);
        return sum;
    }

    public Map<String, Type> mapToMap(final List<? extends Car> cars){
        LinkedHashMap <String, Type> mapCars = cars.stream()
                .distinct()
                .sorted(Comparator.comparing(Car::getManufacturer))
                .collect(Collectors.toMap(Car::getId, Car::getType, (key1, key2) -> key1, LinkedHashMap::new));
        return mapCars;
    }

    public Map<Integer, Long> statistic(final List<? extends Car> cars){
        Map<Integer, Long> statistic = cars.stream()
                .collect(Collectors.groupingBy(Car::getPrice, Collectors.counting()));
        return statistic;
    }

    public boolean priceCheck(final List<? extends Car> cars, int price){
        Predicate <Car> predicatePrice = p -> p.getPrice() > price;
        return cars.stream()
                .allMatch(predicatePrice);
    }

    public Car mapToObject (final Map<String, Object> config){
        Function<Map, Car> function = map -> createCar((Type) config.get("Type"));
        Car car = function
                .andThen(newCar -> {
                    newCar.setColor((Color) config.get("Color"));
                    return newCar;
                }).andThen(newCar -> {
                        newCar.setPrice((int)config.get("Price"));
                        return newCar;
                }).andThen(newCar -> {
                    newCar.setManufacturer((String) config.get("Manufacturer"));
                    return newCar;
                }).apply(config);
        return car;
    }

    public Map<Color,Long> innerList(final List<List<Car>> cars, int price){
        Map<Color, Long> sortedCars = cars.stream()
                .flatMap(list -> list.stream())
                .sorted(Comparator.comparing(Car::getColor))
                .peek(System.out::println)
                .filter(p -> p.getPrice() > price)
                .collect(Collectors.groupingBy(Car::getColor, Collectors.counting()));
        return sortedCars;
    }
}