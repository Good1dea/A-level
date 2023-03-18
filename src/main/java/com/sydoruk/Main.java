package com.sydoruk;

import com.sydoruk.util.ConcurrencyUtils;

public class Main {

    public static void main(String[] arg) {
        ConcurrencyUtils.getInstance().executeTasksInParallel();
        ConcurrencyUtils.getInstance().executeTasksInPhases();

    }
}