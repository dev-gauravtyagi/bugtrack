package com.demo.bugtrack.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.bugtrack.domain.Project;

/**
 * @author gaurav_t
 * @since 24-06-2021
 *
 * Repository of Project DB Operation
 */
@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {

	Optional<Project> findByProjectIdIgnoreCaseAndStatusTrue(String projectId);

	Optional<Project> findByIdAndStatusTrue(Integer id);

	List<Project> findByStatusTrue();
}
