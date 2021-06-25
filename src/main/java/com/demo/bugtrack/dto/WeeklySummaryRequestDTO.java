package com.demo.bugtrack.dto;

import java.util.List;

import com.demo.bugtrack.domain.enumeration.IssueState;
import com.demo.bugtrack.domain.enumeration.IssueType;

import lombok.Data;

/**
 * @author gaurav_t
 * @since 24-06-2021
 * 
 * DTO class for Weekly Summary Request
 *
 */
@Data
public class WeeklySummaryRequestDTO {
	private String projectId;
	private String fromWeek;
	private String toWeek;
	private List<IssueState> states;
	private List<IssueType> types;
}
