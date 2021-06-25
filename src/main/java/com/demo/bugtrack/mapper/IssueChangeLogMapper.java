package com.demo.bugtrack.mapper;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.demo.bugtrack.domain.IssueChangeLog;
import com.demo.bugtrack.dto.IssueChangeLogDTO;

/**
 * @author gaurav_t
 * @since 24-06-2021
 *
 * Mapper Class for mapping IssueChangeLog & IssueChangeLogDTO
 *
 */

public class IssueChangeLogMapper {

	public static List<IssueChangeLogDTO> toDTOs(List<IssueChangeLog> issueChangeLogs) {
		return issueChangeLogs.stream().filter(Objects::nonNull).map(IssueChangeLogMapper::toDTO)
				.collect(Collectors.toList());
	}

	public static List<IssueChangeLog> toEntities(List<IssueChangeLogDTO> issueChangeLogDTOs) {
		return issueChangeLogDTOs.stream().filter(Objects::nonNull).map(IssueChangeLogMapper::toEntity)
				.collect(Collectors.toList());
	}

	public static IssueChangeLogDTO toDTO(IssueChangeLog issueChangeLog) {
		IssueChangeLogDTO issueChangeLogDTO = new IssueChangeLogDTO();
		issueChangeLogDTO.setId(issueChangeLog.getId());
		issueChangeLogDTO.setChangedOn(issueChangeLog.getChangedOn());
		issueChangeLogDTO.setFromState(issueChangeLog.getFromState());
		issueChangeLogDTO.setToState(issueChangeLog.getToState());
		return issueChangeLogDTO;
	}

	public static IssueChangeLog toEntity(IssueChangeLogDTO issueChangeLogDTO) {
		IssueChangeLog issueChangeLog = new IssueChangeLog();
		issueChangeLog.setId(issueChangeLogDTO.getId());
		issueChangeLog.setChangedOn(issueChangeLogDTO.getChangedOn());
		issueChangeLog.setFromState(issueChangeLogDTO.getFromState());
		issueChangeLog.setToState(issueChangeLogDTO.getToState());
		return issueChangeLog;
	}

}
