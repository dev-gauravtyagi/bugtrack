package com.demo.bugtrack.domain;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author gaurav_t
 * @since 24-06-2021
 * 
 * Entity Class of Project
 *
 */
@Entity
@Table(name = "Project")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Project {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, unique = true)
	private Integer id;

	@Column(name = "project_id", length = 50, nullable = false, unique = true)
	private String projectId;

	@Column(name = "description", length = 255)
	private String description;

	@Column(name = "created_date")
	private Instant createdDate;

	@Column(name = "updated_date")
	private Instant updatedDate;

	@Column(name = "status")
	private Boolean status;

	@OneToMany(mappedBy = "project")
	private Set<Issue> issues = new HashSet<>();

}
