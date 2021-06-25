package com.demo.bugtrack.dto;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.demo.bugtrack.domain.enumeration.IssueState;
import com.demo.bugtrack.domain.enumeration.IssueType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author gaurav_t
 * @since 24-06-2021
 * 
 *        DTO Class of Issue
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IssueDTO {
	private Integer id;

	@NotBlank(message = "Issue Heading is required.")
	private String issueHeading;

	@NotBlank(message = "Issue Description is required.")
	private String issueDescription;

	@NotNull(message = "Issue type is required.")
	private IssueType issueType;

	@NotNull(message = "Issue State is required.")
	private IssueState issueState;

	@NotNull(message = "Project is required.")
	private ProjectDTO projectDTO;
	
	private List<IssueChangeLogDTO> issueChangeLogDTOs;

}
