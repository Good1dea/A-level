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
@Table(name = "truck_car")
@PrimaryKeyJoinColumn(name = "id_truck")
@OnDelete(action = OnDeleteAction.CASCADE)
public class TruckHibernate extends CarHibernate{

    @Setter
    @Getter
    private int loadCapacity;

    @Transient
    Random random = new Random();

    public TruckHibernate(){
        super();
        this.type = Type.TRUCK;
        this.loadCapacity = random.nextInt(500, 10001);
    }

    public TruckHibernate(final String id, final int loadCapacity){
        this.id_car = id;
        this.type = Type.TRUCK;
        this.loadCapacity = loadCapacity;
    }
}
