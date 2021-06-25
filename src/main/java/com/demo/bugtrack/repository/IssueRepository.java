package com.demo.bugtrack.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.bugtrack.domain.Issue;
import com.demo.bugtrack.domain.Project;
import com.demo.bugtrack.domain.enumeration.IssueState;
import com.demo.bugtrack.domain.enumeration.IssueType;

/**
 * @author gaurav_t
 * @since 24-06-2021
 *
 * Repository of Issue DB Operation
 */
@Repository
public interface IssueRepository extends JpaRepository<Issue, Integer> {

	Optional<Issue> findByIdAndStatusTrue(Integer id);

	List<Issue> findByStatusTrue();

	List<Issue> findByProjectAndStatusTrue(Project project);

	List<Issue> findByProjectAndIssueTypeInAndIssueStateInAndStatusTrue(Project project, List<IssueType> issueTypes,
			List<IssueState> issueStates);

}
