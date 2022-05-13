package com.example.demo.controller;

import com.example.demo.model.ItemModel;
import com.example.demo.model.Pagination;
import com.example.demo.model.ResponseWrapper;
import com.example.demo.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/items")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @PostMapping
    public ResponseWrapper<ItemModel> findAll(@RequestBody Pagination pagination){
        return itemService.listAll(pagination);
    }

    @PostMapping("/get/{id}")
    public ResponseWrapper<ItemModel> getById(@PathVariable Long id){
        return itemService.getById(id);
    }

    @PostMapping("/save")
    public ResponseWrapper<ItemModel> saveOrUpdate(@RequestBody ItemModel item){
        return itemService.saveOrUpdate(item);
    }

    @PostMapping("/delete")
    public ResponseWrapper<ItemModel> delete(@RequestBody ItemModel item){
        return itemService.deleteItem(item);
    }
}
