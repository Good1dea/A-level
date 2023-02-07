package com.sydoruk.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Random;
import java.util.UUID;

@Setter
@Getter
public class Engine {

    private final String id;
    private final int power;
    private final String type;
    private final String[] types = {"GASOLINE","ELECTRIC","DIESEL"};
    private final Random random = new Random();

    public Engine(){
        this.id = UUID.randomUUID().toString();
        this.power = random.nextInt(0,1001);
        this.type = types[random.nextInt(types.length)];
    }

    public Engine(String id, int power, String type){
        this.id = id;
        this.power = power;
        this.type = type;
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
