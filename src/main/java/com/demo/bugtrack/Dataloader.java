package com.demo.bugtrack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.demo.bugtrack.domain.enumeration.IssueState;
import com.demo.bugtrack.domain.enumeration.IssueType;
import com.demo.bugtrack.dto.IssueDTO;
import com.demo.bugtrack.dto.ProjectDTO;
import com.demo.bugtrack.service.IssueService;
import com.demo.bugtrack.service.ProjectService;

/**
 * @author gaurav_t
 * @since 24-06-2021
 * 
 * Initial database Data loader class
 * 
 */
@Component
public class Dataloader implements ApplicationRunner {

	private final Logger log = LoggerFactory.getLogger(Dataloader.class);

	private final ProjectService projectService;
	private final IssueService issueService;

	@Value("${bugtrack.loaddata}")
	private Boolean loadData;

	public Dataloader(ProjectService projectService, IssueService issueService) {
		this.projectService = projectService;
		this.issueService = issueService;
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		if (loadData) {
			log.info("----- Loading initial data -----");
			createProject();
			createIssue();
			updateIssue();
		}

	}

	private void createProject() {
		log.debug("----- Creating Projects -----");
		ProjectDTO projectDTO1 = new ProjectDTO(null, "prj1", "testing project1", null);
		projectService.save(projectDTO1);
		ProjectDTO projectDTO2 = new ProjectDTO(null, "prj2", "testing project2", null);
		projectService.save(projectDTO2);
		ProjectDTO projectDTO3 = new ProjectDTO(null, "prj3", "testing project3", null);
		projectService.save(projectDTO3);
		ProjectDTO projectDTO4 = new ProjectDTO(null, "prj4", "testing project4", null);
		projectService.save(projectDTO4);
		ProjectDTO projectDTO5 = new ProjectDTO(null, "prj5", "testing project5", null);
		projectService.save(projectDTO5);

	}

	private void createIssue() {
		log.debug("----- Creating Issues -----");
		IssueDTO issueDTO1 = new IssueDTO(null, "Button not working",
				"Login button not working after filling credentials", IssueType.BUG, IssueState.OPEN,
				new ProjectDTO(1, null, null, null), null);
		issueService.save(issueDTO1);
		IssueDTO issueDTO2 = new IssueDTO(null, "Project not save", "Error in while saving the projec", IssueType.BUG,
				IssueState.OPEN, new ProjectDTO(1, null, null, null), null);
		issueService.save(issueDTO2);
		IssueDTO issueDTO3 = new IssueDTO(null, "wrong Date capture", "Wrong date captured when saving issue",
				IssueType.BUG, IssueState.OPEN, new ProjectDTO(1, null, null, null), null);
		issueService.save(issueDTO3);
		IssueDTO issueDTO4 = new IssueDTO(null, "Task1", "Task1 Description", IssueType.TASK, IssueState.OPEN,
				new ProjectDTO(1, null, null, null), null);
		issueService.save(issueDTO4);
		IssueDTO issueDTO5 = new IssueDTO(null, "Task2", "Task2 Description", IssueType.TASK, IssueState.OPEN,
				new ProjectDTO(1, null, null, null), null);
		issueService.save(issueDTO5);
		IssueDTO issueDTO6 = new IssueDTO(null, "Enhancement1", "Enhancement1 Description", IssueType.ENHANCEMENT,
				IssueState.OPEN, new ProjectDTO(1, null, null, null), null);
		issueService.save(issueDTO6);
	}

	private void updateIssue() {
		log.debug("----- Updating Issues -----");
		IssueDTO issueDTO1 = new IssueDTO(1, "Button not working", "Login button not working after filling credentials",
				IssueType.BUG, IssueState.IN_PROGRESS, new ProjectDTO(1, null, null, null), null);
		issueService.update(issueDTO1);
		IssueDTO issueDTO2 = new IssueDTO(1, "Button not working", "Login button not working after filling credentials",
				IssueType.BUG, IssueState.TESTING, new ProjectDTO(1, null, null, null), null);
		issueService.update(issueDTO2);
		IssueDTO issueDTO3 = new IssueDTO(1, "Button not working", "Login button not working after filling credentials",
				IssueType.BUG, IssueState.DEPLOY, new ProjectDTO(1, null, null, null), null);
		issueService.update(issueDTO3);
		IssueDTO issueDTO4 = new IssueDTO(2, "Project not save", "Error in while saving the projec", IssueType.BUG,
				IssueState.IN_PROGRESS, new ProjectDTO(1, null, null, null), null);
		issueService.update(issueDTO4);
		IssueDTO issueDTO5 = new IssueDTO(2, "Project not save", "Error in while saving the projec", IssueType.BUG,
				IssueState.TESTING, new ProjectDTO(1, null, null, null), null);
		issueService.update(issueDTO5);
		IssueDTO issueDTO6 = new IssueDTO(2, "Project not save", "Error in while saving the projec", IssueType.BUG,
				IssueState.DEPLOY, new ProjectDTO(1, null, null, null), null);
		issueService.update(issueDTO6);
	}
}
