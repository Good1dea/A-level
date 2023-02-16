package com.sydoruk.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CarFactory {

    final static Logger logger = LoggerFactory.getLogger(CarFactory.class);

    public CarBuilder createCar(final Type type){

        CarBuilder builder = null;
        switch (type){
            case CAR:
                builder = new PassengerCarBuilder();
                break;
            case TRUCK:
                builder = new TruckCarBuilder();
                break;
            default:
                logger.warn("Unknown type {}, car not created", type);
        }
        return builder;
    }

}