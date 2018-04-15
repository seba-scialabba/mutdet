package com.magneto.mutantdetector.api.exceptionhandler;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.magneto.mutantdetector.exception.InvalidDnaSequenceException;

@ControllerAdvice
@RestController
@Slf4j
public class InvalidDnaSequenceExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(InvalidDnaSequenceException.class)
	public final ResponseEntity handleInvalidDnaSequenceException(InvalidDnaSequenceException ex, WebRequest request) {
		log.info("DNA sequence is not valid");
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}
}
