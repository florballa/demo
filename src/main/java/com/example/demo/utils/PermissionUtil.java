package com.example.demo.utils;

import com.example.demo.model.OrderModel;
import com.example.demo.model.Pagination;
import com.example.demo.model.ResponseWrapper;
import com.example.demo.model.UserModel;
import com.example.demo.service.OrderService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Service
public class PermissionUtil {

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    public ResponseWrapper<OrderModel> checkPermissionToListOrders(String username, Long userId, Pagination pagination){

        UserModel user = userService.findByUsername(username).get();

        if(user.getRole().getRoleName().equalsIgnoreCase("CLIENT")) {
            if (userId != null && userId != user.getId())
                throw new AccessDeniedException("You don't have rights to perform this action!");
        }
        return orderService.listAll(pagination, userId);
    }

    public ResponseWrapper<OrderModel> checkPermissionToGetOrderById(String username, Long orderId){

        UserModel user = userService.findByUsername(username).get();
        ResponseWrapper<OrderModel> res = orderService.getById(orderId);

        if(user.getRole().getRoleName().equalsIgnoreCase("CLIENT")) {

            Long userId = res.getContent().get(0).getUserId();

            if (userId != null && userId != user.getId())
                throw new AccessDeniedException("You don't have rights to perform this action!");
        }
        return res;
    }

    public ResponseWrapper<OrderModel> checkPermissionToSaveOrder(String username, OrderModel order){

        UserModel user = userService.findByUsername(username).get();

        if(user.getRole().getRoleName().equalsIgnoreCase("CLIENT")) {
            if (order.getUserId() != null && order.getUserId() != user.getId())
                throw new AccessDeniedException("You don't have rights to perform this action!");
        }
        return orderService.saveOrUpdate(order);
    }
}
