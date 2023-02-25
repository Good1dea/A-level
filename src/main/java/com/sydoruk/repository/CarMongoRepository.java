package com.sydoruk.repository;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.sydoruk.model.*;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CarMongoRepository implements InterfaceRepository <Car> {
    private static CarMongoRepository instance;

    private final MongoClient mongoClient;
    private final MongoDatabase database;
    private final MongoCollection<Document> collection;

    public static CarMongoRepository getInstance(){
        if(instance == null){
            instance = new CarMongoRepository();
        }
        return instance;
    }

    private CarMongoRepository() {
        mongoClient = MongoClients.create("mongodb://root:example@wmt.sya.pp.ua:7712");
        database = mongoClient.getDatabase("AutoMongoRepo");
        collection = database.getCollection("Auto");
    }

    public void closeConnection(){
        if(mongoClient != null){
            mongoClient.close();
        }
    }

    @Override
    public void save(final Car car) {
        Document doc = new Document("_id", car.getId())
                .append("manufacturer", car.getManufacturer())
                .append("engine", car.getEngine().toBsonDocument())
                .append("color", car.getColor().toString())
                .append("count",car.getCount())
                .append("type", car.getType().toString())
                .append("price", car.getPrice());

        switch (car.getType()){
            case CAR -> doc.append("passengerCount", ((PassengerCar)car).getPassengerCount());
            case TRUCK -> doc.append("loadCapacity", ((Truck)car).getLoadCapacity());
        }

        collection.insertOne(doc);
    }

    @Override
    public Optional<Car> getById(final String id) {
        Document doc = collection.find(Filters.eq("_id", id)).first();
        if (doc == null) {
            return null;
        }
        return Optional.ofNullable(createCarFromBD(doc));
    }

    @Override
    public List<Car> getAll() {
        List<Car> cars = new ArrayList<>();
        for (Document doc : collection.find()) {
            cars.add(createCarFromBD(doc));
        }
        return cars;
    }

    @Override
    public void delete(final String id) {
        collection.deleteOne(Filters.eq("_id", id));
    }

    public Car createCarFromDoc(final Document doc){
        return createCarFromBD(doc);
    }
    private Car createCarFromBD(final Document doc){
        Car car = null;
        try {
            if (doc.getString("type").equals(Type.CAR.toString())) {
                car = new PassengerCar(doc.getString("_id"), doc.getInteger("passengerCount"));
            } else {
                car = new Truck(doc.getString("_id"), doc.getInteger("loadCapacity"));
            }

            Document docEngine = (Document) doc.get("engine");
            Engine engine = new Engine(docEngine.getString("id"),
                    docEngine.getInteger("power"),
                    docEngine.getString("type"));
            car.setEngine(engine);

            car.setManufacturer(doc.getString("manufacturer"));
            car.setColor(Color.valueOf(doc.getString("color")));
            car.setPrice(doc.getInteger("price"));
            car.setCount(doc.getInteger("count"));
        }
        catch (Exception e){
            System.out.println("Exception : " + e.getMessage());
        }
        return car;
    }

}