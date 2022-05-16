package com.example.demo.model.dto;

import com.example.demo.model.OrderStatusModel;

import java.util.List;

public class OrderDto {

    private Long id;
    private List<ItemDto> items;
    private OrderStatusDto orderStatus;
    private Long userId;

    public OrderDto(){}

    public void setId(Long id) {
        this.id = id;
    }

    public List<ItemDto> getItems() {
        return items;
    }

    public void setItems(List<ItemDto> items) {
        this.items = items;
    }

    public OrderStatusDto getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatusDto orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "OrderDto{" +
                "id=" + id +
                ", items=" + items +
                ", orderStatus=" + orderStatus +
                ", userId=" + userId +
                '}';
    }
}
