package com.demo.bugtrack.domain;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.demo.bugtrack.domain.enumeration.IssueState;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author gaurav_t
 * @since 24-06-2021
 * 
 * Entity Class of Issue Change logs
 *
 */
@Entity
@Table(name = "issue_change_log")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IssueChangeLog {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, unique = true)
	private Integer id;

	@Enumerated(EnumType.STRING)
	@Column(name = "from_state", length = 50)
	private IssueState fromState;

	@Enumerated(EnumType.STRING)
	@Column(name = "to_state", length = 50)
	private IssueState toState;

	@Column(name = "changed_on")
	private Instant changedOn;

	@Column(name = "created_date")
	private Instant createdDate;

	@Column(name = "status")
	private Boolean status;

	@ManyToOne
	@JoinColumn(name = "issue_id")
	private Issue issue;
}
