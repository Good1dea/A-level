package com.sydoruk.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "orders")
@Setter
@Getter
@ToString
public class OrderHibernate {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id_order;

    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL)
    private List<CarHibernate> cars;
    private LocalDate date;

    public OrderHibernate () {
    }

    @PrePersist
    public void prePersist() {
        if (date == null) {
            date = LocalDate.now();
        }
    }
}