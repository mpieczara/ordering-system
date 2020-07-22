package com.system.ordering.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String customer;
    private LocalDate date;

    public Order() {

    }

    public Order(Long id, String name, String customer, LocalDate date) {
        this.id = id;
        this.name = name;
        this.customer = customer;
        this.date = date;
    }
}
