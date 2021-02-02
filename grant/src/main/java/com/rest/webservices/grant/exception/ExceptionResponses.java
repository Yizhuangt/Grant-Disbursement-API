package com.rest.webservices.grant.exception;

import java.util.Date;

public class ExceptionResponses {
	//to create a standard exception response structure
	
	private Date timestamp; // time stamp of exception
	private String message; // exception message
	private String details; // exception details
			
	public ExceptionResponses(Date timestamp, String message, String details) {
		super();
		this.timestamp = timestamp;
		this.message = message;
		this.details = details;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
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
}
