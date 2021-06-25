package com.demo.bugtrack.mapper;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.demo.bugtrack.domain.Issue;
import com.demo.bugtrack.dto.IssueDTO;

/**
 * @author gaurav_t
 * @since 24-06-2021
 *
 * Mapper Class for mapping Issue & IssueDTO
 *
 */

public class IssueMapper {

	public static List<IssueDTO> toDTOs(List<Issue> issues) {
		return issues.stream().filter(Objects::nonNull).map(IssueMapper::toDTO).collect(Collectors.toList());
	}

	public static List<Issue> toEntities(List<IssueDTO> issueDTOs) {
		return issueDTOs.stream().filter(Objects::nonNull).map(IssueMapper::toEntity).collect(Collectors.toList());
	}

	public static IssueDTO toDTO(Issue issue) {
		IssueDTO issueDTO = new IssueDTO();
		issueDTO.setId(issue.getId());
		issueDTO.setIssueHeading(issue.getIssueHeading());
		issueDTO.setIssueDescription(issue.getIssueDescription());
		issueDTO.setIssueState(issue.getIssueState());
		issueDTO.setIssueType(issue.getIssueType());
		return issueDTO;
	}

	public static Issue toEntity(IssueDTO issueDTO) {
		Issue issue = new Issue();
		issue.setId(issueDTO.getId());
		issue.setIssueHeading(issueDTO.getIssueHeading());
		issue.setIssueDescription(issueDTO.getIssueDescription());
		issue.setIssueState(issueDTO.getIssueState());
		issue.setIssueType(issueDTO.getIssueType());
		return issue;
	}

}
