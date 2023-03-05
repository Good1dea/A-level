package com.sydoruk.util;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class ConcurrencyUtils {

    private static ConcurrencyUtils instance;
    private final int numberOfThreads = 4;
    private final int phases = 4;
    private final CyclicBarrier cyclicBarrier = new CyclicBarrier(numberOfThreads);

    private ConcurrencyUtils() {
    }

    public static synchronized ConcurrencyUtils getInstance() {
        if (instance == null) {
            instance = new ConcurrencyUtils();
        }
        return instance;
    }
        
    public void executeTasksInParallel() {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                System.out.println("Thread 1, iteration " + i);
            }
        });

        Thread t2 = new Thread(() -> {
        for (int i = 0; i < 5; i++) {
                System.out.println("Thread 2, iteration " + i);
            }
        });

        Thread t3 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                System.out.println("Thread 3, iteration " + i);
            }
        });
    
        t1.start();
        t2.start();
        t3.start();
    }

    public void executeTasksInPhases() {
        Thread[] threads = new Thread[numberOfThreads];
        for (int i = 0; i < numberOfThreads; i++) {
            final int threadNumber = i;
            threads[i] = new Thread(() -> {
                for (int j = 1; j <= phases; j++) {
                    executePhase("Thread " + (threadNumber + 1), "Phase " + j);
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
        int sleepTime = rand.nextInt(1,11);
        System.out.println(threadName + ", " + getCurrentTime() + ", " + phaseName + " started, sleeping for " + sleepTime + " seconds");
        try {
            Thread.sleep(sleepTime * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(threadName + ", " + getCurrentTime() + ", " + phaseName + " completed");

        try {
            cyclicBarrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
    }

    private static String getCurrentTime() {
        return java.time.LocalTime.now().toString();
    }
}