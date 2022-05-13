package com.example.demo.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table
public class DeliveryModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "delivery_seq")
    @SequenceGenerator(name = "delivery_seq", sequenceName = "delivery_id_seq", allocationSize = 1)
    private Long id;

    @OneToMany
    private List<OrderModel> orders;

    @OneToMany
    private List<TruckModel> trucks;

    private Date date;

    public DeliveryModel(){}

    public Long getId() {
        return id;
    }
    public List<OrderModel> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderModel> orders) {
        this.orders = orders;
    }

    public List<TruckModel> getTrucks() {
        return trucks;
    }

    public void setTrucks(List<TruckModel> trucks) {
        this.trucks = trucks;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "DeliveryModel{" +
                "id=" + id +
                ", orders=" + orders +
                ", trucks=" + trucks +
                ", date=" + date +
                '}';
    }
}
