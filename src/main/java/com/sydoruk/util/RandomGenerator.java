package com.sydoruk.util;

import java.util.Date;

public class RandomGenerator {

    private final Date date = new Date();

    public RandomGenerator() {
    }

    public int random() {
        long time = date.getTime();
        return Integer.parseInt(String.valueOf(time).substring(String.valueOf(time).length() - 1));
    }
}