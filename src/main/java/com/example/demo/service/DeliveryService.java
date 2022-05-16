package com.example.demo.service;

import com.example.demo.model.*;
import com.example.demo.repository.DeliveryRepository;
import com.example.demo.repository.ItemRepository;
import com.example.demo.repository.OrderStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@Service
public class DeliveryService {

    @Autowired
    private DeliveryRepository deliveryRepository;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderStatusRepository orderStatusRepository;

    @Autowired
    private ItemRepository itemRepository;

    public ResponseWrapper<DeliveryModel> listAll(Pagination pagination){
        try{
            Pageable paging = PageRequest.of(pagination.getPageNo(), pagination.getPageSize(), Sort.by(pagination.getSortBy()));

            if (pagination.getSortType().equalsIgnoreCase("desc"))
                paging = PageRequest.of(pagination.getPageNo(), pagination.getPageSize(), Sort.by(pagination.getSortBy()).descending());
            return new ResponseWrapper<>("Success", true, new ArrayList<>(deliveryRepository.findAll(paging).getContent()));
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseWrapper<>("Failed, " + e.getMessage(), false, new ArrayList<>());
        }
    }

    public ResponseWrapper<DeliveryModel> getById(Long id){
        try{
            Optional<DeliveryModel> deliveryModel = deliveryRepository.findById(id);

            if(deliveryModel.isEmpty())
                return new ResponseWrapper<>("Can't find delivery with this ID: " + id, false, new ArrayList<>());
            return new ResponseWrapper<>("Success", true, new ArrayList<>(Arrays.asList(deliveryModel.get())));
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseWrapper<>("Failed, " + e.getMessage(), false, new ArrayList<>());
        }
    }

    public ResponseWrapper<DeliveryModel> save(DeliveryModel delivery){
        try{
            TruckModel tr = checkTrucksByDay(delivery.getTrucks());
            if(tr != null)
                return new ResponseWrapper<>("Sorry this truck is on another order: " + tr.getLicensePlate(), false, new ArrayList<>());
            String dateName = LocalDate.now().getDayOfWeek().name();
            if (dateName.equalsIgnoreCase("SUNDAY"))
                return new ResponseWrapper<>("Sorry, drivers are off today!", false, new ArrayList<>());


            BigDecimal itemQuantity = getItemQuantity(delivery.getOrders());
            Double howManyTrucks = checkHowManyTruckAreNeeded(itemQuantity);
            if(itemQuantity.compareTo(BigDecimal.TEN) >= 0)
                return new ResponseWrapper<>("Sorry, you need " + howManyTrucks + "more truck for this orders!", false, new ArrayList<>());

            saveOrdersStatusAndItemQuantity(delivery.getOrders());

            return new ResponseWrapper<>("Success", true, new ArrayList<>(Arrays.asList(deliveryRepository.save(delivery))));
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseWrapper<>("Failed, " + e.getMessage(), false, new ArrayList<>());
        }
    }

    public ResponseWrapper<DeliveryModel> delete(Long id){
        try{
            deliveryRepository.deleteById(id);
            return new ResponseWrapper<>("Success", true, new ArrayList<>());
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseWrapper<>("Failed, " + e.getMessage(), false, new ArrayList<>());
        }
    }

    private BigDecimal getItemQuantity(List<OrderModel> orders){
        BigDecimal itemsQuantity = BigDecimal.ZERO;
        for(OrderModel order : orders){
            for(OrderItemsModel item : order.getItems()){
                itemsQuantity = itemsQuantity.add(item.getQuantity());
            }
        }
        return itemsQuantity;
    }

    private double checkHowManyTruckAreNeeded(BigDecimal itemQuantity){
        BigDecimal diff = itemQuantity.divide(BigDecimal.TEN);
        return Math.ceil(diff.doubleValue());
    }

    private void saveOrdersStatusAndItemQuantity(List<OrderModel> orders){
        for(OrderModel order : orders){
            OrderStatusModel orderStatusModel = orderStatusRepository.getById(5L);
            order.setOrderStatus(orderStatusModel);
            orderService.save(order);

            for(OrderItemsModel item : order.getItems()){
                ItemModel itemInDb = itemRepository.getByItemCode(item.getItemCode());
                item.setQuantity(itemInDb.getQuantity().subtract(item.getQuantity()));
            }
        }
    }

    private TruckModel checkTrucksByDay(List<TruckModel> trucks){

        List<DeliveryModel> deliveries = deliveryRepository.findByDate(new Date());

        for(DeliveryModel delivery : deliveries){
            for(TruckModel truckDelivery : delivery.getTrucks()){
                for(TruckModel truck : trucks){
                    if(truck == truckDelivery)
                        return truck;
                }
            }
        }
        return null;
    }
}
