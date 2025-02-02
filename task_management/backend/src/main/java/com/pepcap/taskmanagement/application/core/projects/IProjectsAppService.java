package com.pepcap.taskmanagement.application.core.projects;

import org.springframework.data.domain.Pageable;
import com.pepcap.taskmanagement.application.core.projects.dto.*;
import com.pepcap.taskmanagement.commons.search.SearchCriteria;
import java.net.MalformedURLException;
import java.util.*;

public interface IProjectsAppService {
	
	//CRUD Operations
	CreateProjectsOutput create(CreateProjectsInput projects);

    void delete(Integer id);

    UpdateProjectsOutput update(Integer id, UpdateProjectsInput input);

    FindProjectsByIdOutput findById(Integer id);


    List<FindProjectsByIdOutput> find(SearchCriteria search, Pageable pageable) throws MalformedURLException;
    
    //Join Column Parsers

	Map<String,String> parseTasksJoinColumn(String keysString);
}

