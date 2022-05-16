package com.example.demo.service;

import com.example.demo.model.PasswordResetTokenModel;
import com.example.demo.model.ResponseWrapper;
import com.example.demo.model.UserModel;
import com.example.demo.model.dto.UserDto;
import com.example.demo.repository.PasswordResetTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PasswordResetTokenService {

	@Autowired
	private PasswordResetTokenRepository repo;

	@Autowired
	private UserService userService;

	public void createPasswordResetTokenForUser(String email, String token) {
		System.err.println("In createPasswordResetTokenForUser with email:: " + email + ", and token:: " + token);
		PasswordResetTokenModel myToken = new PasswordResetTokenModel(token, email);
		myToken.setExpiryDate(new Date());
		repo.save(myToken);
	}

	public ResponseWrapper<PasswordResetTokenModel> validatePasswordResetToken(String token) {

		System.err.println("In validateResetToken with token:: " + token);

		try {

			ResponseWrapper<PasswordResetTokenModel> res;
			Optional<PasswordResetTokenModel> tokenOp = repo.findByToken(token);

			if (tokenOp.isEmpty())
				return new ResponseWrapper<PasswordResetTokenModel>("Can't find this token, or it has been used!", false, new ArrayList<>());

			PasswordResetTokenModel passToken = tokenOp.get();

			if (isTokenExpired(passToken)) {
				res = new ResponseWrapper<PasswordResetTokenModel>("Token expired", false, new ArrayList<>());
				deleteToken(passToken);
			} else {
				res = new ResponseWrapper<PasswordResetTokenModel>("Success", true, Arrays.asList(passToken));
			}
			return res;
		}catch (Exception e){
			e.printStackTrace();
			return new ResponseWrapper<>("Failed in password service exception: " + e.getMessage(), false, new ArrayList<>());
		}
	}

	private boolean isTokenExpired(PasswordResetTokenModel passToken) {
		final Calendar cal = Calendar.getInstance();
		long expire = passToken.getExpiryDate().getTime();
		long now = cal.getTimeInMillis();

		if (now - expire >= 60000 * 60)
			return true;
		return false;
	}

	public void deleteToken(PasswordResetTokenModel tokenModel) {
		System.err.println("In deleteToken with tokenModel:: " + tokenModel);
		try{
			repo.delete(tokenModel);
		}catch (Exception e){
			e.printStackTrace();
		}
	}

    public ResponseWrapper resetPassword(String token, String password) {
		System.err.println("In resetPassword with token:: " + token + ", and new password:: " + password.isEmpty());
		ResponseWrapper<PasswordResetTokenModel> validateRes = validatePasswordResetToken(token);
		try {
			if (validateRes.getStatus()) {
				PasswordResetTokenModel resetTokenModel = validateRes.getContent().get(0);
				Optional<UserModel> user = userService.getByEmail(resetTokenModel.getEmail());

				if (user.isPresent()) {
					user.get().setPassword(password);
					ResponseWrapper<UserDto> saveUserRes = userService.save(user.get());
					if (saveUserRes.getStatus()) {
						deleteToken(resetTokenModel);
						return new ResponseWrapper<>("Success", true, new ArrayList<>());
					}
				}
			}
			return validateRes;
		} catch (Exception e){
			e.printStackTrace();
			return new ResponseWrapper("Failed in password service: " + e.getMessage(), false, new ArrayList<>());
		}
    }
}