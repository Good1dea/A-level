package com.sydoruk.container;

import com.sydoruk.model.Car;

import java.util.Comparator;

public  class CarComparator <T extends  Car> implements Comparator<T> {
    @Override
    public int compare(T carFirst, T carSecond) {
        if (carFirst != null && carSecond != null) {
            int result = 0;
            if(carFirst.getCount() > carSecond.getCount()) {
                result = 1;
            } else if(carFirst.getCount() < carSecond.getCount()){
                result = -1;
            }
            return result;
        }
        return -2;
    }
}