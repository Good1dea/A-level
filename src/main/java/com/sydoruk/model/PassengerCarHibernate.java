package com.sydoruk.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Random;

@Entity
@Table(name = "passenger_car")
@PrimaryKeyJoinColumn(name = "id_pass")
@OnDelete(action = OnDeleteAction.CASCADE)
public class PassengerCarHibernate extends CarHibernate {

    @Setter
    @Getter
    private int passengerCount;

    @Transient
    final Random random = new Random();
    public PassengerCarHibernate(){
        super();
        this.type = Type.CAR;
        passengerCount = random.nextInt(1,9);
    }

    public PassengerCarHibernate(final String id, final int count){
        this.id_car = id;
        this.type = Type.CAR;
        this.passengerCount = count;
    }
}