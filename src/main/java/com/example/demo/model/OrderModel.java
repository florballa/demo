package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "order_model")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class OrderModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_seq")
    @SequenceGenerator(name = "order_seq", sequenceName = "order_id_seq", allocationSize = 1)
    private Long id;
    private Date submittedDate;
    private Date deadlineDate;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "order_status_id")
    private OrderStatusModel orderStatus;
    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "orders_item",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id")
    )
    private List<OrderItemsModel> items;
    private Long userId;

    public OrderModel() {
    }

    public Long getId() {
        return id;
    }

    public Date getSubmittedDate() {
        return submittedDate;
    }

    public void setSubmittedDate(Date submittedDate) {
        this.submittedDate = submittedDate;
    }

    public Date getDeadlineDate() {
        return deadlineDate;
    }

    public void setDeadlineDate(Date deadlineDate) {
        this.deadlineDate = deadlineDate;
    }

    public OrderStatusModel getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatusModel orderStatus) {
        this.orderStatus = orderStatus;
    }

    public List<OrderItemsModel> getItems() {
        return items;
    }

    public void setItems(List<OrderItemsModel> items) {
        this.items = items;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "OrderModel{" +
                "id=" + id +
                ", submittedDate=" + submittedDate +
                ", deadlineDate=" + deadlineDate +
                ", orderStatus=" + orderStatus +
                ", items=" + items +
                ", userId=" + userId +
                '}';
    }
}
