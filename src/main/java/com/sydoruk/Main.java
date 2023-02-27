package com.sydoruk;

import com.sydoruk.model.Type;
import com.sydoruk.repository.CarMap;
import com.sydoruk.service.CarService;
import com.sydoruk.util.ReadFromJsonXml;

import java.io.IOException;

public class Main {

    public static void main(String[] arg) throws IOException {
        final CarMap repository = CarMap.getInstance();
        final CarService service = CarService.getInstance();
        repository.save(service.createCar(Type.randomType()));
        repository.save(service.createCarFromFile(ReadFromJsonXml.getInstance().readFromXmlFile()));
        repository.getAll().forEach(CarService::printCar);
        repository.getById("32k4-5698-5yt9-2288").ifPresent(CarService::printCar);
        repository.delete("32k4-5698-5yt9-2288");
    }
}