package com.demo.bugtrack.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BTBadRequestException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public BTBadRequestException(String message, String entityName, String errorKey) {
		super(message);
	}
}
