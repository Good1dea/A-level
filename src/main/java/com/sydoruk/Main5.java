package com.sydoruk;

import java.util.Arrays;
import java.util.Random;

public class Main5 {
    public static void main(String[] args) {
        System.out.println("Task 1");
        task1();
        System.out.println("Task 2");
        task2();
        System.out.println("Task 3");
        task3();
        System.out.println("Task 4");
        task4();
        System.out.println("Bubble sort");
        bubbleSort();
    }

    private static void task1() {
        int[] numbers = new int[12];
        Random random = new Random();
        int count = 0;
        int max = -15;
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = random.nextInt(-15,16);
        }
        System.out.println("Initial array " + Arrays.toString(numbers));
        for (int i = 0; i < numbers.length; i++){
            if(numbers[i] >= max){
                max = numbers[i];
                count = i;
            }else{
                continue;
            }
        }
        System.out.printf("Max value %d, last index %d%n", numbers[count], count);
    }

    private static void task2() {
        int[] numbers = new int[8];
        Random random = new Random();
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = random.nextInt(1, 11);
        }
        System.out.println("Initial array " + Arrays.toString(numbers));
        for (int i = 0; i < numbers.length; i++) {
            if (i % 2 != 0) {
                numbers[i] = 0;
            } else {
                continue;
            }
        }
        System.out.println("Array after replacing the elements " + Arrays.toString(numbers));
    }

    private static void task3() {
        int[] numbers = new int[4];
        Random random = new Random();
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = random.nextInt(9, 100);
        }
        System.out.println("Initial array " + Arrays.toString(numbers));
        boolean isIncreas = false;
        for (int i = 0; i < numbers.length - 1; i++) {
            if (numbers[i] < numbers[i + 1]) {
                isIncreas = true;
            } else {
                isIncreas = false;
                break;
            }
        }
        System.out.println(isIncreas ? "Array is an increasing sequence" : "Array is not an increasing sequence");
    }

    private static void task4() {
        int[] numbers1 = new int[5];
        int[] numbers2 = new int[5];
        Random random = new Random();
        for (int i = 0; i < numbers1.length; i++) {
            numbers1[i] = random.nextInt(0, 6);
        }
        for (int i = 0; i < numbers2.length; i++) {
            numbers2[i] = random.nextInt(0, 6);
        }
        System.out.println("Initial first array " + Arrays.toString(numbers1));
        System.out.println("Initial second array " + Arrays.toString(numbers2));
        int sum1 = 0;
        int sum2 = 0;
        for (int number1 : numbers1) {
            sum1 += number1;
        }
        for (int number2 : numbers2) {
            sum2 += number2;
        }
        double average1 = (sum1*1.0) / numbers1.length;
        double average2 = (sum2 *1.0) / numbers2.length;
        if(average1 > average2) {
            System.out.println("Average arithmetic value of the first array " + average1 +
                    " is greater than that of the second array " + average2);
        }else if(average2 > average1) {
            System.out.println("Average arithmetic value of the second array " + average2 +
                    " is greater than that of the first array " + average1);
        }else{
            System.out.println("Average arithmetic value of the first and second arrays is equal " +
                    average1);
        }
    }

    private static void bubbleSort() {
        final Random random = new Random();
        final int[] numbers = new int[10];
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = random.nextInt(100);
        }
        System.out.println("Initial array " + Arrays.toString(numbers));
        boolean change = true;
        while (change){
            change = false;
            for (int i = 0; i < numbers.length - 1; i++) {
                if (numbers[i] > numbers[i+1]) {
                    int temp = numbers[i];
                    numbers[i] = numbers[i+1];
                    numbers[i+1] = temp;
                    change = true;
                }
            }
        }
        System.out.println("Sorted array " + Arrays.toString(numbers));
    }
}
