package com.sydoruk;

import com.sydoruk.util.MultiThreadFillArray;
import com.sydoruk.util.MultiThreadSumArray;

import java.util.Arrays;

public class Main {

    public static void main(String[] arg) {
        final int[] array = MultiThreadFillArray.getInstance().fillArray();
        System.out.println("Array contents:");
        System.out.println(Arrays.toString(array));
        int numThreads = 4;
        final int sum = MultiThreadSumArray.getInstance(array, numThreads).sumInParallel();
        System.out.println();
        System.out.println("Sum of array elements in " + numThreads + " threads: " + sum);
    }
}