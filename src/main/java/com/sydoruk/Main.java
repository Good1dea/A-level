package com.sydoruk;

import com.sydoruk.repository.CarArrayRepository;
import com.sydoruk.service.CarService;
import com.sydoruk.util.RandomGenerator;

public class Main {
    public static void main(final String[] args) {
        CarService carService = new CarService(new CarArrayRepository());
        RandomGenerator random = new RandomGenerator();
        System.out.println(carService.create(random));
    }
}