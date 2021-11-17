package com.otp.exceptions;

import java.util.Date;

public class ErrorHandling {
	
	private Date timespam;
	private String message;
	private String details;
	
	public Date getTimespam() {
		return timespam;
	}
	public void setTimespam(Date timespam) {
		this.timespam = timespam;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	
	
	public ErrorHandling(Date timespam, String message, String details) {
		super();
		this.timespam = timespam;
		this.message = message;
		this.details = details;
	}
	@Override
	public String toString() {
		return "ErrorHandling [timespam=" + timespam + ", message=" + message + ", details=" + details + "]";
	}

}
