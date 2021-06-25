package com.demo.bugtrack.error;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class BTExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> handleBTException(Exception ex, WebRequest request) throws Exception {
		ErrorResponse errorResponse = new ErrorResponse(new Date(), ex.getMessage(), request.getDescription(false),
				null);
		return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(BTBadRequestException.class)
	public final ResponseEntity<Object> handleBadRequestException(BTBadRequestException ex, WebRequest req) {
		ErrorResponse errorResponse = new ErrorResponse(new Date(), ex.getMessage(), req.getDescription(false), null);
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(BTEntityNotFoundException.class)
	public final ResponseEntity<Object> handleEntityNotFoundException(BTEntityNotFoundException ex, WebRequest req) {
		ErrorResponse errorResponse = new ErrorResponse(new Date(), ex.getMessage(), req.getDescription(false), null);
		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(EntityAlreadyExistException.class)
	public final ResponseEntity<Object> handleHISAlreadyExistExceptionException(EntityAlreadyExistException ex,
			WebRequest req) {
		ErrorResponse errorResponse = new ErrorResponse(new Date(), ex.getMessage(), req.getDescription(false), null);
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public final ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException exception,
			WebRequest webRequest) throws IOException {
		List<ErrorProperties> errorProperties = exception.getConstraintViolations().stream()
				.map(error -> new ErrorProperties(error.getPropertyPath().toString(), error.getMessage()))
				.collect(Collectors.toList());
		ErrorResponse errorResponse = new ErrorResponse(new Date(), exception.getMessage(),
				webRequest.getDescription(false), errorProperties);
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest req) {
		ErrorResponse errorResponse = new ErrorResponse(new Date(),
				ex.getBindingResult().getFieldError().getDefaultMessage(), req.getDescription(false), null);
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);

	}

}
