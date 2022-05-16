package com.example.demo.controller;

import com.example.demo.model.PasswordResetTokenModel;
import com.example.demo.model.ResponseWrapper;
import com.example.demo.model.UserModel;
import com.example.demo.service.EmailService;
import com.example.demo.service.PasswordResetTokenService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/general")
public class GeneralEndpoint {

	@Autowired
	private EmailService emailService;

	@Autowired
	private UserService userService;

	@Autowired
	private PasswordResetTokenService passwordService;

//	@PostMapping("/signup")
//	public ResponseWrapper<UserModel> signUp(@RequestBody UserModel user){
//		return userService.saveOrUpdateUser(user);
//	}

	@PostMapping("/sendEmailForPassword")
	public <T> ResponseWrapper sendLinkToEmail(@RequestBody Map<String, Object> email) {
		try {
			System.err.println("Inside send link");
			String userEmail = email.get("email").toString();
			Optional<UserModel> user = userService.getByEmail(userEmail);
			if (user.isPresent()) {
				String token = UUID.randomUUID().toString();
				passwordService.createPasswordResetTokenForUser(userEmail, token);
				return emailService.sendEmail(userEmail, token, user.get().getUsername());
			}
			return new ResponseWrapper<T>("User not found, please provide valid email!", false, new ArrayList<>());
		} catch (UnsupportedEncodingException | MessagingException e) {
			e.printStackTrace();
			return new ResponseWrapper<T>("Failed, " + e.getMessage(), false, new ArrayList<>());
		}
	}

	@PostMapping("/validateResetToken")
	public ResponseWrapper<PasswordResetTokenModel> validateResetToken(@RequestBody Map<String, Object> token) {
		return passwordService.validatePasswordResetToken(token.get("token").toString());
	}

	@PostMapping("/resetPassword")
	public ResponseWrapper resetPassword(@RequestBody Map<String, Object> obj){
		return passwordService.resetPassword(obj.get("token").toString(), obj.get("password").toString());
	}
}
