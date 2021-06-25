package com.demo.bugtrack.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class BTEntityNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public BTEntityNotFoundException(String property, String value) {
		super(String.format("Resource %s not found for ID %s", property, value));
	}

}
