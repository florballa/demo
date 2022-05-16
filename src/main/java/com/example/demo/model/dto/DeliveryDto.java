package com.example.demo.model.dto;

import java.util.Date;
import java.util.List;

public class DeliveryDto {

    private Long id;
    private List<OrderDto> orders;
    private List<TruckDto> trucks;
    private Date date;

    public DeliveryDto(){}

    public Long getId() {
        return id;
    }

    public List<OrderDto> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderDto> orders) {
        this.orders = orders;
    }

    public List<TruckDto> getTrucks() {
        return trucks;
    }

    public void setTrucks(List<TruckDto> trucks) {
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
        return "DeliveryDto{" +
                "id=" + id +
                ", orders=" + orders +
                ", trucks=" + trucks +
                ", date=" + date +
                '}';
    }
}
