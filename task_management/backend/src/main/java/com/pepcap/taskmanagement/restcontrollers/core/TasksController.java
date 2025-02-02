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
import com.pepcap.taskmanagement.application.core.tasks.ITasksAppService;
import com.pepcap.taskmanagement.application.core.tasks.dto.*;
import com.pepcap.taskmanagement.application.core.projects.IProjectsAppService;
import com.pepcap.taskmanagement.application.core.timesheets.ITimesheetsAppService;
import com.pepcap.taskmanagement.application.core.timesheets.dto.FindTimesheetsByIdOutput;
import com.pepcap.taskmanagement.application.core.authorization.users.IUsersAppService;
import java.util.*;
import java.time.*;
import java.net.MalformedURLException;
import com.pepcap.taskmanagement.commons.logging.LoggingHelper;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TasksController {

	@Qualifier("tasksAppService")
	@NonNull protected final ITasksAppService _tasksAppService;
    @Qualifier("projectsAppService")
	@NonNull  protected final IProjectsAppService  _projectsAppService;

    @Qualifier("timesheetsAppService")
	@NonNull  protected final ITimesheetsAppService  _timesheetsAppService;

    @Qualifier("usersAppService")
	@NonNull  protected final IUsersAppService  _usersAppService;

	@NonNull protected final LoggingHelper logHelper;

	@NonNull protected final Environment env;

    @PreAuthorize("hasAnyAuthority('TASKSENTITY_CREATE')")
	@RequestMapping(method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
	public ResponseEntity<CreateTasksOutput> create( @RequestBody @Valid CreateTasksInput tasks) {
		CreateTasksOutput output=_tasksAppService.create(tasks);
		return new ResponseEntity<>(output, HttpStatus.OK);
	}

	// ------------ Delete tasks ------------
	@PreAuthorize("hasAnyAuthority('TASKSENTITY_DELETE')")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, consumes = {"application/json"})
	public void delete(@PathVariable String id) {

    	FindTasksByIdOutput output = _tasksAppService.findById(Integer.valueOf(id));
    	if(output == null) {
    		throw new EntityNotFoundException(String.format("There does not exist a tasks with a id=%s", id));
    	}	

    	_tasksAppService.delete(Integer.valueOf(id));
    }


	// ------------ Update tasks ------------
    @PreAuthorize("hasAnyAuthority('TASKSENTITY_UPDATE')")
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = {"application/json"}, produces = {"application/json"})
	public ResponseEntity<UpdateTasksOutput> update(@PathVariable String id,  @RequestBody @Valid UpdateTasksInput tasks) {

	    FindTasksByIdOutput currentTasks = _tasksAppService.findById(Integer.valueOf(id));
		if(currentTasks == null) {
			throw new EntityNotFoundException(String.format("Unable to update. Tasks with id=%s not found.", id));
		}

		tasks.setVersiono(currentTasks.getVersiono());
	    UpdateTasksOutput output = _tasksAppService.update(Integer.valueOf(id),tasks);
		return new ResponseEntity<>(output, HttpStatus.OK);
	}
    

    @PreAuthorize("hasAnyAuthority('TASKSENTITY_READ')")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, consumes = {"application/json"}, produces = {"application/json"})
	public ResponseEntity<FindTasksByIdOutput> findById(@PathVariable String id) {

    	FindTasksByIdOutput output = _tasksAppService.findById(Integer.valueOf(id));
        if(output == null) {
    		throw new EntityNotFoundException("Not found");
    	}
    	
		return new ResponseEntity<>(output, HttpStatus.OK);
	}
    @PreAuthorize("hasAnyAuthority('TASKSENTITY_READ')")
	@RequestMapping(method = RequestMethod.GET, consumes = {"application/json"}, produces = {"application/json"})
	public ResponseEntity<List<FindTasksByIdOutput>> find(@RequestParam(value="search", required=false) String search, @RequestParam(value = "offset", required=false) String offset, @RequestParam(value = "limit", required=false) String limit, Sort sort) throws EntityNotFoundException, MalformedURLException {

		if (offset == null) { offset = env.getProperty("pepcap.offset.default"); }
		if (limit == null) { limit = env.getProperty("pepcap.limit.default"); }

		Pageable Pageable = new OffsetBasedPageRequest(Integer.parseInt(offset), Integer.parseInt(limit), sort);
		SearchCriteria searchCriteria = SearchUtils.generateSearchCriteriaObject(search);

		return new ResponseEntity<>(_tasksAppService.find(searchCriteria,Pageable), HttpStatus.OK);
	}
	
    @PreAuthorize("hasAnyAuthority('TASKSENTITY_READ')")
	@RequestMapping(value = "/{id}/projects", method = RequestMethod.GET, consumes = {"application/json"}, produces = {"application/json"})
	public ResponseEntity<GetProjectsOutput> getProjects(@PathVariable String id) {
    	GetProjectsOutput output= _tasksAppService.getProjects(Integer.valueOf(id));
		if(output ==null) {
			throw new EntityNotFoundException("Not found");
	    }		

		return new ResponseEntity<>(output, HttpStatus.OK);
	}
    @PreAuthorize("hasAnyAuthority('TASKSENTITY_READ')")
	@RequestMapping(value = "/{id}/timesheets", method = RequestMethod.GET, consumes = {"application/json"}, produces = {"application/json"})
	public ResponseEntity<List<FindTimesheetsByIdOutput>> getTimesheets(@PathVariable String id, @RequestParam(value="search", required=false) String search, @RequestParam(value = "offset", required=false) String offset, @RequestParam(value = "limit", required=false) String limit, Sort sort) throws EntityNotFoundException, MalformedURLException {
   		if (offset == null) { offset = env.getProperty("pepcap.offset.default"); }
		if (limit == null) { limit = env.getProperty("pepcap.limit.default"); }

		Pageable pageable = new OffsetBasedPageRequest(Integer.parseInt(offset), Integer.parseInt(limit), sort);

		SearchCriteria searchCriteria = SearchUtils.generateSearchCriteriaObject(search);
		Map<String,String> joinColDetails=_tasksAppService.parseTimesheetsJoinColumn(id);
		if(joinColDetails == null) {
			throw new EntityNotFoundException("Invalid join column");
		}

		searchCriteria.setJoinColumns(joinColDetails);

    	List<FindTimesheetsByIdOutput> output = _timesheetsAppService.find(searchCriteria,pageable);
    	
    	if(output == null) {
			throw new EntityNotFoundException("Not found");
		}
		
		return new ResponseEntity<>(output, HttpStatus.OK);
	}
    @PreAuthorize("hasAnyAuthority('TASKSENTITY_READ')")
	@RequestMapping(value = "/{id}/users", method = RequestMethod.GET, consumes = {"application/json"}, produces = {"application/json"})
	public ResponseEntity<GetUsersOutput> getUsers(@PathVariable String id) {
    	GetUsersOutput output= _tasksAppService.getUsers(Integer.valueOf(id));
		if(output ==null) {
			throw new EntityNotFoundException("Not found");
	    }		

		return new ResponseEntity<>(output, HttpStatus.OK);
	}
}


