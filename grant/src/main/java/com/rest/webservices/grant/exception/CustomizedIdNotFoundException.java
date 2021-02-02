package com.rest.webservices.grant.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CustomizedIdNotFoundException extends RuntimeException {

	// overwrite the constructor to provide message
	public CustomizedIdNotFoundException(String message) {
		super(message + " not found");
		// TODO Auto-generated constructor stub
	}

}
