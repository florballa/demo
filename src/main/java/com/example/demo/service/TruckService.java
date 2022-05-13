package com.example.demo.service;

import com.example.demo.model.Pagination;
import com.example.demo.model.ResponseWrapper;
import com.example.demo.model.TruckModel;
import com.example.demo.repository.TruckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

@Service
public class TruckService {

    @Autowired
    private TruckRepository truckRepository;

    public ResponseWrapper<TruckModel> listAll(Pagination pagination) {
        try {
            Pageable paging = PageRequest.of(pagination.getPageNo(), pagination.getPageSize(), Sort.by(pagination.getSortBy()));

            if (pagination.getSortType().equalsIgnoreCase("desc"))
                paging = PageRequest.of(pagination.getPageNo(), pagination.getPageSize(), Sort.by(pagination.getSortBy()).descending());

            return new ResponseWrapper<>("Success", true, new ArrayList<>(truckRepository.findAll(paging).getContent()));
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseWrapper<>("Failed, " + e.getMessage(), false, new ArrayList<>());
        }
    }

    public ResponseWrapper<TruckModel> getById(Long id){
        try{
            Optional<TruckModel> truckModel = truckRepository.findById(id);

            if(truckModel.isEmpty())
                return new ResponseWrapper<>("Can't find item with this ID: " + id, false, new ArrayList<>());
            return new ResponseWrapper<>("Success", true, new ArrayList<>(Arrays.asList(truckModel.get())));
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseWrapper<>("Failed, " + e.getMessage(), false, new ArrayList<>());
        }
    }

    public ResponseWrapper<TruckModel> saveOrUpdate(TruckModel truckModel){
        try {
            return new ResponseWrapper<TruckModel>("Success", true, new ArrayList<>(Arrays.asList(truckRepository.save(truckModel))));
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseWrapper<>("Failed: " + e.getMessage(), false, new ArrayList<>());
        }
    }

    public ResponseWrapper<TruckModel> delete(Long id){
        try{
            truckRepository.deleteById(id);
            return new ResponseWrapper<>("Success", true, new ArrayList<>());
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseWrapper<>("Failed: " + e.getMessage(), false, new ArrayList<>());
        }
    }
}
