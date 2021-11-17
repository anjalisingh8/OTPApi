package com.otp.service;

import java.io.IOException;
import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.Optional;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import org.springframework.stereotype.Service;
import com.otp.model.User;
import com.otp.repository.UserRepo;

@Service
public class MailService {
	
	private final Logger logger = LoggerFactory.getLogger(MailService.class);
	
	private JavaMailSender javaMailSender;
	
//	@Autowired
//	private UserRepo repo;
//	
//	@Autowired
//	private User user;
	 
	
	@Autowired
	public MailService(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

	public boolean sendEmail(User u,String otp) throws MailException {
	
		
		logger.info("Info in sending mail method");
		
		Properties properties = new Properties();
		try {
			properties.load(getClass().getResourceAsStream("/config.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(u.getEmailAddress());
		mail.setSubject("Email Verification OTP");
		mail.setText("Your OTP for Email Verification is "+ otp);
		
		
		
		
		javaMailSender.send(mail);
		
		
//		Optional<User> user=repo.findById(u.getEmailAddress());
//		if(user.isEmpty()==false) {
//		User userEntity=user.get();
//		userEntity.setOtp(otp);
//		userEntity.setAttempts(userEntity.getAttempts()-1);
//		userEntity.setStartTime(System.currentTimeMillis());
//		repo.save(userEntity);
//		}
//		else {
//			User userEntity=user.get();
//			userEntity.setEmailAddress(u.getEmailAddress());
//			userEntity.setOtp(otp);
//			userEntity.setStartTime(System.currentTimeMillis());
//			userEntity.setAttempts(Integer.parseInt(properties.getProperty("totalNoOfAttempts")));
//			repo.save(userEntity);
//		}
		
		
		return true;
	}
	

}
