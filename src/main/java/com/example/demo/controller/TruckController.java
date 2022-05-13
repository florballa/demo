package com.example.demo.controller;

import com.example.demo.model.Pagination;
import com.example.demo.model.ResponseWrapper;
import com.example.demo.model.TruckModel;
import com.example.demo.service.TruckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/trucks")
public class TruckController {

    @Autowired
    private TruckService truckService;

    @PostMapping
    public ResponseWrapper<TruckModel> listAll(@RequestBody Pagination pagination){
        return truckService.listAll(pagination);
    }

    @PostMapping("/get/{id}")
    public ResponseWrapper<TruckModel> getById(@PathVariable Long id){
        return truckService.getById(id);
    }

    @PostMapping("/save")
    public ResponseWrapper<TruckModel> saveOrUpdate(@RequestBody TruckModel truckModel){
        return truckService.saveOrUpdate(truckModel);
    }

    @PostMapping("/delete/{id}")
    public ResponseWrapper delete(@PathVariable Long id){
        return truckService.delete(id);
    }
}
