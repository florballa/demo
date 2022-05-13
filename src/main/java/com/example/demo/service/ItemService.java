package com.example.demo.service;

import com.example.demo.model.ItemModel;
import com.example.demo.model.Pagination;
import com.example.demo.model.ResponseWrapper;
import com.example.demo.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    public ResponseWrapper<ItemModel> listAll(Pagination pagination) {
        try {
            Pageable paging = PageRequest.of(pagination.getPageNo(), pagination.getPageSize(), Sort.by(pagination.getSortBy()));

            if (pagination.getSortType().equalsIgnoreCase("desc"))
                paging = PageRequest.of(pagination.getPageNo(), pagination.getPageSize(), Sort.by(pagination.getSortBy()).descending());

            return new ResponseWrapper<>("Success", true, new ArrayList<>(itemRepository.findAll(paging).getContent()));
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseWrapper<>("Failed, " + e.getMessage(), false, new ArrayList<>());
        }
    }

    public ResponseWrapper<ItemModel> getById(Long id){
        try{
            Optional<ItemModel> itemModel = itemRepository.findById(id);

            if(itemModel.isEmpty())
                return new ResponseWrapper<>("Can't find item with this ID: " + id, false, new ArrayList<>());
            return new ResponseWrapper<>("Success", true, new ArrayList<>(Arrays.asList(itemModel.get())));
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseWrapper<>("Failed, " + e.getMessage(), false, new ArrayList<>());
        }
    }

    public ResponseWrapper<ItemModel> saveOrUpdate(ItemModel item){
        try{
            return new ResponseWrapper<>("Success", true, new ArrayList<>(Arrays.asList(itemRepository.save(item))));
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseWrapper<>("Failed, " + e.getMessage(), false, new ArrayList<>());
        }
    }

    public ResponseWrapper<ItemModel> deleteItem(ItemModel item){
        try{
            itemRepository.delete(item);
            return new ResponseWrapper<>("Success", true, new ArrayList<>());
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseWrapper<>("Failed, " + e.getMessage(), false, new ArrayList<>());
        }
    }
}
