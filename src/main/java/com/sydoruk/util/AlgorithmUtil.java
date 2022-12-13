package com.sydoruk.util;

import com.sydoruk.model.Car;

public class AlgorithmUtil {

    public Car[] bubbleSort(Car[] cars){
        boolean change = true;
        while (change) {
            change = false;
            for (int i = 0; i < cars.length - 1; i++) {
                if ((cars[i].getId().compareTo(cars[i+1].getId())) > 0) {
                    Car min = cars[i + 1];
                    cars[i + 1] = cars[i];
                    cars[i] = min;
                    change = true;
                }
            }
        }
        return cars;
    }

    public int binarySearch(Car[] cars, String id){
        int indexFirst = 0;
        int indexLast = cars.length-1;
        while(indexFirst <= indexLast) {
            int indexMiddle = (indexFirst + indexLast) / 2;
            if ((cars[indexMiddle].getId().compareTo(id)) == 0) {
                return indexMiddle;
            } else if ((cars[indexMiddle].getId().compareTo(id)) < 0) {
                indexFirst = indexMiddle + 1;
            } else if ((cars[indexMiddle].getId().compareTo(id)) > 0 ) {
                indexLast = indexMiddle - 1;
            }
        }
        return -1;
    }
}