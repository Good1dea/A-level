package com.sydoruk.util;

import java.time.LocalTime;
import java.util.Random;
import java.util.concurrent.*;

public class ConcurrencyUtils {

    private static ConcurrencyUtils instance;

    private ConcurrencyUtils() {
    }

    public static synchronized ConcurrencyUtils getInstance() {
        if (instance == null) {
            instance = new ConcurrencyUtils();
        }
        return instance;
    }

    public void executeTasksInParallel() {
        ExecutorService executor = Executors.newFixedThreadPool(3);
        for (int i = 1; i <= 3; i++) {
            final int threadNum = i;
            executor.submit(() -> {
                for (int j = 0; j < 5; j++) {
                    System.out.println("Thread " + threadNum + ", iteration " + j);
                }
            });
        }
        executor.shutdown();
    }

    public void executeTasksInPhases() {
        final int numberOfThreads = 4;
        final Phaser phaser = new Phaser(numberOfThreads);
        Thread[] threads = new Thread[numberOfThreads];
        for (int i = 0; i < numberOfThreads; i++) {
            final int threadNum = i;
            threads[i] = new Thread(() -> {
                for (int j = 1; j <= 4; j++) {
                    phaser.arriveAndAwaitAdvance();
                    executePhase("Thread " + (threadNum + 1), "Phase " + j);
                }
            });
            threads[i].start();
        }

        for (int i = 0; i < numberOfThreads; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void executePhase(String threadName, String phaseName) {
        final Random rand = new Random();
        int sleepTime = rand.nextInt(10) + 1;
        System.out.println(threadName + ", " + getCurrentTime() + ", " + phaseName + " started, sleeping for " + sleepTime + " seconds");
        try {
            Thread.sleep(sleepTime * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(threadName + ", " + getCurrentTime() + ", " + phaseName + " completed");
    }

    private static String getCurrentTime() {
        return LocalTime.now().toString();
    }
}