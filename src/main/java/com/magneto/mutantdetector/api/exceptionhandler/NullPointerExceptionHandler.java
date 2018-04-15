package com.magneto.mutantdetector.api.exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class NullPointerExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(NullPointerException.class)
	public final ResponseEntity handleNullPointerException(NullPointerException ex, WebRequest request) {
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
}
