package com.otp.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Optional;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;

import com.otp.exceptions.InvalidOtpException;
import com.otp.exceptions.OTPTime;
import com.otp.model.User;
import com.otp.repository.UserRepo;

@SpringBootTest
public class OTPServiceTests {
	
	private final int otp = 908765;
	private final String email = "singhanjali7992@gmail.com";
//	private User u = new User();
	
	@Mock
	private User user;
	
	@Mock
	private UserRepo repo;	
	
	@Spy
	@InjectMocks
	private OTPService otpService;
	
	@Test
	@Order(1)
	public void TestGenerateOTP() {

			int result = otpService.generateOTP();
			assertTrue(result>= 100000 && result<=999999);
			
			User u = new User();
			u.setEmailAddress(user.getEmailAddress());
			u.setAttempts(3);
			u.setOtp(String.valueOf(otp));
			u.setStartTime(System.currentTimeMillis());
			repo.save(u);
		
	}
	
	@Test
	@Order(2)
	public void TestValidateOTP(){
			User user = new User();
			user.setEmailAddress("singhanjali7992@gmail.com");
			user.setOtp("908765");
			user.setStartTime(System.currentTimeMillis());
			Optional<User> userOptional = Optional.of(user);
			when(repo.findById(anyString())).thenReturn(userOptional);
			String result = otpService.validateOTP(user, String.valueOf(otp));
			assertEquals(result, "valid");
		}
	
	@Test
	@Order(3)
	public void TestResendAllowed() {
		
		User user = new User();	user.setEmailAddress("singhanjali7992@gmail.com");
		user.setOtp("908765");
		user.setStartTime(1637043803787L);
		
		boolean flag = otpService.resendAllowed(user);
		
		assertTrue(flag);

	}
	
	@Test
	@Order(4)
	public void TestResendAllowedWhen() throws OTPTime ,IOException{

		User user = new User();
		user.setEmailAddress("singhanjali7992@gmail.com");
		user.setOtp("908765");
		user.setStartTime(System.currentTimeMillis());

		boolean result = otpService.resendAllowed(user);
		
//	    Assertions.assertThrows(OTPTime.class, () -> otpService.resendAllowed(user));
	    assertFalse(result);

	}

	/*@Test
	@Order(5)
	public void TestOTPLengthValidOrNot() {
		
		User u = new User();
		u.setEmailAddress(anyString());
		u.setOtp(anyString());
		u.setStartTime(System.currentTimeMillis());
		u.setAttempts(anyInt());
		
		String otp = "123";
		
		
		Assertions.assertThrows(InvalidOtpException.class,() -> otpService.validateOTP(u, otp));

	}
	*/
	
	@Test
	@Order(5)
	public void TestAttemptAvaialbleOrNot() {
		
		User u = new User();
		u.setEmailAddress("singhanjali7992@gmail.com");
		u.setOtp("567890");
		u.setStartTime(System.currentTimeMillis());
		u.setAttempts(1);
		
		boolean flag = otpService.attemptIsAvaiable(user);
		assertEquals(flag,true);

	}
	
	
	/*@Test
	@Order(6)
	public void TestOTPExpiredOrNot() {
		User u = new User();
		u.setEmailAddress("singhanjali7992@gmail.com");
		u.setAttempts(3);
		u.setOtp("123456");
		u.setStartTime(System.currentTimeMillis() - 120);
		
		String result = otpService.validateOTP(u, "234567");
		assertEquals(result, "otp-expired");
		
	}
	*/
	
}
