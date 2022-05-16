package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class DeliveryModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "delivery_seq")
    @SequenceGenerator(name = "delivery_seq", sequenceName = "delivery_id_seq", allocationSize = 1)
    private Long id;

    @OneToMany
    @JsonBackReference
    private List<OrderModel> orders;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "delivery_trucks",
            joinColumns = @JoinColumn(name = "delivery_id"),
            inverseJoinColumns = @JoinColumn(name = "truck_id")
    )
    private List<TruckModel> trucks;

    private Date date;

    public DeliveryModel(){}

    public Long getId() {
        return id;
    }
    public List<OrderModel> getOrders() {
        return orders;
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
