package com.example.demo.controller;

import com.example.demo.model.DeliveryModel;
import com.example.demo.model.Pagination;
import com.example.demo.model.ResponseWrapper;
import com.example.demo.service.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jms.JmsProperties;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/deliveries")
public class DeliveryController {

    @Autowired
    private DeliveryService deliveryService;

    @PostMapping
    public ResponseWrapper<DeliveryModel> listAll(@RequestBody Pagination pagination){
        return deliveryService.listAll(pagination);
    }

    @PostMapping("/get/{id}")
    public ResponseWrapper<DeliveryModel> getById(@PathVariable Long id){
        return deliveryService.getById(id);
    }

    @PostMapping("/save")
    public ResponseWrapper<DeliveryModel> save(@RequestBody DeliveryModel deliveryModel){
        return deliveryService.save(deliveryModel);
    }

    @PostMapping("/delete/{id}")
    public ResponseWrapper<DeliveryModel> delete(@PathVariable Long id){
        return deliveryService.delete(id);
    }
}
