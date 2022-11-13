package com.sydoruk.model;

import java.util.Random;

public enum Color {
    GREEN,
    BLUE,
    BLACK,
    WHITE,
    GREY,
    RED,
    YELLOW,
    TURQUOISE,
    PURPLE;

    private static final Random random = new Random();

    public static Color randomColor(){
        Color[] colors = values();
        return colors[random.nextInt(colors.length)];
    }
}

