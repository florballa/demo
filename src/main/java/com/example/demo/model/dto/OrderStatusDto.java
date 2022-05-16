package com.example.demo.model.dto;

public class OrderStatusDto {

    private Long id;
    private String statusName;

    public OrderStatusDto(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }
}
