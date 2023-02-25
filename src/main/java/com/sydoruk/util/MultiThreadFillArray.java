package com.sydoruk.util;

import java.util.Random;

public class MultiThreadFillArray {

    private static MultiThreadFillArray instance;
    private final int[] array;
    private final boolean[] used;

    private MultiThreadFillArray() {
        array = new int[100];
        used = new boolean[1000];
    }

    public static synchronized MultiThreadFillArray getInstance() {
        if (instance == null) {
            instance = new MultiThreadFillArray();
        }
        return instance;
    }

    public int[] fillArray() {
        Thread[] threads = new Thread[10];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(new ArrayFiller(i));
            threads[i].start();
        }
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return array;
    }


    private class ArrayFiller implements Runnable {

        private final int index;
        private final Random random = new Random();

        public ArrayFiller(int index) {
            this.index = index;
        }

        @Override
        public synchronized void run() {
            for (int i = 0; i < 10; i++) {
                int value;
                boolean unique = false;
                while (!unique) {
                    value = random.nextInt(1000);
                    if (!used[value]) {
                        array[index * 10 + i] = value;
                        used[value] = true;
                        unique = true;
                    }
                }
            }
        }
    }
}