package com.example.demo.controller;

import com.example.demo.model.BasicOrder;
import com.example.demo.model.OrderModel;
import com.example.demo.model.Pagination;
import com.example.demo.model.ResponseWrapper;
import com.example.demo.service.OrderService;
import com.example.demo.utils.PermissionUtil;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseWrapper<OrderModel> findAll(@RequestBody Pagination pagination,
                                               @PathVariable(required = false) Long userId,
                                               Principal principal){

        return permissionUtil.checkPermissionToListOrders(principal.getName(), userId, pagination);
    }

    @PostMapping("/basicOrders")
    public ResponseWrapper<BasicOrder> findAllBasicOrders(@RequestBody Pagination pagination){
        return orderService.findAllBasicOrders(pagination);
    }

    @PostMapping("/get/{id}")
    public ResponseWrapper<OrderModel> getById(@PathVariable Long id, Principal principal){
        return permissionUtil.checkPermissionToGetOrderById(principal.getName(), id);
    }

    @PostMapping("/save")
    public ResponseWrapper<OrderModel> saveOrUpdate(@RequestBody OrderModel order, Principal principal){
        return permissionUtil.checkPermissionToSaveOrder(principal.getName(), order);
    }

    @PostMapping("delete")
    public ResponseWrapper<OrderModel> deleteOrder(@RequestBody OrderModel order){
        return orderService.deleteOrder(order);
    }
}
