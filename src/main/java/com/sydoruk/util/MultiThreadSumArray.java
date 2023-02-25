package com.sydoruk.util;

import java.util.Arrays;

public class MultiThreadSumArray {

    private static MultiThreadSumArray instance;
    private final int[] array;
    private final int numThreads;

    private MultiThreadSumArray(final int[] array, int numThreads) {
        this.array = array;
        this.numThreads = numThreads;
    }

    public static synchronized MultiThreadSumArray getInstance(final int[] array, final int numThreads) {
        if (instance == null) {
            instance = new MultiThreadSumArray(array, numThreads);
        }
        return instance;
    }

    public int sumInParallel() {
        int chunkSize = array.length / numThreads;
        ArraySumThread[] threads = new ArraySumThread[numThreads];
        for (int i = 0; i < numThreads; i++) {
            int startIndex = i * chunkSize;
            int endIndex;
            if (i == (numThreads - 1)) {
                endIndex = array.length;
            } else {
                endIndex = (i + 1) * chunkSize;
            }
            threads[i] = new ArraySumThread(array, startIndex, endIndex);
            threads[i].start();
        }
        int sum = 0;
        for (ArraySumThread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            sum += thread.getSum();
        }
        return sum;
    }

    private class ArraySumThread extends Thread {
        private final int[] array;
        private final int startIndex;
        private final int endIndex;
        private int sum;

        public ArraySumThread(int[] array, int startIndex, int endIndex) {
            this.array = array;
            this.startIndex = startIndex;
            this.endIndex = endIndex;
        }

        @Override
        public void run() {
            sum = Arrays.stream(array, startIndex, endIndex).sum();
        }

        public int getSum() {
            System.out.println("the sum of array elements from " + startIndex + " to " + endIndex + " = " + sum);
            return sum;
        }
    }
}

