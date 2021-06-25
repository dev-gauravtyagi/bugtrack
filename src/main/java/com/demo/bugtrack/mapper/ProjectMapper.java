package com.demo.bugtrack.mapper;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.demo.bugtrack.domain.Project;
import com.demo.bugtrack.dto.ProjectDTO;

/**
 * @author gaurav_t
 * @since 24-06-2021
 *
 *        Mapper Class for mapping Project & ProjectDTO
 *
 */

public class ProjectMapper {

	public static List<ProjectDTO> toDTOs(List<Project> projects) {
		return projects.stream().filter(Objects::nonNull).map(ProjectMapper::toDTO).collect(Collectors.toList());
	}

	public static List<Project> toEntities(List<ProjectDTO> projectDTOs) {
		return projectDTOs.stream().filter(Objects::nonNull).map(ProjectMapper::toEntity).collect(Collectors.toList());
	}

	public static ProjectDTO toDTO(Project project) {
		ProjectDTO projectDTO = new ProjectDTO();
		projectDTO.setId(project.getId());
		projectDTO.setProjectId(project.getProjectId());
		projectDTO.setDescription(project.getDescription());
		return projectDTO;
	}

	public static Project toEntity(ProjectDTO projectDTO) {
		Project project = new Project();
		project.setId(projectDTO.getId());
		project.setProjectId(projectDTO.getProjectId());
		project.setDescription(projectDTO.getDescription());
		return project;
	}
}
