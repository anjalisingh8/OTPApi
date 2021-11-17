package com.otp.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;

import com.otp.exceptions.OTPTime;
import com.otp.model.User;
import com.otp.service.MailService;
import com.otp.service.OTPService;

@SpringBootTest
public class OTPControllerTests {
	
	private  final int otp = 123456;
	private  final String email = "anjali04112001@gmail.com";
	
	//create mock objects for dependencies
	@Mock
    private MailService notification;//mock object
	@Mock
	private User user;
	@Mock
	private OTPService otpService;
	@Spy
	@InjectMocks //it injects the mocked objects as a dependency, sets mocked objects
	private OTPController otpController; //test Object
	
	@Test
	public void TestSendWhenEmailSentSuccessfully() {
		
		
		when(otpService.generateOTP()).thenReturn(otp);
		//void methods get mocked by doNothing
		doNothing().when(user).setEmailAddress(anyString());
		when(notification.sendEmail(user,String.valueOf(otp))).thenReturn(true);
		String result = otpController.send(email);
		
		
		assertEquals(result,"verify-otp");
	}
	@Test
	public void TestSendWhenEmailDidNotSentSuccessfully()  {
		 
		
		when(otpService.generateOTP()).thenReturn(otp);
		//void methods get mocked by doNothing
		doNothing().when(user).setEmailAddress(anyString());
		when(notification.sendEmail(user,String.valueOf(otp))).thenReturn(false);
		String result = otpController.send(email);
		assertEquals(result,"wrongOtp");
	}
	
	/*@Test
	public void TestWhenResendOTPSmallerThan60Seconds() throws OTPTime{

		when(otpService.generateOTP()).thenReturn(otp);
		when(notification.sendEmail(user,String.valueOf(otp))).thenReturn(true);
		when(user.getStartTime()).thenReturn((long) 50);
		Assertions.assertThrows(OTPTime.class, () -> otpController.resendOtp());
	}
	*/
	/*@Test
	public void TestWhenResendOTPLargerThan60Seconds(){
		
		when(otpService.generateOTP()).thenReturn(otp);
		when(notification.sendEmail(user,String.valueOf(otp))).thenReturn(false);
		when(user.getStartTime()).thenReturn((long) 70);
		Assertions.assertThrows(OTPTime.class, () -> otpController.resendOtp());
	}
	*/
	
	@Test
	public void TestResendOTPSentSuccessfully() {
		
		when(otpService.generateOTP()).thenReturn(otp);
		when(notification.sendEmail(user,String.valueOf(otp))).thenReturn(true);
		when(otpService.resendAllowed(user)).thenReturn(true);
		when(otpService.attemptIsAvaiable(user)).thenReturn(true);
		String result;
		try {
			result = otpController.resendOtp();
			assertEquals(result,"verify-otp");
//			Assertions.assertThrows(OTPTime.class, () -> otpController.resendOtp());
		} catch (OTPTime e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void TestResendOTPDidNotSentSuccessfully(){
		
		when(otpService.generateOTP()).thenReturn(otp);
		when(notification.sendEmail(user,String.valueOf(otp))).thenReturn(false);
		
		String result;
		try {
			result = otpController.resendOtp();
			assertEquals(result,"wrongOtp");
//			Assertions.assertThrows(OTPTime.class, () -> otpController.resendOtp());
		} catch (OTPTime e) {
			e.printStackTrace();
		}

	}
	
	@Test 
	public void TestValidateOTP() throws Exception{
		when(otpService.validateOTP(user, email)).thenReturn(email);
//		Assertions.assertThrows(Exception.class, () -> otpController.validateOtp(anyString()));
		String result = otpController.validateOtp(email);
		assert (result) != null;
	}
	
}
