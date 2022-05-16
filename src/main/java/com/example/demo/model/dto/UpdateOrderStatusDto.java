package com.example.demo.model.dto;

import com.example.demo.model.OrderStatusModel;

public class UpdateOrderStatusDto {
    private Long id;
    private OrderStatusModel orderStatus;

    public UpdateOrderStatusDto(){}

    public Long getId() {
        return id;
    }

    public OrderStatusModel getOrderStatus() {
        return orderStatus;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setOrderStatus(OrderStatusModel orderStatus) {
        this.orderStatus = orderStatus;
    }

    @Override
    public String toString() {
        return "UpdateOrderStatusDto{" +
                "id=" + id +
                ", orderStatus=" + orderStatus +
                '}';
    }
}
