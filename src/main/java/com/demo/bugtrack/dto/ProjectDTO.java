package com.demo.bugtrack.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author gaurav_t
 * @since 24-06-2021
 * 
 * DTO class of Project
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectDTO {

	private Integer id;
	private String projectId;
	private String description;

	private List<IssueDTO> issueDTOs;
}
