package com.otp.service;
import java.io.IOException;
import java.util.Properties;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.otp.exceptions.InvalidOtpException;
import com.otp.exceptions.OTPTime;
import com.otp.model.User;
import com.otp.repository.UserRepo;


@Service
public class OTPService {

	@Autowired
	private User user;
	
	@Autowired
	private UserRepo repo;
	
    public int generateOTP(){
    	Properties properties = new Properties();
		try {
			properties.load(getClass().getResourceAsStream("/config.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
    	Random random = new Random();
    	int otp = Integer.parseInt(properties.getProperty("otpMin")) + random.nextInt(Integer.parseInt(properties.getProperty("otpMax")));
    	
    	user.setEmailAddress(user.getEmailAddress());
		user.setAttempts(Integer.parseInt(properties.getProperty("totalNoOfAttempts")));
		user.setOtp(String.valueOf(otp));
		user.setStartTime(System.currentTimeMillis());
		repo.save(user);
    	
    	return otp;
 }
    
    public boolean resendAllowed(User user) {
    	
    	long time = System.currentTimeMillis();
    	boolean flag = true;
    	
    	try {
        	Properties properties = new Properties();
			properties.load(getClass().getResourceAsStream("/config.properties"));
			if((time - user.getStartTime())/1000 < Integer.parseInt(properties.getProperty("expiryTime"))) {
				flag = false;
				throw new OTPTime("Please wait for 60 seconds to resend the OTP");
			}
			else
				flag = true;
		} 
    	 catch (OTPTime e) {
 			System.out.println(e);
 			e.printStackTrace();
 		}
    	catch (IOException e) {
			System.out.println(e);
			e.printStackTrace();
		}

    	return flag;
    }
    public String validateOTP(User user, String otp) {
    	try {
    		
    		Properties properties = new Properties();
        	properties.load(getClass().getResourceAsStream("/config.properties"));
        	
        	if(otp.length() < Integer.parseInt(properties.getProperty("otpSize")) ) {
    			throw new InvalidOtpException("OTP is wrong check again");
    		}
    	}
    	catch (InvalidOtpException e) {
    		System.out.println(e);
    		e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	User u = repo.findById(user.getEmailAddress()).orElse(null);
    	long time = System.currentTimeMillis();
    	
    	long timeDiff = (time - u.getStartTime())/1000;
    	
    	String result = " ";
    	try {
    		Properties properties = new Properties();
			properties.load(getClass().getResourceAsStream("/config.properties"));
			
	    	if(u.getOtp().equals(otp) && timeDiff < Integer.parseInt(properties.getProperty("expiryTime"))) {
	    		return "valid";
	    	}
	    	else if(timeDiff > Integer.parseInt(properties.getProperty("expiryTime"))) {
	    		result = "otp-expired";
	    	}
	    	else
	    		result = "wrongOtp";
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	
		return result;
    	
    }
    
    public boolean attemptIsAvaiable(User user) {
    	
    	boolean flag = true;
    	
		try {
			Properties properties = new Properties();
			properties.load(getClass().getResourceAsStream("/config.properties"));
	
	    	System.out.println(user.getAttempts());
	    	int attempt = user.getAttempts() - 1;
	    	user.setAttempts(attempt);
	    	repo.save(user);
	    	System.out.println(user.getAttempts());
			
			if(user.getAttempts() > Integer.parseInt(properties.getProperty("totalNoOfAttempts"))) {
				flag = false;
				return flag;
			}
			else
				return flag;
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	return flag;

    }
}