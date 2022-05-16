package com.example.demo.controller;

import com.example.demo.model.BasicOrder;
import com.example.demo.model.OrderModel;
import com.example.demo.model.Pagination;
import com.example.demo.model.ResponseWrapper;
import com.example.demo.model.dto.OrderDto;
import com.example.demo.model.dto.UpdateOrderItemsDto;
import com.example.demo.model.dto.UpdateOrderStatusDto;
import com.example.demo.service.OrderService;
import com.example.demo.utils.PermissionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private PermissionUtil permissionUtil;

    @PostMapping(value = {"", "/{userId}"})
    public ResponseEntity<ResponseWrapper<OrderDto>> findAll(@RequestBody Pagination pagination,
                                                             @PathVariable(required = false) Long userId){

        ResponseWrapper<OrderDto> res = orderService.listAll(pagination, userId);
        if(res.getStatus())
            return ResponseEntity.ok(res);
        return ResponseEntity.status(460).body(res);
    }

    @PostMapping("/get/{id}")
    public ResponseEntity<ResponseWrapper<OrderDto>> getById(@PathVariable Long id, Principal principal){
        ResponseWrapper<OrderDto> res = permissionUtil.checkPermissionToGetOrderById(principal.getName(), id);
        if(res.getStatus())
            return ResponseEntity.ok(res);
        return ResponseEntity.status(460).body(res);
    }

    @PostMapping("/save")
    public ResponseEntity<ResponseWrapper<OrderDto>> save(@RequestBody OrderModel order, Principal principal){
        ResponseWrapper<OrderDto> res = orderService.save(order);
        if(res.getStatus())
            return ResponseEntity.ok(res);
        return ResponseEntity.status(460).body(res);
    }

    @PostMapping("/update")
    public ResponseEntity<ResponseWrapper<OrderDto>> updateOrder(@RequestBody UpdateOrderItemsDto order){
        ResponseWrapper<OrderDto> res = orderService.updateItems(order);
        if(res.getStatus())
            return ResponseEntity.ok(res);
        return ResponseEntity.status(460).body(res);
    }

    @PostMapping("/updateItems")
    public ResponseEntity<ResponseWrapper<OrderDto>> updateItems(@RequestBody UpdateOrderItemsDto updateOrderItemsDto){
        ResponseWrapper<OrderDto> res = orderService.updateItems(updateOrderItemsDto);
        if(res.getStatus())
            return ResponseEntity.ok(res);
        return ResponseEntity.status(460).body(res);
    }

    @PostMapping("/updateStatus")
    public ResponseEntity<ResponseWrapper<OrderDto>> updateOrderStatus(@RequestBody UpdateOrderStatusDto updateOrderStatusDto){
        ResponseWrapper<OrderDto> res = orderService.updateOrderStatus(updateOrderStatusDto);
        if(res.getStatus())
            return ResponseEntity.ok(res);
        return ResponseEntity.status(460).body(res);
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<ResponseWrapper<OrderDto>> deleteOrder(@PathVariable Long id){
        ResponseWrapper<OrderDto> res = orderService.deleteOrder(id);
        if(res.getStatus())
            return ResponseEntity.ok(res);
        return ResponseEntity.status(460).body(res);
    }
}
