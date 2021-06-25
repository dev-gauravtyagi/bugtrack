package com.demo.bugtrack.dto;

import java.util.List;

import lombok.Data;

/**
 * @author gaurav_t
 * @since 24-06-2021
 * 
 * DTO class for Weekly Summary
 *
 */
@Data
public class WeeklySummariesDTO {

	private String weeek;
	private List<StateSummariesDTO> stateSummariesDTOs;

}
