package com.otp.service;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import com.otp.model.User;
import com.otp.repository.UserRepo;

@SpringBootTest
public class MailServiceTests {
	
//	@Mock
//	private UserRepo repo;
//	
//	@Mock
//	private User user;
	
	@Mock
	private JavaMailSender javaMailSender;
	
	@Spy
	@InjectMocks
	private MailService mailService;
	
	
	@Test
	public void TestMailSendMethod() {
		User u = new User();
		u.setEmailAddress("singhanjali7992@gmail.com");
		u.setOtp("908765");
		u.setStartTime(System.currentTimeMillis());
		u.setAttempts(3);
		String otp = "908651";
		SimpleMailMessage m = new SimpleMailMessage();
		m.setTo(u.getEmailAddress());
		m.setText("Text");
		m.setSubject("otp");
		javaMailSender.send(m);

		boolean result = mailService.sendEmail(u, otp);

		assertTrue(result);

	}

}

//doNothing().when(user).setEmailAddress(u.getEmailAddress());
//doNothing().when(user).setOtp(null);
//doNothing().when(user).setStartTime(anyLong());
//doNothing().when(user).setAttempts(anyInt());
//when(user.getEmailAddress()).thenReturn("singhanjali7992@gmail.com");
