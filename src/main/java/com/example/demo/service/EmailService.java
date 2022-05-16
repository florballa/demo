package com.example.demo.service;

import com.example.demo.model.ResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

@Service
public class EmailService {
	
	@Autowired
	private JavaMailSender mailSender;
	
	public <T> ResponseWrapper<T> sendEmail(String recipientEmail, String token, String username) throws MessagingException, UnsupportedEncodingException {

		try {

			System.err.println("In sendEmail with recipient:: " + recipientEmail);

			String link = "http://localhost:8088/reset?token=" + token;

			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message);

			helper.setFrom("flor.balla97@gmail.com", "Demo");
			helper.setTo(recipientEmail);

			String subject = "Reset your password!";

			String content = "<p>Hello <b>" + username + "</b>,</p>"
					+ "<p>You have requested to reset your password.</p>"
					+ "<p>Click the link below to change your password:</p>"
					+ "<p><a href=\"" + link + "\">Change my password</a></p>"
					+ "<p>Ignore this email if you do remember your password, "
					+ "or you have not made the request.</p>"
					+ "<br>"
					+ "<p>Best regards,</p>"
					+ "<p><i>Demo Support Team</i></p>"
					+ "<hr>"
					+ "<br>"
					+ "<a href=\"mailto:demo@info.com\">demo@info.com</a>"
					+ "<br>"
					+ "<a href=\"tel:5554280940\">Call us at 555-428-0940</a>";

			helper.setSubject(subject);

			helper.setText(content, true);

			mailSender.send(message);

			return new ResponseWrapper<T>("Success!", true, new ArrayList<>());
		}
		catch (Exception e){
			e.printStackTrace();
			return new ResponseWrapper<>("Failed in email service exception: " + e.getMessage(), false, new ArrayList<>());
		}
	}

}