package com.example.demo.repository;

import com.example.demo.model.OrderModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<OrderModel, Long> {

    public Page<OrderModel> findByUserIdAndOrderStatus_StatusName(Long userId, String statusName, Pageable pageable);
}
