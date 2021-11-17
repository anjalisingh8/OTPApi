
package com.otp.exceptions;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;


@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(OTPTime.class)
	public ResponseEntity<?> handleAPIException(OTPTime exception, WebRequest request){
		
		ErrorHandling exceptionHandling = new ErrorHandling(new Date(),exception.getMessage(),request.getDescription(false));
		return new ResponseEntity<>(exceptionHandling, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(InvalidOtpException.class)
	public ResponseEntity<?> handleAPIException(InvalidOtpException exception, WebRequest request){
		
		ErrorHandling exceptionHandling = new ErrorHandling(new Date(),exception.getMessage(),request.getDescription(false));
		return new ResponseEntity<>(exceptionHandling, HttpStatus.NOT_FOUND);
	}
	
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> globalExceptionHandler(Exception e, WebRequest request){
		ErrorHandling exceptionHandling = new ErrorHandling(new Date(),e.getMessage(),request.getDescription(false));
		return new ResponseEntity<>(exceptionHandling, HttpStatus.INTERNAL_SERVER_ERROR);
		//annotation - search
	}
	

}
