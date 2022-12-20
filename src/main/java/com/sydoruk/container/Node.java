package com.sydoruk.container;

import com.sydoruk.model.Car;

public class Node <T extends Car>{
    T car;
    Node previous;
    Node next;
    public Node(T car){
        this.car = car;
    }

    public T getCar(){
        return car;
    }

}