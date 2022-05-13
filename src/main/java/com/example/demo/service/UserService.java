package com.example.demo.service;

import com.example.demo.model.ResponseWrapper;
import com.example.demo.model.UserModel;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

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

    public Optional<UserModel> findByUsername(String username){
        return userRepository.getByUsernameIgnoreCase(username);
    }

    public ResponseWrapper<UserModel> findAllUsers(Integer pageNo, Integer pageSize, String sortBy, String sortType) {

        try {
            System.err.println("In findAllUsers!!");
            Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

            if (sortType.equalsIgnoreCase("desc"))
                paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());

            return new ResponseWrapper<UserModel>("Success", true, userRepository.findAll(paging).getTotalElements(), userRepository.findAll(paging).getContent());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseWrapper<>("Failed in user service: " + e.getMessage(), false, new ArrayList<>());
        }
    }

    public Optional<UserModel> getByEmail(String email) {
        System.err.println("In userService getByEmail:: " + email);
        return userRepository.getByEmailIgnoreCase(email);
    }

    public ResponseWrapper<UserModel> getById(Long id) {
        try {
            System.err.println("In userService getById with ID:: " + id);
            Optional<UserModel> user = userRepository.findById(id);

            if (user.isEmpty()) {
                return new ResponseWrapper<UserModel>("Failed", false, "User not found", new ArrayList<>());
            }
            return new ResponseWrapper<UserModel>("Success", true, Arrays.asList(user.get()));
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseWrapper<>("Failed in user service: " + e.getMessage(), false, new ArrayList<>());
        }
    }

    public ResponseWrapper<UserModel> saveOrUpdateUser(UserModel user){
        try {
            if(user.getId() == null) {
                String passwordEncoded = bCryptPasswordEncoder.encode(user.getPassword());
                user.setPassword(passwordEncoded);
            }
            user.setEnabled(true);
            return new ResponseWrapper<UserModel>("Success", true, new ArrayList<>(Arrays.asList(userRepository.save(user))));
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseWrapper<>("Failed: " + e.getMessage(), false, new ArrayList<>());
        }
    }

    public ResponseWrapper<UserModel> deleteUser(UserModel user) {
        try {
            System.err.println("In deleteUser:: " + user.toString());
            user.setEnabled(false);
            userRepository.save(user);
            return new ResponseWrapper<UserModel>("Success", true, new ArrayList<>());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseWrapper<>("Failed in user service: " + e.getMessage(), false, new ArrayList<>());
        }
    }
}
