package com.demo.bugtrack.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.demo.bugtrack.dto.IssueDTO;
import com.demo.bugtrack.dto.ProjectDTO;
import com.demo.bugtrack.dto.WeeklySummaryRequestDTO;
import com.demo.bugtrack.error.BTEntityNotFoundException;
import com.demo.bugtrack.mapper.ProjectMapper;

/**
 * @author gaurav_t
 * @since 24-06-2021
 *
 */
@Service
@Transactional
public class CustomRequestService {

	private final Logger log = LoggerFactory.getLogger(CustomRequestService.class);

	private final ProjectService projectService;
	private final IssueService issueService;

	public CustomRequestService(ProjectService projectService, IssueService issueService) {
		this.projectService = projectService;
		this.issueService = issueService;
	}

	/**
	 * find all issue of the project with change logs
	 * 
	 * @param projectDTO
	 * @return project DTO with all the issues & issue change logs
	 */
	public ProjectDTO findIssues(ProjectDTO projectDTO) {
		log.debug("Request to find issues of project: {}", projectDTO);
		Optional<ProjectDTO> existingProject = projectService.findByProjectId(projectDTO.getProjectId())
				.map(ProjectMapper::toDTO);
		if (existingProject.isPresent()) {
			List<IssueDTO> issueDTOs = issueService.findByProject(existingProject.get());
			existingProject.get().setIssueDTOs(issueDTOs);
			return existingProject.get();
		} else {
			throw new BTEntityNotFoundException("Project", projectDTO.getProjectId());
		}
	}

	/**
	 * find the weekly summary report
	 * 
	 * @param weeklySummaryDTO
	 * @return Project DTO with weekly summary
	 */
	public Map<String, Object> findWeeklySummary(WeeklySummaryRequestDTO weeklySummaryDTO) {
		log.debug("Request to find weekly Summary: {}", weeklySummaryDTO);
		return issueService.findWeeklySummaryReport(weeklySummaryDTO);
	}

}
