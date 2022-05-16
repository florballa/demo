package com.example.demo.model;

import javax.persistence.*;

@Entity
@Table
public class OrderStatusModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_seq")
    @SequenceGenerator(name = "order_seq", sequenceName = "order_id_seq", allocationSize = 1)
    private Long id;

    private String statusName;

    public OrderStatusModel(){}

    public Long getId() {
        return id;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    @Override
    public String toString() {
        return "OrderStatusModel{" +
                "id=" + id +
                ", statusName='" + statusName + '\'' +
                '}';
    }
}
