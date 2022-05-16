package com.example.demo.repository;

import com.example.demo.model.ItemModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<ItemModel, Long> {

    public ItemModel getByItemCode(String itemCode);
}
