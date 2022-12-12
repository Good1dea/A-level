package com.sydoruk.model;

public class PassengerCar extends Car {

     private int passengerCount;

     public PassengerCar(){
          super();
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
