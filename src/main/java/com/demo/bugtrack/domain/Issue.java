package com.demo.bugtrack.domain;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.demo.bugtrack.domain.enumeration.IssueState;
import com.demo.bugtrack.domain.enumeration.IssueType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author gaurav_t
 * @since 24-06-2021
 * 
 * Entity class for Issue
 *
 */
@Entity
@Table(name = "issue")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Issue {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, unique = true)
	private Integer id;

	@Column(name = "issue_heading", length = 255)
	private String issueHeading;

	@Column(name = "issue_description", length = 500)
	private String issueDescription;

	@Enumerated(EnumType.STRING)
	@Column(name = "issue_type", length = 50)
	private IssueType issueType;

	@Enumerated(EnumType.STRING)
	@Column(name = "issue_state", length = 50)
	private IssueState issueState;

	@Column(name = "created_date")
	private Instant createdDate;

	@Column(name = "updated_date")
	private Instant updatedDate;

	@Column(name = "status")
	private Boolean status;

	@ManyToOne
	@JoinColumn(name = "project_id")
	private Project project;

	@OneToMany(mappedBy = "issue")
	private Set<IssueChangeLog> issueChangeLogs = new HashSet<>();

}
