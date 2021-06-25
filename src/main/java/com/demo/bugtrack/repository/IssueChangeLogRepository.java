package com.demo.bugtrack.repository;

import java.time.Instant;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.demo.bugtrack.domain.Issue;
import com.demo.bugtrack.domain.IssueChangeLog;
import com.demo.bugtrack.domain.Project;
import com.demo.bugtrack.domain.enumeration.IssueState;
import com.demo.bugtrack.domain.enumeration.IssueType;

/**
 * @author gaurav_t
 * @since 24-06-2021
 *
 *        Repository of IssueChangeLog DB Operation
 * 
 */

@Repository
public interface IssueChangeLogRepository extends JpaRepository<IssueChangeLog, Integer> {

	List<IssueChangeLog> findByIssueAndStatusTrue(Issue issue);

	@Query("select issuelog from IssueChangeLog issuelog where issuelog.toState in ?1 and issuelog.changedOn between ?2 and ?3 and issuelog.issue.project = ?4 and issuelog.issue.issueType in ?5")
	List<IssueChangeLog> findByProjectAndTypeAndStateAndCreatedDate(List<IssueState> issueStates, Instant startDate,
			Instant endDate, Project project, List<IssueType> issueTypes);
}
