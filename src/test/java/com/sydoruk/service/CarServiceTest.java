package com.sydoruk.service;

import com.sydoruk.model.Car;
import com.sydoruk.model.PassengerCar;
import com.sydoruk.repository.CarArrayRepository;
import com.sydoruk.util.RandomGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class CarServiceTest {

    private CarService target;
    private CarArrayRepository repository;
    private RandomGenerator rnd;

    @BeforeEach
    void setUp() {
        repository = Mockito.mock(CarArrayRepository.class);
        target = new CarService(repository);
        rnd = Mockito.mock(RandomGenerator.class);
    }

    @Test
    void createZero() {
        Mockito.when(rnd.random()).thenReturn(0);
        Assertions.assertEquals(-1, target.create(rnd));
    }

    @Test
    void createPositive() {
        Mockito.when(rnd.random()).thenReturn(5);
        Assertions.assertEquals(5, target.create(rnd));
    }

    @Test
    void createPassengerCarNotNull() {
        Assertions.assertNotNull(target.createPassengerCar());
    }

    @Test
    void createTruckNotNull() {
        Assertions.assertNotNull(target.createTruck());
    }

    @Test
    void printInputNull() {
        Assertions.assertDoesNotThrow(() -> target.printPass(null));
    }

    @Test
    void printNotThrow() {
        Assertions.assertDoesNotThrow(() -> target.printPass(target.createPassengerCar()));
    }

    @Test
    void printAllNotThrow() {
        Assertions.assertDoesNotThrow(() -> target.printAll());
    }

    @Test
    void checkInputNull() {
        Assertions.assertDoesNotThrow(() -> CarService.check(null));
    }

    @Test
    void checkNotThrow() {
        Assertions.assertDoesNotThrow(() -> CarService.check(target.createPassengerCar()));
    }

    @Test
    void findIdIncorrectNullId() {
        final Car car = target.find(null);
        Assertions.assertNull(car);
    }

    @Test
    void findIdIncorrectEmptyId() {
        final Car car = target.find("");
        Assertions.assertNull(car);
    }

    @Test
    void findNotFound() {
        String id = "123";
        Mockito.when(repository.getById("123")).thenReturn(null);
        final Car car = target.find(id);
        Assertions.assertNull(car);
    }

    @Test
    void findEquals() {
        final PassengerCar expected = new PassengerCar();
        String id = "123";
        Mockito.when(repository.getById("123")).thenReturn(expected);
        final Car actual = target.find(id);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void deleteNull() {
        Assertions.assertDoesNotThrow(() -> target.delete(null));
    }

    @Test
    void deleteEmpty() {
        Assertions.assertDoesNotThrow(() -> target.delete(""));
    }
}