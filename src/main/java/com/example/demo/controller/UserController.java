package com.example.demo.controller;

import com.example.demo.model.ResponseWrapper;
import com.example.demo.model.UserModel;
import com.example.demo.model.dto.UserDto;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<ResponseWrapper<UserDto>> listAll(@RequestParam(defaultValue="1") Integer pageNo,
                                                            @RequestParam(defaultValue="10") Integer pageSize,
                                                            @RequestParam(defaultValue="id") String sortBy,
                                                            @RequestParam(defaultValue="asc") String sortType){
        ResponseWrapper<UserDto> res = userService.findAllUsers(pageNo, pageSize, sortBy, sortType);
        if(res.getStatus())
            return ResponseEntity.ok(res);
        return ResponseEntity.status(460).body(res);
    }

    @PostMapping("/get/{id}")
    public ResponseEntity<ResponseWrapper<UserDto>> getById(@PathVariable("id") Long id){
        ResponseWrapper<UserDto> res = userService.getById(id);
        if(res.getStatus())
            return ResponseEntity.ok(res);
        return ResponseEntity.status(460).body(res);
    }

    @PostMapping("/save")
    public ResponseEntity<ResponseWrapper<UserDto>> save(@RequestBody UserModel user){
        ResponseWrapper<UserDto> res = userService.save(user);
        if(res.getStatus())
            return ResponseEntity.ok(res);
        return ResponseEntity.status(460).body(res);
    }

    @PostMapping("/update")
    public ResponseEntity<ResponseWrapper<UserDto>> update(@RequestBody UserModel user){
        ResponseWrapper<UserDto> res = userService.updateUser(user);
        if(res.getStatus())
            return ResponseEntity.ok(res);
        return ResponseEntity.status(460).body(res);
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<ResponseWrapper<UserDto>> delete(@PathVariable Long id){
        ResponseWrapper<UserDto> res = userService.deleteUser(id);
        if(res.getStatus())
            return ResponseEntity.ok(res);
        return ResponseEntity.status(460).body(res);
    }
}
