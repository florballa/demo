package com.example.demo.model;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table
public class ItemModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "item_seq")
    @SequenceGenerator(name = "item_seq", sequenceName = "item_id_seq", allocationSize = 1)
    private Long id;
    private String itemName;
    private BigDecimal quantity;
    private BigDecimal unitPrice;
    private Long orderId;

    public ItemModel(){}

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    @Override
    public String toString() {
        return "ItemModel{" +
                "id=" + id +
                ", itemName='" + itemName + '\'' +
                ", quantity=" + quantity +
                ", unitPrice=" + unitPrice +
                ", orderModel=" + orderId +
                '}';
    }
}
