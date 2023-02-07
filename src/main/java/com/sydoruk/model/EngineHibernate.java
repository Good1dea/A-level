package com.sydoruk.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "engines")
@Setter
@Getter
@ToString
public class EngineHibernate {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id_engine;

    private int power;
    private String type;

    public EngineHibernate(){

    }
}