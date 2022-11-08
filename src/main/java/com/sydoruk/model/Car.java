package com.sydoruk.model;

import java.util.Random;

public class Car {

    private String manufacturer;
    private String engine;
    private String color;
    private int count;
    private int price;

    public Car(){
    }

    public Car(String manufacturer, String engine, String color) {
        this.manufacturer = manufacturer;
        this.engine = engine;
        this.color = color;
        count = 1;
        Random random = new Random();
        price = random.nextInt();
    }

    public void setManufacturer(final String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setEngine(final String engine) {
        this.engine = engine;
    }

    public String getEngine() {
        return engine;
    }

    public void setColor(final String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public void setCount(final int count) {
        if (count < 0) {
            this.count = 0;
        } else {
            this.count = count;
        }
    }

    public int getCount() {
        return count;
    }

    public void setPrice(final int price) {
        if (price < 0) {
            this.price = 0;
        } else {
            this.price = price;
        }
    }

    public int getPrice() {
        return price;
    }
}