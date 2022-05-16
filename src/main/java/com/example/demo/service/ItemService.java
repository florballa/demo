package com.example.demo.service;

import com.example.demo.model.ItemModel;
import com.example.demo.model.Pagination;
import com.example.demo.model.ResponseWrapper;
import com.example.demo.model.dto.ItemDto;
import com.example.demo.model.dto.UserDto;
import com.example.demo.repository.ItemRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ModelMapper modelMapper;

    public ResponseWrapper<ItemDto> listAll(Pagination pagination) {
        try {
            Pageable paging = PageRequest.of(pagination.getPageNo(), pagination.getPageSize(), Sort.by(pagination.getSortBy()));

            if (pagination.getSortType().equalsIgnoreCase("desc"))
                paging = PageRequest.of(pagination.getPageNo(), pagination.getPageSize(), Sort.by(pagination.getSortBy()).descending());

            List<ItemModel> items = itemRepository.findAll(paging).getContent();
            List<ItemDto> itemsDto = items.stream().map(this::convertToDto).collect(Collectors.toList());

            return new ResponseWrapper<>("Success", true, new ArrayList<>(itemsDto));
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseWrapper<>("Failed, " + e.getMessage(), false, new ArrayList<>());
        }
    }

    public ResponseWrapper<ItemDto> getById(Long id) {
        try {
            Optional<ItemModel> itemModel = itemRepository.findById(id);
            if (itemModel.isEmpty())
                return new ResponseWrapper<>("Can't find item with this ID: " + id, false, new ArrayList<>());
            ItemDto itemDto = convertToDto(itemModel.get());
            return new ResponseWrapper<>("Success", true, new ArrayList<>(Arrays.asList(itemDto)));
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseWrapper<>("Failed, " + e.getMessage(), false, new ArrayList<>());
        }
    }

    public ResponseWrapper<ItemDto> saveOrUpdate(ItemModel item) {
        try {
            ItemModel savedItem = itemRepository.save(item);
            ItemDto itemDto = convertToDto(savedItem);
            return new ResponseWrapper<>("Success", true, new ArrayList<>(Arrays.asList(itemDto)));
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseWrapper<>("Failed, " + e.getMessage(), false, new ArrayList<>());
        }
    }

    @Transactional
    public ResponseWrapper<ItemDto> update(ItemModel item){
        try{
            ItemModel savedItem = itemRepository.save(item);
            ItemDto itemDto = convertToDto(savedItem);
            return new ResponseWrapper<>("Success", true, new ArrayList<>(Arrays.asList(itemDto)));
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseWrapper<>("Failed, " + e.getMessage(), false, new ArrayList<>());
        }
    }

    public ResponseWrapper<ItemDto> deleteItem(Long id) {
        try {
            itemRepository.deleteById(id);
            return new ResponseWrapper<>("Success", true, new ArrayList<>());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseWrapper<>("Failed, " + e.getMessage(), false, new ArrayList<>());
        }
    }

    private ItemDto convertToDto(ItemModel item) {
        ItemDto itemDto = modelMapper.map(item, ItemDto.class);
        return itemDto;
    }

    private ItemModel convertToEntity(ItemDto itemDto) {
        ItemModel item = modelMapper.map(itemDto, ItemModel.class);
        return item;
    }
}
