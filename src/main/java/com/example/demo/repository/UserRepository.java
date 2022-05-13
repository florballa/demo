package com.example.demo.repository;

import com.example.demo.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {

    public Optional<UserModel> getByUsernameIgnoreCase(String username);
    public Optional<UserModel> getByEmailIgnoreCase(String email);
}
