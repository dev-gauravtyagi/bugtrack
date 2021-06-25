package com.demo.bugtrack.service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.demo.bugtrack.domain.Project;
import com.demo.bugtrack.dto.ProjectDTO;
import com.demo.bugtrack.error.BTEntityNotFoundException;
import com.demo.bugtrack.mapper.ProjectMapper;
import com.demo.bugtrack.repository.ProjectRepository;

/**
 * @author gaurav_t
 * @since 24-06-2021
 * 
 *        Service class for manage Project
 *
 */
@Service
@Transactional
public class ProjectService {

	private final Logger log = LoggerFactory.getLogger(ProjectService.class);

	private final ProjectRepository projectRepository;

	public ProjectService(ProjectRepository projectRepository) {
		this.projectRepository = projectRepository;
	}

	/**
	 * save & update a project
	 * 
	 * @param projectDTO
	 * @return DTO of the persisted Entity
	 */
	public ProjectDTO save(ProjectDTO projectDTO) {
		log.debug("Request to save project : {}", projectDTO);
		Project project = Optional.ofNullable(projectDTO).map(ProjectMapper::toEntity).get();
		if (projectDTO.getId() == null) {
			project.setCreatedDate(Instant.now());
			project.setStatus(true);
		} else {
			Optional<Project> existingProject = projectRepository.findByIdAndStatusTrue(projectDTO.getId());
			if (existingProject.isPresent()) {
				project.setUpdatedDate(Instant.now());
				project.setCreatedDate(existingProject.get().getCreatedDate());
				project.setStatus(existingProject.get().getStatus());
			}
		}
		return Optional.ofNullable(projectRepository.save(project)).map(ProjectMapper::toDTO).get();
	}

	/**
	 * find all Projects
	 * 
	 * @return List<ProjectDTO> the DTO of the entities
	 */
	public List<ProjectDTO> findAll() {
		log.debug("Request to find all Projects DTO.");
		return projectRepository.findByStatusTrue().stream().map(ProjectMapper::toDTO).collect(Collectors.toList());
	}

	/**
	 * find entity by Id
	 * 
	 * @param id
	 * @return DTO of the entity class
	 */
	public ProjectDTO findOne(Integer id) {
		log.debug("Request to find project DTO by Id: {}", id);
		return projectRepository.findByIdAndStatusTrue(id).map(ProjectMapper::toDTO)
				.orElseThrow(() -> new BTEntityNotFoundException("Project", String.valueOf(id)));
	}

	/**
	 * find entity by projectId
	 * 
	 * @param projectId
	 * @return DTO of the entity class
	 */
	public Optional<Project> findByProjectId(String projectId) {
		log.debug("Request to find project by projectId: {}", projectId);
		return projectRepository.findByProjectIdIgnoreCaseAndStatusTrue(projectId);
	}

	/**
	 * find entity by Id
	 * 
	 * @param id
	 * @return entity class Object
	 */
	public Optional<Project> findById(Integer id) {
		log.debug("Request to find project by id: {}", id);
		return projectRepository.findByIdAndStatusTrue(id);
	}

}
