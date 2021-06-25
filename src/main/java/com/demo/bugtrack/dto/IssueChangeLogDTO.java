package com.demo.bugtrack.dto;

import java.time.Instant;

import com.demo.bugtrack.domain.enumeration.IssueState;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author gaurav_t
 * @since 24-06-2021
 * 
 * DTO class of Issue Change Log
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IssueChangeLogDTO {
	private Integer id;
	private IssueState fromState;
	private IssueState toState;
	private Instant changedOn;
	private IssueDTO issueDTO;
}
