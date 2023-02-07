package com.sydoruk.model;

import java.util.Random;

public class PassengerCar extends Car {

     private int passengerCount;
     final Random random = new Random();
     public PassengerCar(){
          super();
          this.type = Type.CAR;
          passengerCount = random.nextInt(1,9);
     }

     public PassengerCar(final String id, final int count){
          this.id = id;
          this.type = Type.CAR;
          this.passengerCount = count;
     }

     public void setPassengerCount(int passengerCount){
          this.passengerCount = passengerCount;
     }

     public int getPassengerCount() {
          return passengerCount;
     }

     @Override
     public void restore() {
          super.setCount(100);
     }
}
