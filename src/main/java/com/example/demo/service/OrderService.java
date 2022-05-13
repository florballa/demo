package com.example.demo.service;

import com.example.demo.model.*;
import com.example.demo.repository.BasicOrderRepository;
import com.example.demo.repository.ItemRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.OrderStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

@Service
@Transactional
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private BasicOrderRepository basicOrderRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private OrderStatusRepository orderStatusRepository;

    public ResponseWrapper<OrderModel> listAll(Pagination pagination, Long userId){
        try {
            Pageable paging = PageRequest.of(pagination.getPageNo(), pagination.getPageSize(), Sort.by(pagination.getSortBy()));

            if (pagination.getSortType().equalsIgnoreCase("desc"))
                paging = PageRequest.of(pagination.getPageNo(), pagination.getPageSize(), Sort.by(pagination.getSortBy()).descending());

            if(userId != null)
                return new ResponseWrapper<>("Success", true, new ArrayList<>(orderRepository.findByUserIdAndOrderStatus_StatusName(userId, pagination.getType(), paging).getContent()));

            return new ResponseWrapper<OrderModel>("Success", true, new ArrayList<>(orderRepository.findAll(paging).getContent()));
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseWrapper<>("Failed: " + e.getMessage(), false, new ArrayList<>());
        }
    }

    public ResponseWrapper<BasicOrder> findAllBasicOrders(Pagination pagination){
        try {
            Pageable paging = PageRequest.of(pagination.getPageNo(), pagination.getPageSize(), Sort.by(pagination.getSortBy()));

            if (pagination.getSortType().equalsIgnoreCase("desc"))
                paging = PageRequest.of(pagination.getPageNo(), pagination.getPageSize(), Sort.by(pagination.getSortBy()).descending());

            if(pagination.getType().equalsIgnoreCase("ALL"))
                return new ResponseWrapper<BasicOrder>("Success", true, new ArrayList<>(basicOrderRepository.findAll(paging).getContent()));
            return new ResponseWrapper<BasicOrder>("Success", true, new ArrayList<>(basicOrderRepository.findByOrderStatus_StatusName(pagination.getType(), paging).getContent()));
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseWrapper<>("Failed: " + e.getMessage(), false, new ArrayList<>());
        }
    }

    public ResponseWrapper<OrderModel> getById(Long id){
        try {
            System.err.println("In userService getById with ID:: " + id);
            Optional<OrderModel> order = orderRepository.findById(id);

            if (order.isEmpty()) {
                return new ResponseWrapper<OrderModel>("Failed", false, "Order not found", new ArrayList<>());
            }
            return new ResponseWrapper<OrderModel>("Success", true, Arrays.asList(order.get()));
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseWrapper<>("Failed in order service get by id: " + e.getMessage(), false, new ArrayList<>());
        }
    }

    public ResponseWrapper<OrderModel> saveOrUpdate(OrderModel order){
        try{

            if(order.getId() != null){
                ResponseWrapper<UserModel> userModel = userService.getById(order.getUserId());
                UserModel user = userModel.getContent().get(0);

                if(user.getRole().getRoleName().equalsIgnoreCase("CLIENT")){
                    if(order.getOrderStatus().getStatusName().equalsIgnoreCase("CREATED") || order.getOrderStatus().getStatusName().equalsIgnoreCase("DECLINED"))
                        return new ResponseWrapper<>("Success", true, new ArrayList<>(Arrays.asList(orderRepository.save(order))));
                    return new ResponseWrapper<>("Failed, don't have permission to perform this action", false, new ArrayList<>());
                }
            }
            else{
                order.setOrderStatus(orderStatusRepository.findById(1L).get());
            }
            order.setSubmittedDate(new Date());

            for(ItemModel item : order.getItems()){
                itemRepository.save(item);
            }

            return new ResponseWrapper<>("Success", true, new ArrayList<>(Arrays.asList(orderRepository.save(order))));
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseWrapper<>("Failed: " + e.getMessage(), false, new ArrayList<>());
        }
    }

    public ResponseWrapper<OrderModel> deleteOrder(OrderModel order) {
        return null;
    }
}
