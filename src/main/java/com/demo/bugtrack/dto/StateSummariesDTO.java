package com.demo.bugtrack.dto;

import java.util.List;

import com.demo.bugtrack.domain.enumeration.IssueState;

import lombok.Data;

/**
 * @author gaurav_t
 * @since 24-06-2021
 * 
 * DTO class for State Summary
 *
 */
@Data
public class StateSummariesDTO {

	private IssueState state;
	private int count;
	private List<IssueDTO> issueDTOs;
}
