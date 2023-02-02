package com.sydoruk.model;

import com.sydoruk.service.CarService;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Setter
@Getter
public class Order {

    private final String id;
    private List<Car> cars;
    private final String date;

    public Order() {
        this.id = UUID.randomUUID().toString();
        date = java.time.LocalDate.now() + "  " + java.time.LocalTime.now().withNano(0);
    }

    public Order(String id, String date){
        this.id = id;
        this.date = date;
    }

    public void addCar(Car car){
        if(car != null){
            cars.add(car);
        }
    }

    public static void printOrder(Order order){
        System.out.println("Order id: " + order.getId() + "; date: " + order.getDate());
        System.out.println("Cars in order:");
        List<Car> cars = order.getCars();
        for (Car car : cars){
            CarService.printCar(car);
        }
    }
}