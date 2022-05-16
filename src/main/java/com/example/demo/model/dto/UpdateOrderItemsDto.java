package com.example.demo.model.dto;

import com.example.demo.model.OrderItemsModel;

import java.util.List;

public class UpdateOrderItemsDto {
    private Long id;
    private List<OrderItemsModel> items;

    public UpdateOrderItemsDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<OrderItemsModel> getItems() {
        return items;
    }

    public void setItems(List<OrderItemsModel> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "UpdateOrderItemsDto{" +
                "id=" + id +
                ", items=" + items +
                '}';
    }
}
