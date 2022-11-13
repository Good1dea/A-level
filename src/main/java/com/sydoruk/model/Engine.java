package com.sydoruk.model;

import java.util.Random;

public class Engine {

    private final int power;
    private final String type;
    private final String[] types = {"GASOLINE","ELECTRIC","DIESEL"};
    private final Random random = new Random();

    public Engine(){
        power = random.nextInt(0,1001);
        type = types[random.nextInt(types.length)];
    }

    public int getPower(){
        return power;
    }

    public String getType(){
        return type;
    }

    @Override
    public String toString() {
        return String.format("%s %d", type, power);
    }
}
