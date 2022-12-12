package com.sydoruk.model;

import java.util.Random;

public enum Type {
    CAR,
    TRUCK,
    NULL;

    private static final Random random = new Random();

    public static Type randomType() {
        Type[] types = values();
        return types[random.nextInt(types.length)];
    }
}