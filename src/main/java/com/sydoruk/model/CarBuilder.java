package com.sydoruk.model;

public interface CarBuilder {

    public CarBuilder addManufacturer(String manufacturer);

     public CarBuilder addEngine(Engine engine);

    public CarBuilder addColor(Color color);

    public CarBuilder addCount(int count);

    public CarBuilder addPrice(int price);

    public boolean checkPrice();

    public Car build();
}