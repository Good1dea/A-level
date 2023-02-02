package com.sydoruk.model;

import java.util.Random;

public class Truck extends Car {

    private int loadCapacity;
    Random random = new Random();

    public Truck(){
        super();
        this.type = Type.TRUCK;
        this.loadCapacity = random.nextInt(500, 10001);
    }

    public Truck(final String id, final int loadCapacity){
        this.id = id;
        this.type = Type.TRUCK;
        this.loadCapacity = loadCapacity;
    }

    public void setLoadCapacity(int loadCapacity){
        this.loadCapacity = loadCapacity;
    }

    public int getLoadCapacity() {
        return loadCapacity;
    }

    @Override
    public void restore() {
        super.setCount(50);
    }
}
