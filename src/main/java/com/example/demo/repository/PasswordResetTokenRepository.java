package com.example.demo.repository;

import com.example.demo.model.PasswordResetTokenModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetTokenModel, Long> {
	public Optional<PasswordResetTokenModel> findByToken(String token);
}
