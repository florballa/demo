package com.example.demo.controller;

import com.example.demo.model.ItemModel;
import com.example.demo.model.Pagination;
import com.example.demo.model.ResponseWrapper;
import com.example.demo.model.dto.ItemDto;
import com.example.demo.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/items")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @PostMapping
    public ResponseEntity<ResponseWrapper<ItemDto>> findAll(@RequestBody Pagination pagination){
        ResponseWrapper<ItemDto> res = itemService.listAll(pagination);
        if(res.getStatus())
            return ResponseEntity.ok(res);
        return ResponseEntity.status(460).body(res);
    }

    @PostMapping("/get/{id}")
    public ResponseEntity<ResponseWrapper<ItemDto>> getById(@PathVariable Long id){
        ResponseWrapper<ItemDto> res = itemService.getById(id);
        if(res.getStatus())
            return ResponseEntity.ok(res);
        return ResponseEntity.status(460).body(res);
    }

    @PostMapping("/save")
    public ResponseEntity<ResponseWrapper<ItemDto>> save(@RequestBody ItemModel item){
        ResponseWrapper<ItemDto> res = itemService.saveOrUpdate(item);
        if(res.getStatus())
            return ResponseEntity.ok(res);
        return ResponseEntity.status(460).body(res);
    }

    @PostMapping("/update")
    public ResponseEntity<ResponseWrapper<ItemDto>> update(@RequestBody ItemModel item){
        ResponseWrapper<ItemDto> res = itemService.update(item);
        if(res.getStatus())
            return ResponseEntity.ok(res);
        return ResponseEntity.status(460).body(res);
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<ResponseWrapper<ItemDto>> delete(@PathVariable Long id){
        ResponseWrapper<ItemDto> res = itemService.deleteItem(id);
        if(res.getStatus())
            return ResponseEntity.ok(res);
        return ResponseEntity.status(460).body(res);
    }
}
