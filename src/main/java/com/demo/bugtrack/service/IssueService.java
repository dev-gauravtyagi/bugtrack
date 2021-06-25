package com.demo.bugtrack.service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.demo.bugtrack.domain.Issue;
import com.demo.bugtrack.domain.IssueChangeLog;
import com.demo.bugtrack.domain.Project;
import com.demo.bugtrack.domain.enumeration.IssueState;
import com.demo.bugtrack.dto.IssueChangeLogDTO;
import com.demo.bugtrack.dto.IssueDTO;
import com.demo.bugtrack.dto.ProjectDTO;
import com.demo.bugtrack.dto.StateSummariesDTO;
import com.demo.bugtrack.dto.WeeklySummariesDTO;
import com.demo.bugtrack.dto.WeeklySummaryRequestDTO;
import com.demo.bugtrack.error.BTBadRequestException;
import com.demo.bugtrack.error.BTEntityNotFoundException;
import com.demo.bugtrack.mapper.IssueChangeLogMapper;
import com.demo.bugtrack.mapper.IssueMapper;
import com.demo.bugtrack.repository.IssueChangeLogRepository;
import com.demo.bugtrack.repository.IssueRepository;
import com.demo.bugtrack.util.DateUtil;

/**
 * 
 * Service class for manage Issue
 * 
 * @author gaurav_t
 * @since 24-06-2021
 */
@Service
@Transactional
public class IssueService {

	private final Logger log = LoggerFactory.getLogger(IssueService.class);

	private final IssueRepository issueRepository;
	private final IssueChangeLogRepository issueChangeLogRepository;
	private final ProjectService projectService;

	public IssueService(IssueRepository issueRepository, ProjectService projectService,
			IssueChangeLogRepository issueChangeLogRepository) {
		this.issueRepository = issueRepository;
		this.projectService = projectService;
		this.issueChangeLogRepository = issueChangeLogRepository;
	}

	/**
	 * save a issue & issue change logs
	 * 
	 * @param issueDTO
	 * @return DTO of the persisted Entity
	 */
	public IssueDTO save(IssueDTO issueDTO) {
		log.debug("Request to save issue : {}", issueDTO);
		Issue issue = Optional.ofNullable(issueDTO).map(IssueMapper::toEntity).get();
		issue.setProject(projectService.findById(issueDTO.getProjectDTO().getId()).get());
		if (issueDTO.getId() == null) {
			issue.setIssueState(IssueState.OPEN);
			issue.setCreatedDate(Instant.now());
			issue.setStatus(true);
			Issue newIssue = issueRepository.save(issue);
			updateIssueChangeLog(newIssue, null);
			return Optional.ofNullable(newIssue).map(IssueMapper::toDTO).get();
		} else {
			throw new BTBadRequestException("New Issue can not have an Id", "Issue", "IDExist");
		}

	}

	/**
	 * update a issue & issue change logs
	 * 
	 * @param issueDTO
	 * @return DTO of the persisted Entity
	 */
	public IssueDTO update(IssueDTO issueDTO) {
		Issue issue = Optional.ofNullable(issueDTO).map(IssueMapper::toEntity).get();
		issue.setProject(projectService.findById(issueDTO.getProjectDTO().getId()).get());
		Optional<Issue> existingIssue = issueRepository.findByIdAndStatusTrue(issueDTO.getId());
		if (existingIssue.isPresent()) {
			issue.setUpdatedDate(Instant.now());
			issue.setCreatedDate(existingIssue.get().getCreatedDate());
			issue.setStatus(existingIssue.get().getStatus());
			if (!issue.getIssueState().equals(existingIssue.get().getIssueState())) {
				updateIssueChangeLog(issue, existingIssue.get().getIssueState());
			}
			return Optional.ofNullable(issueRepository.save(issue)).map(IssueMapper::toDTO).get();
		} else {
			throw new BTEntityNotFoundException("Issue", String.valueOf(issueDTO.getId()));
		}
	}

	private void updateIssueChangeLog(Issue issue, IssueState previousState) {
		log.debug("Request to save issue changelog: {}", issue);
		IssueChangeLog issueChangeLog = new IssueChangeLog();
		issueChangeLog.setChangedOn(Instant.now());
		issueChangeLog.setCreatedDate(Instant.now());
		issueChangeLog.setStatus(true);
		issueChangeLog.setIssue(issue);
		issueChangeLog.setFromState(previousState);
		issueChangeLog.setToState(issue.getIssueState());
		issueChangeLogRepository.save(issueChangeLog);
	}

	/**
	 * find all Issues
	 * 
	 * @return List<IssueDTO> the DTO of the entities
	 */
	public List<IssueDTO> findAll() {
		log.debug("Request to find all Issues.");
		return issueRepository.findByStatusTrue().stream().map(IssueMapper::toDTO).collect(Collectors.toList());
	}

	/**
	 * find entity by Id
	 * 
	 * @param id
	 * @return DTO of the entity class
	 */
	public IssueDTO findOne(Integer id) {
		log.debug("Request to find issue by Id: {}", id);
		return issueRepository.findByIdAndStatusTrue(id).map(IssueMapper::toDTO)
				.orElseThrow(() -> new BTEntityNotFoundException("Issue", String.valueOf(id)));
	}

