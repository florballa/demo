package com.example.demo.utils;

import com.example.demo.model.OrderModel;
import com.example.demo.model.Pagination;
import com.example.demo.model.ResponseWrapper;
import com.example.demo.model.UserModel;
import com.example.demo.model.dto.OrderDto;
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

    public ResponseWrapper<OrderDto> checkPermissionToGetOrderById(String username, Long orderId){

        UserModel user = userService.findByUsername(username).get();
        ResponseWrapper<OrderDto> res = orderService.getById(orderId);

        if(user.getRole().getRoleName().equalsIgnoreCase("CLIENT")) {
            Long userId = res.getContent().get(0).getUserId();
            if (userId != null && userId != user.getId())
                throw new AccessDeniedException("You don't have rights to perform this action!");
        }
        return res;
    }
}
