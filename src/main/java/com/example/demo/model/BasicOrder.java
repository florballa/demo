package com.example.demo.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "order_model")
public class BasicOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_seq")
    @SequenceGenerator(name = "order_seq", sequenceName = "order_id_seq", allocationSize = 1)
    private Long id;
    private Date submittedDate;
    private Date deadlineDate;
    @OneToOne
    private OrderStatusModel orderStatus;
    private Long userId;

    public BasicOrder(){}

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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
