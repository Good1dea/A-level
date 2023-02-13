package com.sydoruk.model;

public class TruckCarBuilder implements CarBuilder {

    Car car = new Truck();

    @Override
    public CarBuilder addManufacturer(String manufacturer) {
        car.manufacturer = manufacturer;
        return this;
    }

    @Override
    public CarBuilder addEngine(Engine engine) {
        car.engine = engine;
        return this;
    }

    @Override
    public CarBuilder addColor(Color color) {
        car.color = color;
        return this;
    }

    @Override
    public CarBuilder addCount(int count) {
        car.count = count;
        return this;
    }

    @Override
    public CarBuilder addPrice(int price) {
        car.price = price;
        return this;
    }

    @Override
    public boolean checkPrice(){
        return car.price > 1000;
    }

    @Override
    public Car build() {
        if (car.count < 1){
            throw new IllegalArgumentException("Incorrect count, car was not created");
        } else if (checkPrice()) {
            System.out.println("Price is above 1000");
        }
        return car;
    }
}