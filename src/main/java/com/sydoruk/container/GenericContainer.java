package com.sydoruk.container;

import com.sydoruk.model.Car;

import java.util.Random;

public class GenericContainer<T extends Car> {

    private T obj;
    final private Random random = new Random();
    public GenericContainer(final T object) {
        this.obj = object;
    }

    public void print(){
        if (obj == null) {
            System.out.println("null");
        } else {
            System.out.println("ID: " + obj.getId());
            System.out.println("Count: " + obj.getCount());
        }
    }

    public void increaseCount(){
        int rnd = random.nextInt(100,301);
        obj.setCount(obj.getCount() + rnd);
    }

    public <V extends Number> void increaseCount(V count){
        obj.setCount(obj.getCount() + count.intValue());
    }
}
