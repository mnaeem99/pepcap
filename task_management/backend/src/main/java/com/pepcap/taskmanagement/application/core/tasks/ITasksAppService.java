package com.pepcap.taskmanagement.application.core.tasks;

import org.springframework.data.domain.Pageable;
import com.pepcap.taskmanagement.application.core.tasks.dto.*;
import com.pepcap.taskmanagement.commons.search.SearchCriteria;
import java.net.MalformedURLException;
import java.util.*;

public interface ITasksAppService {
	
	//CRUD Operations
	CreateTasksOutput create(CreateTasksInput tasks);

    void delete(Integer id);

    UpdateTasksOutput update(Integer id, UpdateTasksInput input);

    FindTasksByIdOutput findById(Integer id);


    List<FindTasksByIdOutput> find(SearchCriteria search, Pageable pageable) throws MalformedURLException;
	//Relationship Operations
	//Relationship Operations
    
    GetProjectsOutput getProjects(Integer tasksid);
    
    GetUsersOutput getUsers(Integer tasksid);
    
    //Join Column Parsers

	Map<String,String> parseTimesheetsJoinColumn(String keysString);
}

