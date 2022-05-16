package com.example.demo.service;

import com.example.demo.model.ResponseWrapper;
import com.example.demo.model.UserModel;
import com.example.demo.model.dto.UserDto;
import com.example.demo.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        System.err.println("In loadUserByUsername with username:: " + username);

        Optional<UserModel> user = userRepository.getByUsernameIgnoreCase(username);

        if (user.isEmpty() || !user.get().isEnabled()) {
            System.err.println("User not found with username:: " + username);
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return new org.springframework.security.core.userdetails.User(user.get().getUsername(), user.get().getPassword(), user.get().getAuthorities());
    }

    public Optional<UserModel> findByUsername(String username) {
        return userRepository.getByUsernameIgnoreCase(username);
    }

    public ResponseWrapper<UserDto> findAllUsers(Integer pageNo, Integer pageSize, String sortBy, String sortType) {

        try {
            System.err.println("In findAllUsers!!");
            Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

            if (sortType.equalsIgnoreCase("desc"))
                paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());

            List<UserModel> users = userRepository.findAll(paging).getContent();
            List<UserDto> usersDto = users.stream().map(this::convertToDto).collect(Collectors.toList());

            return new ResponseWrapper<UserDto>("Success", true, userRepository.findAll(paging).getTotalElements(), usersDto);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseWrapper<>("Failed in user service: " + e.getMessage(), false, new ArrayList<>());
        }
    }

    public Optional<UserModel> getByEmail(String email) {
        System.err.println("In userService getByEmail:: " + email);
        return userRepository.getByEmailIgnoreCase(email);
    }

    public ResponseWrapper<UserDto> getById(Long id) {
        try {
            System.err.println("In userService getById with ID:: " + id);
            Optional<UserModel> user = userRepository.findById(id);

            if (user.isEmpty()) {
                return new ResponseWrapper<UserDto>("Failed", false, "User not found", new ArrayList<>());
            }
            UserDto userDto = convertToDto(user.get());
            return new ResponseWrapper<UserDto>("Success", true, Arrays.asList(userDto));
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseWrapper<>("Failed in user service: " + e.getMessage(), false, new ArrayList<>());
        }
    }

    public ResponseWrapper<UserDto> save(UserModel user) {
        try {
            String passwordEncoded = bCryptPasswordEncoder.encode(user.getPassword());
            user.setPassword(passwordEncoded);
            user.setEnabled(true);
            UserModel savedUser = userRepository.save(user);
            UserDto userDto = convertToDto(savedUser);
            return new ResponseWrapper<UserDto>("Success", true, new ArrayList<>(Arrays.asList(userDto)));
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseWrapper<>("Failed: " + e.getMessage(), false, new ArrayList<>());
        }
    }

    @Transactional
    public ResponseWrapper<UserDto> updateUser(UserModel user){
        try{
            UserModel updatedUser = userRepository.save(user);
            UserDto userDto = convertToDto(updatedUser);
            return new ResponseWrapper<UserDto>("Success", true, new ArrayList<>(Arrays.asList(userDto)));
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseWrapper<>("Failed: " + e.getMessage(), false, new ArrayList<>());
        }
    }

    public ResponseWrapper<UserDto> deleteUser(Long id) {
        try {
            System.err.println("In deleteUser:: " + id);
            Optional<UserModel> user = userRepository.findById(id);
            if(user.isEmpty())
                return new ResponseWrapper<>("User not found with id: " + id, false, new ArrayList<>());
            user.get().setEnabled(false);
            userRepository.save(user.get());
            return new ResponseWrapper<UserDto>("Success", true, new ArrayList<>());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseWrapper<>("Failed in user service: " + e.getMessage(), false, new ArrayList<>());
        }
    }

    private UserDto convertToDto(UserModel user) {
        UserDto userDto = modelMapper.map(user, UserDto.class);
        return userDto;
    }

    private UserModel convertToEntity(UserDto userDto) {
        UserModel user = modelMapper.map(userDto, UserModel.class);
        return user;
    }
}
