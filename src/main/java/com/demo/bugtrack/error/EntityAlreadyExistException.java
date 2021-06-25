package com.demo.bugtrack.error;

public class EntityAlreadyExistException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public EntityAlreadyExistException(String property, String value) {
		super(String.format(
				"Resource with property %s and value %s already exists." + "Make sure to insert a unique value for %s",
				property, value, property));
	}
}
