package com.demo.bugtrack.error;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ErrorResponse {

	private Date errorTime;
	private String message;
	private String details;
	private List<ErrorProperties> errors = new ArrayList<>();
}
