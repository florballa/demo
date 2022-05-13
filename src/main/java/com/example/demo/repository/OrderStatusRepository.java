package com.example.demo.repository;

import com.example.demo.model.OrderStatusModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderStatusRepository extends JpaRepository<OrderStatusModel, Long> {
}
