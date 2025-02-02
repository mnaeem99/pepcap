package com.pepcap.taskmanagement.restcontrollers.core;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.pepcap.taskmanagement.commons.search.SearchCriteria;
import com.pepcap.taskmanagement.commons.search.SearchUtils;
import com.pepcap.taskmanagement.commons.search.OffsetBasedPageRequest;
import com.pepcap.taskmanagement.application.core.projects.IProjectsAppService;
import com.pepcap.taskmanagement.application.core.projects.dto.*;
import com.pepcap.taskmanagement.application.core.tasks.ITasksAppService;
import com.pepcap.taskmanagement.application.core.tasks.dto.FindTasksByIdOutput;
import java.util.*;
import java.time.*;
import java.net.MalformedURLException;
import com.pepcap.taskmanagement.commons.logging.LoggingHelper;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectsController {

	@Qualifier("projectsAppService")
	@NonNull protected final IProjectsAppService _projectsAppService;
    @Qualifier("tasksAppService")
	@NonNull  protected final ITasksAppService  _tasksAppService;

	@NonNull protected final LoggingHelper logHelper;

	@NonNull protected final Environment env;

    @PreAuthorize("hasAnyAuthority('PROJECTSENTITY_CREATE')")
	@RequestMapping(method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
	public ResponseEntity<CreateProjectsOutput> create( @RequestBody @Valid CreateProjectsInput projects) {
		CreateProjectsOutput output=_projectsAppService.create(projects);
		return new ResponseEntity<>(output, HttpStatus.OK);
	}

	// ------------ Delete projects ------------
	@PreAuthorize("hasAnyAuthority('PROJECTSENTITY_DELETE')")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, consumes = {"application/json"})
	public void delete(@PathVariable String id) {

    	FindProjectsByIdOutput output = _projectsAppService.findById(Integer.valueOf(id));
    	if(output == null) {
    		throw new EntityNotFoundException(String.format("There does not exist a projects with a id=%s", id));
    	}	

    	_projectsAppService.delete(Integer.valueOf(id));
    }


	// ------------ Update projects ------------
    @PreAuthorize("hasAnyAuthority('PROJECTSENTITY_UPDATE')")
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = {"application/json"}, produces = {"application/json"})
	public ResponseEntity<UpdateProjectsOutput> update(@PathVariable String id,  @RequestBody @Valid UpdateProjectsInput projects) {

	    FindProjectsByIdOutput currentProjects = _projectsAppService.findById(Integer.valueOf(id));
		if(currentProjects == null) {
			throw new EntityNotFoundException(String.format("Unable to update. Projects with id=%s not found.", id));
		}

		projects.setVersiono(currentProjects.getVersiono());
	    UpdateProjectsOutput output = _projectsAppService.update(Integer.valueOf(id),projects);
		return new ResponseEntity<>(output, HttpStatus.OK);
	}
    

    @PreAuthorize("hasAnyAuthority('PROJECTSENTITY_READ')")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, consumes = {"application/json"}, produces = {"application/json"})
	public ResponseEntity<FindProjectsByIdOutput> findById(@PathVariable String id) {

    	FindProjectsByIdOutput output = _projectsAppService.findById(Integer.valueOf(id));
        if(output == null) {
    		throw new EntityNotFoundException("Not found");
    	}
    	
		return new ResponseEntity<>(output, HttpStatus.OK);
	}
    @PreAuthorize("hasAnyAuthority('PROJECTSENTITY_READ')")
	@RequestMapping(method = RequestMethod.GET, consumes = {"application/json"}, produces = {"application/json"})
	public ResponseEntity<List<FindProjectsByIdOutput>> find(@RequestParam(value="search", required=false) String search, @RequestParam(value = "offset", required=false) String offset, @RequestParam(value = "limit", required=false) String limit, Sort sort) throws EntityNotFoundException, MalformedURLException {

		if (offset == null) { offset = env.getProperty("pepcap.offset.default"); }
		if (limit == null) { limit = env.getProperty("pepcap.limit.default"); }

		Pageable Pageable = new OffsetBasedPageRequest(Integer.parseInt(offset), Integer.parseInt(limit), sort);
		SearchCriteria searchCriteria = SearchUtils.generateSearchCriteriaObject(search);

		return new ResponseEntity<>(_projectsAppService.find(searchCriteria,Pageable), HttpStatus.OK);
	}
	
    @PreAuthorize("hasAnyAuthority('PROJECTSENTITY_READ')")
	@RequestMapping(value = "/{id}/tasks", method = RequestMethod.GET, consumes = {"application/json"}, produces = {"application/json"})
	public ResponseEntity<List<FindTasksByIdOutput>> getTasks(@PathVariable String id, @RequestParam(value="search", required=false) String search, @RequestParam(value = "offset", required=false) String offset, @RequestParam(value = "limit", required=false) String limit, Sort sort) throws EntityNotFoundException, MalformedURLException {
   		if (offset == null) { offset = env.getProperty("pepcap.offset.default"); }
		if (limit == null) { limit = env.getProperty("pepcap.limit.default"); }

		Pageable pageable = new OffsetBasedPageRequest(Integer.parseInt(offset), Integer.parseInt(limit), sort);

		SearchCriteria searchCriteria = SearchUtils.generateSearchCriteriaObject(search);
		Map<String,String> joinColDetails=_projectsAppService.parseTasksJoinColumn(id);
		if(joinColDetails == null) {
			throw new EntityNotFoundException("Invalid join column");
		}

		searchCriteria.setJoinColumns(joinColDetails);

    	List<FindTasksByIdOutput> output = _tasksAppService.find(searchCriteria,pageable);
    	
    	if(output == null) {
			throw new EntityNotFoundException("Not found");
		}
		
		return new ResponseEntity<>(output, HttpStatus.OK);
	}
}


