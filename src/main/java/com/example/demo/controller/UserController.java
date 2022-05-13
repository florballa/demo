package com.example.demo.controller;

import com.example.demo.model.ResponseWrapper;
import com.example.demo.model.UserModel;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseWrapper<UserModel> listAll(@RequestParam(defaultValue="1") Integer pageNo,
                                              @RequestParam(defaultValue="10") Integer pageSize,
                                              @RequestParam(defaultValue="id") String sortBy,
                                              @RequestParam(defaultValue="asc") String sortType){
        return userService.findAllUsers(pageNo, pageSize, sortBy, sortType);
    }

    @PostMapping("/get/{id}")
    public ResponseWrapper<UserModel> getById(@PathVariable("id") Long id){
        return userService.getById(id);
    }

    @PostMapping("/save")
    public ResponseWrapper<UserModel> saveOrUpdate(@RequestBody UserModel user){
        return userService.saveOrUpdateUser(user);
    }

    @PostMapping("/delete")
    public ResponseWrapper<UserModel> delete(@RequestBody UserModel user){
        return userService.deleteUser(user);
    }
}
