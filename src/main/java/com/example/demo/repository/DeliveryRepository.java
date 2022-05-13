package com.example.demo.repository;

import com.example.demo.model.DeliveryModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface DeliveryRepository extends JpaRepository<DeliveryModel, Long> {
    public List<DeliveryModel> findByDate(Date date);
}
