package com.sydoruk.model;

public class Truck extends Car {

    private int loadCapacity;

    public Truck(){
        super();
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
        System.out.println("Count: " + count);
    }
}
