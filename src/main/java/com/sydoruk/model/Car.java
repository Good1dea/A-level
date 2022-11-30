package com.sydoruk.model;

import java.util.UUID;
import java.util.Random;

public abstract class Car {

    protected String manufacturer;
    protected Engine engine;
    protected Color color;
    protected Type type;
    protected int count;
    protected int price;
    protected String id;

    public Car(){
    }

    public Car(String manufacturer, Engine engine, Color color, Type type) {
        this.id = UUID.randomUUID().toString();
        this.manufacturer = manufacturer;
        this.engine = engine;
        this.color = color;
        this.type = type;
        count = 1;
        Random random = new Random();
        price = random.nextInt();
    }

    public String getId() {
        return id;
    }

    public void setManufacturer(final String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setEngine(final Engine engine) {
        this.engine = engine;
    }

    public Engine getEngine() {
        return engine;
    }

    public void setColor(final Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public void setType(final Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
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