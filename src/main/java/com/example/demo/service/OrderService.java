package com.example.demo.service;

import com.example.demo.model.*;
import com.example.demo.model.dto.OrderDto;
import com.example.demo.model.dto.UpdateOrderItemsDto;
import com.example.demo.model.dto.UpdateOrderStatusDto;
import com.example.demo.repository.ItemRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.OrderStatusRepository;
import com.example.demo.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private OrderStatusRepository orderStatusRepository;

    @Autowired
    private ModelMapper modelMapper;

    public ResponseWrapper<OrderDto> listAll(Pagination pagination, Long userId) {
        try {
            Pageable paging = PageRequest.of(pagination.getPageNo(), pagination.getPageSize(), Sort.by(pagination.getSortBy()));

            if (pagination.getSortType().equalsIgnoreCase("desc"))
                paging = PageRequest.of(pagination.getPageNo(), pagination.getPageSize(), Sort.by(pagination.getSortBy()).descending());

            List<OrderModel> orders = new ArrayList<>();

            if (userId != null)
                orders = orderRepository.findByUserIdAndOrderStatus_StatusName(userId, pagination.getType(), paging).getContent();
            else {
                Optional<UserModel> user = userRepository.findById(userId);
                if (user.isEmpty())
                    return new ResponseWrapper<>("User not found with this id : " + userId, false, new ArrayList<>());
                if (!(user.get().getRole().getRoleName().equalsIgnoreCase("MANAGER")))
                    return new ResponseWrapper<>("User don't have permission to perform this action!", false, new ArrayList<>());
                orders = orderRepository.findAll(paging).getContent();
            }
            List<OrderDto> ordersDto = orders.stream().map(this::convertToDto).collect(Collectors.toList());
            return new ResponseWrapper<OrderDto>("Success", true, new ArrayList<>(ordersDto));
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseWrapper<>("Failed: " + e.getMessage(), false, new ArrayList<>());
        }
    }

    public ResponseWrapper<OrderDto> getById(Long id) {
        try {
            System.err.println("In userService getById with ID:: " + id);
            Optional<OrderModel> order = orderRepository.findById(id);

            if (order.isEmpty())
                return new ResponseWrapper<OrderDto>("Failed", false, "Order not found", new ArrayList<>());

            OrderDto orderDto = convertToDto(order.get());
            return new ResponseWrapper<OrderDto>("Success", true, Arrays.asList(orderDto));
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseWrapper<>("Failed in order service get by id: " + e.getMessage(), false, new ArrayList<>());
        }
    }

    public ResponseWrapper<OrderDto> save(OrderModel order) {
        try {
            Optional<OrderStatusModel> orderStatusModel = orderStatusRepository.findById(order.getOrderStatus().getId());
            if (orderStatusModel.isEmpty())
                return new ResponseWrapper<>("Please provide valid id for order status!!", false, new ArrayList<>());
            order.setOrderStatus(orderStatusModel.get());
            order.setSubmittedDate(new Date());
            checkItemQuantity(order.getItems());
            OrderModel savedOrder = orderRepository.save(order);
            OrderDto orderDto = convertToDto(savedOrder);
            return new ResponseWrapper<>("Success", true, new ArrayList<>(Arrays.asList(orderDto)));
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseWrapper<>("Failed: " + e.getMessage(), false, new ArrayList<>());
        }
    }

    public ResponseWrapper<OrderDto> updateItems(UpdateOrderItemsDto updateOrderItemsDto) {
        try {
            Optional<OrderModel> orderToBeUpdated = orderRepository.findById(updateOrderItemsDto.getId());
            if (orderToBeUpdated.isEmpty())
                return new ResponseWrapper<>("Order not found!", false, new ArrayList<>());
            checkItemQuantity(updateOrderItemsDto.getItems());
            orderToBeUpdated.get().setItems(updateOrderItemsDto.getItems());
            OrderModel savedOrder = orderRepository.save(orderToBeUpdated.get());
            OrderDto orderDto = convertToDto(savedOrder);
            return new ResponseWrapper<>("Success", true, new ArrayList<>(Arrays.asList(orderDto)));
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseWrapper<>("Failed: " + e.getMessage(), false, new ArrayList<>());
        }
    }

    public ResponseWrapper<OrderDto> updateOrderStatus(UpdateOrderStatusDto updateOrderStatusDto) {
        try {
            Optional<OrderModel> orderToBeUpdated = orderRepository.findById(updateOrderStatusDto.getId());
            if (orderToBeUpdated.isEmpty())
                return new ResponseWrapper<>("Order not found!", false, new ArrayList<>());
            Optional<OrderStatusModel> orderStatusModel = orderStatusRepository.findById(updateOrderStatusDto.getOrderStatus().getId());
            if(orderStatusModel.isEmpty())
                return new ResponseWrapper<>("Order Status not found!", false, new ArrayList<>());
            orderToBeUpdated.get().setOrderStatus(orderStatusModel.get());
            OrderModel savedOrder = orderRepository.save(orderToBeUpdated.get());
            OrderDto orderDto = convertToDto(savedOrder);
            return new ResponseWrapper<>("Success", true, new ArrayList<>(Arrays.asList(orderDto)));
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseWrapper<>("Failed: " + e.getMessage(), false, new ArrayList<>());
        }
    }

    public ResponseWrapper<OrderDto> deleteOrder(Long id) {
        try {
            orderRepository.deleteById(id);
            return new ResponseWrapper<>("Success", true, new ArrayList<>());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseWrapper<>("Failed: " + e.getMessage(), false, new ArrayList<>());
        }
    }

    private void checkItemQuantity(List<OrderItemsModel> items) throws Exception {
        for (OrderItemsModel item : items) {
            ItemModel itemInDb = itemRepository.getByItemCode(item.getItemCode());
            if (item.getQuantity().subtract(itemInDb.getQuantity()).compareTo(BigDecimal.ZERO) > 0)
                throw new Exception("Not enough quantity for this item : " + item.getItemName());
        }
    }

    private OrderDto convertToDto(OrderModel order) {
        OrderDto orderDto = modelMapper.map(order, OrderDto.class);
        return orderDto;
    }

    private OrderModel convertToEntity(OrderDto orderDto) {
        OrderModel order = modelMapper.map(orderDto, OrderModel.class);
        return order;
    }
}
