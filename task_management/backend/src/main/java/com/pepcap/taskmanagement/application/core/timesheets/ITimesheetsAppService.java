package com.pepcap.taskmanagement.application.core.timesheets;

import org.springframework.data.domain.Pageable;
import com.pepcap.taskmanagement.application.core.timesheets.dto.*;
import com.pepcap.taskmanagement.commons.search.SearchCriteria;
import java.net.MalformedURLException;
import java.util.*;

public interface ITimesheetsAppService {
	
	//CRUD Operations
	CreateTimesheetsOutput create(CreateTimesheetsInput timesheets);

    void delete(Integer id);

    UpdateTimesheetsOutput update(Integer id, UpdateTimesheetsInput input);

    FindTimesheetsByIdOutput findById(Integer id);


    List<FindTimesheetsByIdOutput> find(SearchCriteria search, Pageable pageable) throws MalformedURLException;
	//Relationship Operations
	//Relationship Operations
    
    GetTasksOutput getTasks(Integer timesheetsid);
    
    GetUsersOutput getUsers(Integer timesheetsid);
    
    //Join Column Parsers
}

