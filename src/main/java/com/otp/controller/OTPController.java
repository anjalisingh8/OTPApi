package com.otp.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


import com.otp.exceptions.OTPTime;
import com.otp.model.User;
import com.otp.service.MailService;
import com.otp.service.OTPService;


@Controller
public class OTPController {
	
	private final Logger logger = LoggerFactory.getLogger(OTPController.class); 
	
	@Autowired
	private MailService notification; //automatically creates setters and creates and injects the dependencies using reflections
	
	@Autowired
	private User user;
	
	@Autowired
	private OTPService otpService;
	
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index() {
		logger.info("Infor method in index");
		return "index";
	}

	
	@PostMapping("/generateOtp")
	public String send(@RequestParam(name="email") String email) {
		
		logger.info("Info method in send");
		System.out.println("Method");
		int otp  = otpService.generateOTP();
		
		
		user.setEmailAddress(email);
		
		boolean flag = notification.sendEmail(user,String.valueOf(otp));
		
		//branch- two branches - 75 and 78 flow distributes
		if(flag) {
			System.out.println("otp verify");
			return "verify-otp";
		}else {
			return "wrongOtp";
		}

	}
	@PostMapping("/resendOtp")
	public String resendOtp() throws OTPTime {
		
		logger.info("Info method in resendOtp");
		boolean isResendAllowed = otpService.resendAllowed(user);
		boolean isAvailable = otpService.attemptIsAvaiable(user);
		System.out.println(isAvailable);
		int otp = otpService.generateOTP();
		boolean flag = notification.sendEmail(user,String.valueOf(otp));
		
			if(flag && isResendAllowed && isAvailable) {
				System.out.println("otp resent again");
				return "verify-otp";
			}else {
				return "wrongOtp";
			}
	}
	
	
	@PostMapping(value ="/validateOtp")
	public String validateOtp(@RequestParam(name="otp") String otp) throws Exception {
		
		logger.info("Infor method in validate");
		System.out.println("Inside validate");
		
	
		String result = otpService.validateOTP(user,otp);
		
		return result;
		
	}
	
	
}
