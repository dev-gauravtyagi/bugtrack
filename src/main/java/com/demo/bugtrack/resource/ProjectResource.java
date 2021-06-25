package com.demo.bugtrack.resource;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.bugtrack.dto.ProjectDTO;
import com.demo.bugtrack.error.BTBadRequestException;
import com.demo.bugtrack.service.ProjectService;

/**
 * @author gaurav_t
 * @since 24-06-2021
 *
 */
@RestController
@RequestMapping("/api")
public class ProjectResource {

	private final Logger log = LoggerFactory.getLogger(ProjectResource.class);

	private final ProjectService projectService;

	public ProjectResource(ProjectService projectService) {
		this.projectService = projectService;
	}

	/**
	 * Post /projects : create new Project
	 * 
	 * @param projectDTO the project to create
	 * @return the ResponseEntity with status 201 (Created) and with body the new
	 *         project, or with status 400 (Bad Request) if the project has already an
	 *         ID
	 * @throws URISyntaxException
	 */
	@PostMapping("/projects")
	public ResponseEntity<ProjectDTO> createProject(@Valid @RequestBody ProjectDTO projectDTO)
			throws URISyntaxException {
		log.debug("REST request to save Project : {}", projectDTO);
		if (projectDTO.getId() != null) {
			throw new BTBadRequestException("A new Project cannot already have an ID.", "project", "idexists");
		}
		ProjectDTO result = projectService.save(projectDTO);
		return ResponseEntity.created(new URI("/api/projects" + result.getId())).body(result);

	}

	/**
	 * PUT /projects : Updates an existing project.
	 * 
	 * @param projectDTO the project to update
	 * @return the ResponseEntity with status 200 (OK) and with body the updated
	 *         Project, or with status 400 (Bad Request)
	 */
	@PutMapping("/projects")
	public ResponseEntity<ProjectDTO> updateProject(@RequestBody ProjectDTO projectDTO) {
		log.debug("REST request to update Project : {}", projectDTO);
		if (projectDTO.getId() == null) {
			throw new BTBadRequestException("ID is required to update the entity.", "project", "idinvalid");
		}
		ProjectDTO result = projectService.save(projectDTO);
		return ResponseEntity.ok().body(result);
	}

	/**
	 * GET /projects
	 * 
	 * @return the ResponseEntity with status 200 (OK) and the list of Project DTO in body
	 */
	@GetMapping("/projects")
	public List<ProjectDTO> findAll() {
		log.debug("Rest request to find all Project.");
		return projectService.findAll();
	}

	/**
	 * GET /projects/:id : get the "id" project
	 * 
	 * @param id the id of project to retrieve
	 * @return the ResponseEntity with status 200 (OK) and with body the project DTO, or
	 *         with status 404 (Not Found)
	 */
	@GetMapping("/projects/{id}")
	public ResponseEntity<ProjectDTO> findOne(@PathVariable(name = "id") Integer id) {
		log.debug("Rest request to find all Project.");
		return ResponseEntity.ok(projectService.findOne(id));
	}

}
