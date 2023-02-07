package com.sydoruk.model;

import java.util.Random;

public enum Manufacturer {

    SUZUKI,
    AUDI,
    ZAZ,
    FORD,
    TOYOTA,
    FIAT,
    VOLVO,
    TESLA,
    VOLKSWAGEN,
    SUBARU,
    DODGE,
    FERRARI,
    CADILLAC,
    BMW,
    BUGATTI,
    JAGUAR;

    private static final Random random = new Random();

    public static Manufacturer randomManufacturer(){
        Manufacturer[] manufacturers = values();
        return manufacturers[random.nextInt(manufacturers.length)];
    }
}