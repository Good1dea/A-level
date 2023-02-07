package com.sydoruk.model;

import com.sydoruk.service.CarService;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
public class Order {

    private final String id;
    private List<Car> cars;
    private final Date date;

    public Order() {
        this.id = UUID.randomUUID().toString();
        date = Date.valueOf(LocalDate.now());
    }

    public Order(String id, Date date){
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