package com.demo.bugtrack.error;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorProperties {

	private String fieldName;
	private String message;

}
