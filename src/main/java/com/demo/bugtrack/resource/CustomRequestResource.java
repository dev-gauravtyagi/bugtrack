package com.demo.bugtrack.resource;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.bugtrack.dto.ProjectDTO;
import com.demo.bugtrack.dto.WeeklySummaryRequestDTO;
import com.demo.bugtrack.service.CustomRequestService;

/**
 * @author gaurav_t
 * @since 24-06-2021
 *
 */

@RestController
@RequestMapping("/api")
public class CustomRequestResource {

	private final Logger log = LoggerFactory.getLogger(CustomRequestResource.class);

	private final CustomRequestService customRequestService;

	public CustomRequestResource(CustomRequestService customRequestService) {
		this.customRequestService = customRequestService;
	}

	/**
	 * Post /getIssues: find project Issues and its change logs
	 * 
	 * @param projectDTO the DTO of project
	 * @return ProjectDTO the DTO of Project Entity
	 */
	@PostMapping("/getIssues")
	public ResponseEntity<ProjectDTO> findIssues(@RequestBody ProjectDTO projectDTO) {
		log.debug("Rest Request to get issues of Project: {}", projectDTO);
		return ResponseEntity.ok(customRequestService.findIssues(projectDTO));
	}

	/**
	 * Post /getWeeklySummary:
	 * 
	 * @param weeklySummaryDTO the DTO to request weekly summary 
	 * @return Map<String, Object> with response to weekly summary 
	 */
	@PostMapping("/getWeeklySummary")
	public ResponseEntity<Map<String, Object>> findWeeklySummary(@RequestBody WeeklySummaryRequestDTO weeklySummaryDTO) {
		log.debug("Rest Request to get weekly summary of Project: {}", weeklySummaryDTO);
		return ResponseEntity.ok(customRequestService.findWeeklySummary(weeklySummaryDTO));
	}

}
