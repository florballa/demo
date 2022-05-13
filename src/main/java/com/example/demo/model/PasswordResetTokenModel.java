package com.example.demo.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table
public class PasswordResetTokenModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String token;
	private String email;
	private Date expiryDate;
	
	public PasswordResetTokenModel() {}

	public PasswordResetTokenModel(String token, String email) {
		super();
		this.token = token;
		this.email = email;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	@Override
	public String toString() {
		return "PasswordResetTokenModel{" +
				"id=" + id +
				", token='" + token + '\'' +
				", email='" + email + '\'' +
				", expiryDate=" + expiryDate +
				'}';
	}
}
