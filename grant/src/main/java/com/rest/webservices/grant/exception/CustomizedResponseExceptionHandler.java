package com.rest.webservices.grant.exception;

import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice // For sharing across multiple controller classes
@RestController
public class CustomizedResponseExceptionHandler extends ResponseEntityExceptionHandler {

	// overwrite every time an exception happens
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> handleAllException(Exception ex, WebRequest request) {
		ExceptionResponses exceptionResponses = new ExceptionResponses(new Date(), ex.getMessage(),
				request.getDescription(false));

		return new ResponseEntity(exceptionResponses, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	// Customize for idNotFoundException
	@ExceptionHandler(CustomizedIdNotFoundException.class)
	public final ResponseEntity<Object> handleFamilyMemberNotFoundException(CustomizedIdNotFoundException ex,
			WebRequest request) {
		ExceptionResponses exceptionResponses = new ExceptionResponses(new Date(), ex.getMessage(),
				request.getDescription(false));

		return new ResponseEntity(exceptionResponses, HttpStatus.NOT_FOUND);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		// return the exception reason (ex.getBindingResult)
		ExceptionResponses exceptionResponses = new ExceptionResponses(new Date(), "Validation not valid",
				ex.getBindingResult().toString());

		return new ResponseEntity(exceptionResponses, HttpStatus.BAD_REQUEST);
	}
}
