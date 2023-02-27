package com.sydoruk.model;

import lombok.Getter;
import lombok.Setter;
import org.bson.BsonDocument;
import org.bson.BsonInt32;
import org.bson.BsonString;

import java.util.Random;
import java.util.UUID;

@Setter
@Getter
public class Engine {

    private final String id;
    private int power;
    private String type;
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

    public BsonDocument toBsonDocument() {
        BsonDocument bson = new BsonDocument()
                .append("id", new BsonString(id))
                .append("power", new BsonInt32(power))
                .append("type", new BsonString(type));
        return bson;
    }

}
