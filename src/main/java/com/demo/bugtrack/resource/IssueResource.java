package com.demo.bugtrack.resource;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.bugtrack.dto.IssueDTO;
import com.demo.bugtrack.error.BTBadRequestException;
import com.demo.bugtrack.service.IssueService;

/**
 * @author gaurav_t
 * @since 24-06-2021
 *
 */
@RestController
@RequestMapping("/api")
public class IssueResource {

	private final Logger log = LoggerFactory.getLogger(IssueResource.class);

	private final IssueService issueService;

	public IssueResource(IssueService issueService) {
		this.issueService = issueService;
	}

	/**
	 * Post /issues : create new Issue
	 * 
	 * @param issueDTO the issue to create
	 * @return the ResponseEntity with status 201 (Created) and with body the new
	 *         issue, or with status 400 (Bad Request) if the issue has already an
	 *         ID
	 * @throws URISyntaxException
	 */
	@PostMapping("/issues")
	public ResponseEntity<IssueDTO> createIssue(@Valid @RequestBody IssueDTO issueDTO) throws URISyntaxException {
		log.debug("REST request to save Issue : {}", issueDTO);
		if (issueDTO.getId() != null) {
			throw new BTBadRequestException("A new Issue cannot already have an ID.", "Issue", "idexists");
		}
		IssueDTO result = issueService.save(issueDTO);
		return ResponseEntity.created(new URI("/api/issues" + result.getId())).body(result);

	}

	/**
	 * PUT /issues : Updates an existing issue.
	 * 
	 * @param issueDTO the issue to update
	 * @return the ResponseEntity with status 200 (OK) and with body the updated
	 *         Issues, or with status 400 (Bad Request)
	 */
	@PutMapping("/issues")
	public ResponseEntity<IssueDTO> updateIssue(@RequestBody IssueDTO issueDTO) {
		log.debug("REST request to update issue : {}", issueDTO);
		if (issueDTO.getId() == null) {
			throw new BTBadRequestException("ID is required to update the entity.", "issue", "idinvalid");
		}
		IssueDTO result = issueService.update(issueDTO);
		return ResponseEntity.ok().body(result);
	}

	/**
	 * GET /issues
	 * 
	 * @return the ResponseEntity with status 200 (OK) and the list of issue DTO in
	 *         body
	 */
	@GetMapping("/issues")
	public List<IssueDTO> findAll() {
		log.debug("Rest request to find all issues.");
		return issueService.findAll();
	}

	/**
	 * GET /issues/:id : get the "id" issue
	 * 
	 * @param id the id of issue to retrieve
	 * @return the ResponseEntity with status 200 (OK) and with body the issue, or
	 *         with status 404 (Not Found)
	 */
	@GetMapping("/issues/{id}")
	public ResponseEntity<IssueDTO> findOne(@PathVariable(name = "id") Integer id) {
		log.debug("Rest request to find issue by id: {}", id);
		return ResponseEntity.ok(issueService.findOne(id));
	}

}