	/**
	 * find all Issue of a project
	 * 
	 * @param projectDTO
	 * @return List<IssueDTO> the DTO of the entities
	 */
	public List<IssueDTO> findByProject(ProjectDTO projectDTO) {
		log.debug("Request to find issue by project: {}", projectDTO);
		Optional<Project> existingProject = projectService.findById(projectDTO.getId());
		if (existingProject.isPresent()) {
			List<IssueDTO> issueDTOs = new ArrayList<>();
			List<Issue> issues = issueRepository.findByProjectAndStatusTrue(existingProject.get());
			for (Issue issue : issues) {
				IssueDTO issueDTO = IssueMapper.toDTO(issue);
				issueDTO.setIssueChangeLogDTOs(findByIssueChangeLogs(issue));
				issueDTOs.add(issueDTO);
			}
			return issueDTOs;
		} else {
			throw new EntityNotFoundException();
		}
	}

	private List<IssueChangeLogDTO> findByIssueChangeLogs(Issue issue) {
		List<IssueChangeLogDTO> issueChangeLogDTOs = issueChangeLogRepository.findByIssueAndStatusTrue(issue).stream()
				.map(IssueChangeLogMapper::toDTO).collect(Collectors.toList());
		issueChangeLogDTOs.sort(Comparator.comparing(IssueChangeLogDTO::getChangedOn));
		return issueChangeLogDTOs;
	}

	/**
	 * find Weekly Summary Report
	 * 
	 * @param weeklySummaryDTO
	 * @return Map<String, Object> with weekly summary of each week
	 */
	public Map<String, Object> findWeeklySummaryReport(WeeklySummaryRequestDTO weeklySummaryDTO) {
		Map<String, Object> result = new HashMap<>();
		Optional<Project> project = projectService.findByProjectId(weeklySummaryDTO.getProjectId());
		if (project.isPresent()) {
			result.put("project_id", project.get().getProjectId());
			List<WeeklySummariesDTO> weeklySummariesDTOs = new ArrayList<>();

			int startWeekYear = 0, startWeek = 0, endWeekYear = 0, endWeek = 0;

			String[] fromWeek = weeklySummaryDTO.getFromWeek().split("W");
			if (fromWeek.length == 2) {
				startWeekYear = Integer.parseInt(fromWeek[0]);
				startWeek = Integer.parseInt(fromWeek[1]);
			}
			String[] toWeek = weeklySummaryDTO.getToWeek().split("W");
			if (fromWeek.length == 2) {
				endWeekYear = Integer.parseInt(toWeek[0]);
				endWeek = Integer.parseInt(toWeek[1]);
			}
			boolean overWeek = false;
			for (int i = startWeekYear; i <= endWeekYear; i++) {
				for (int j = startWeek; j <= DateUtil.getYearlyWeekCount(i); j++) {
					if (j > endWeek && i >= endWeekYear) {
						overWeek = true;
						break;
					}
					WeeklySummariesDTO weeklySummariesDTO = new WeeklySummariesDTO();
					LocalDate startLocalDate = DateUtil.getStartAndEndDateOfWeek(j, i);
					Instant startDate = startLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
					Instant endDate = DateUtil.getEndDateOfWeek(startLocalDate).atStartOfDay(ZoneId.systemDefault())
							.toInstant();
					System.out.println("startDate:: " + startDate + " endDate:: " + endDate);
					List<IssueChangeLog> issueChangeLogs = issueChangeLogRepository
							.findByProjectAndTypeAndStateAndCreatedDate(weeklySummaryDTO.getStates(), startDate,
									endDate, project.get(), weeklySummaryDTO.getTypes());
					weeklySummariesDTO.setWeeek(String.valueOf(i) + "W" + DateUtil.getFormatMonth(j));
					weeklySummariesDTO.setStateSummariesDTOs(getStateSummary(issueChangeLogs));
					weeklySummariesDTOs.add(weeklySummariesDTO);
				}
				if (overWeek) {
					break;
				}
			}
			result.put("weekly_summaries", weeklySummariesDTOs);
			return result;
		}
		return null;
	}

	private List<StateSummariesDTO> getStateSummary(List<IssueChangeLog> issueChangeLogs) {
		if (null != issueChangeLogs && issueChangeLogs.size() > 0) {
			List<StateSummariesDTO> stateSummariesDTOs = new ArrayList<>();
			Map<IssueState, List<IssueChangeLog>> issueGroupOnState = issueChangeLogs.stream()
					.collect(Collectors.groupingBy(IssueChangeLog::getToState));
			issueGroupOnState.forEach((k, v) -> {
				StateSummariesDTO stateSummariesDTO = new StateSummariesDTO();
				stateSummariesDTO.setState(k);
				stateSummariesDTO.setCount(v.size());
				List<IssueDTO> issueDTOs = new ArrayList<>();
				v.forEach(issueChangeLog -> {
					IssueDTO issue = findOne(issueChangeLog.getIssue().getId());
					issueDTOs.add(issue);
				});
				stateSummariesDTO.setIssueDTOs(issueDTOs);
				stateSummariesDTOs.add(stateSummariesDTO);
			});
			return stateSummariesDTOs;
		}
		return null;
	}

}
