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
import com.pepcap.taskmanagement.application.core.timesheets.ITimesheetsAppService;
import com.pepcap.taskmanagement.application.core.timesheets.dto.*;
import com.pepcap.taskmanagement.application.core.tasks.ITasksAppService;
import com.pepcap.taskmanagement.application.core.authorization.users.IUsersAppService;
import java.util.*;
import java.time.*;
import java.net.MalformedURLException;
import com.pepcap.taskmanagement.commons.logging.LoggingHelper;

@RestController
@RequestMapping("/timesheets")
@RequiredArgsConstructor
public class TimesheetsController {

	@Qualifier("timesheetsAppService")
	@NonNull protected final ITimesheetsAppService _timesheetsAppService;
    @Qualifier("tasksAppService")
	@NonNull  protected final ITasksAppService  _tasksAppService;

    @Qualifier("usersAppService")
	@NonNull  protected final IUsersAppService  _usersAppService;

	@NonNull protected final LoggingHelper logHelper;

	@NonNull protected final Environment env;

    @PreAuthorize("hasAnyAuthority('TIMESHEETSENTITY_CREATE')")
	@RequestMapping(method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
	public ResponseEntity<CreateTimesheetsOutput> create( @RequestBody @Valid CreateTimesheetsInput timesheets) {
		CreateTimesheetsOutput output=_timesheetsAppService.create(timesheets);
		return new ResponseEntity<>(output, HttpStatus.OK);
	}

	// ------------ Delete timesheets ------------
	@PreAuthorize("hasAnyAuthority('TIMESHEETSENTITY_DELETE')")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, consumes = {"application/json"})
	public void delete(@PathVariable String id) {

    	FindTimesheetsByIdOutput output = _timesheetsAppService.findById(Integer.valueOf(id));
    	if(output == null) {
    		throw new EntityNotFoundException(String.format("There does not exist a timesheets with a id=%s", id));
    	}	

    	_timesheetsAppService.delete(Integer.valueOf(id));
    }


	// ------------ Update timesheets ------------
    @PreAuthorize("hasAnyAuthority('TIMESHEETSENTITY_UPDATE')")
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = {"application/json"}, produces = {"application/json"})
	public ResponseEntity<UpdateTimesheetsOutput> update(@PathVariable String id,  @RequestBody @Valid UpdateTimesheetsInput timesheets) {

	    FindTimesheetsByIdOutput currentTimesheets = _timesheetsAppService.findById(Integer.valueOf(id));
		if(currentTimesheets == null) {
			throw new EntityNotFoundException(String.format("Unable to update. Timesheets with id=%s not found.", id));
		}

		timesheets.setVersiono(currentTimesheets.getVersiono());
	    UpdateTimesheetsOutput output = _timesheetsAppService.update(Integer.valueOf(id),timesheets);
		return new ResponseEntity<>(output, HttpStatus.OK);
	}
    

    @PreAuthorize("hasAnyAuthority('TIMESHEETSENTITY_READ')")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, consumes = {"application/json"}, produces = {"application/json"})
	public ResponseEntity<FindTimesheetsByIdOutput> findById(@PathVariable String id) {

    	FindTimesheetsByIdOutput output = _timesheetsAppService.findById(Integer.valueOf(id));
        if(output == null) {
    		throw new EntityNotFoundException("Not found");
    	}
    	
		return new ResponseEntity<>(output, HttpStatus.OK);
	}
    @PreAuthorize("hasAnyAuthority('TIMESHEETSENTITY_READ')")
	@RequestMapping(method = RequestMethod.GET, consumes = {"application/json"}, produces = {"application/json"})
	public ResponseEntity<List<FindTimesheetsByIdOutput>> find(@RequestParam(value="search", required=false) String search, @RequestParam(value = "offset", required=false) String offset, @RequestParam(value = "limit", required=false) String limit, Sort sort) throws EntityNotFoundException, MalformedURLException {

		if (offset == null) { offset = env.getProperty("pepcap.offset.default"); }
		if (limit == null) { limit = env.getProperty("pepcap.limit.default"); }

		Pageable Pageable = new OffsetBasedPageRequest(Integer.parseInt(offset), Integer.parseInt(limit), sort);
		SearchCriteria searchCriteria = SearchUtils.generateSearchCriteriaObject(search);

		return new ResponseEntity<>(_timesheetsAppService.find(searchCriteria,Pageable), HttpStatus.OK);
	}
	
    @PreAuthorize("hasAnyAuthority('TIMESHEETSENTITY_READ')")
	@RequestMapping(value = "/{id}/tasks", method = RequestMethod.GET, consumes = {"application/json"}, produces = {"application/json"})
	public ResponseEntity<GetTasksOutput> getTasks(@PathVariable String id) {
    	GetTasksOutput output= _timesheetsAppService.getTasks(Integer.valueOf(id));
		if(output ==null) {
			throw new EntityNotFoundException("Not found");
	    }		

		return new ResponseEntity<>(output, HttpStatus.OK);
	}
    @PreAuthorize("hasAnyAuthority('TIMESHEETSENTITY_READ')")
	@RequestMapping(value = "/{id}/users", method = RequestMethod.GET, consumes = {"application/json"}, produces = {"application/json"})
	public ResponseEntity<GetUsersOutput> getUsers(@PathVariable String id) {
    	GetUsersOutput output= _timesheetsAppService.getUsers(Integer.valueOf(id));
		if(output ==null) {
			throw new EntityNotFoundException("Not found");
	    }		

		return new ResponseEntity<>(output, HttpStatus.OK);
	}
}


