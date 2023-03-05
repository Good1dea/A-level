package com.sydoruk.repository;
import com.mongodb.client.*;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.sydoruk.model.Order;
import org.bson.Document;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


public class OrderMongoRepository implements InterfaceRepository<Order> {
    private static OrderMongoRepository instance;
    private final MongoDatabase database;
    private final MongoClient mongoClient;
    private final MongoCollection<Document> collection;

    public static OrderMongoRepository getInstance(){
        if(instance == null){
            instance = new OrderMongoRepository();
        }
        return instance;
    }
    private OrderMongoRepository() {
        mongoClient = MongoClients.create("mongodb://root:example@wmt.sya.pp.ua:7712");
        database = mongoClient.getDatabase("AutoMongoRepo");
        collection = database.getCollection("Order");
    }
    public void closeConnection(){
        if(mongoClient != null){
            mongoClient.close();
        }
    }

    @Override
    public void save(final Order order) {
        Document orderDoc = new Document("_id", order.getId())
                .append("date", order.getDate())
                .append("id_auto", order.getCarId());
        collection.insertOne(orderDoc);
    }

    @Override
    public Optional<Order> getById(final String id) {
        return Optional.ofNullable(createOrder(id));
    }

    @Override
    public List<Order> getAll() {
        List<Order> orders = new ArrayList<>();
        for (Document doc : collection.find()) {
            orders.add(createOrder(doc.getString("_id")));
        }
        return orders;
    }

    @Override
    public void delete(String id){
        collection.deleteOne(Filters.eq("_id", id));
    }

    private Order createOrder(String id) {
        Order order = null;
        AggregateIterable<Document> result = collection.aggregate(Arrays.asList(
                Aggregates.match(Filters.eq("_id", id)),
                Aggregates.lookup("Auto", "id_auto", "_id", "auto"),
                Aggregates.unwind("$auto")
        ));

        for (Document doc : result) {
            order = createOrderFromBD(doc, order);
        }
        return  order;
    }

    private Order createOrderFromBD(final Document doc, Order existsOrder){
        Order order = existsOrder;
        Document autoDoc = doc.get("auto", Document.class);
        Date date = new Date(doc.getDate("date").getTime());
        if(order == null) {
            order = new Order(doc.getString("_id"), date);
            order.setCarId(doc.getList("id_auto", String.class));
        }
        if(autoDoc != null) {
            order.addCar(CarMongoRepository.getInstance().createCarFromDoc(autoDoc));
        }
        return order;
    }

}
