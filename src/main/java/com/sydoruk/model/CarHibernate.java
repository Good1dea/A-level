package com.sydoruk.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name = "cars")
@Setter
@Getter

@Inheritance(strategy = InheritanceType.JOINED)
public abstract class CarHibernate {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    protected String id_car;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_order")
    private OrderHibernate order;

    @OneToOne(cascade = CascadeType.PERSIST)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_engine")
    protected EngineHibernate engine;

    protected Color color;

    protected Type type;

    protected Manufacturer manufacturer;

    protected int count;
    protected int price;

    public CarHibernate() {

    }
}