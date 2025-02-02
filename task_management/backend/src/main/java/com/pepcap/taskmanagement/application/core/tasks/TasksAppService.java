package com.pepcap.taskmanagement.application.core.tasks;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import com.pepcap.taskmanagement.application.core.tasks.dto.*;
import com.pepcap.taskmanagement.domain.core.tasks.ITasksRepository;
import com.pepcap.taskmanagement.domain.core.tasks.QTasks;
import com.pepcap.taskmanagement.domain.core.tasks.Tasks;
import com.pepcap.taskmanagement.domain.core.projects.IProjectsRepository;
import com.pepcap.taskmanagement.domain.core.projects.Projects;
import com.pepcap.taskmanagement.domain.core.authorization.users.IUsersRepository;
import com.pepcap.taskmanagement.domain.core.authorization.users.Users;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page; 
import org.springframework.data.domain.Pageable; 
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.lang3.StringUtils;
import com.pepcap.taskmanagement.commons.search.*;
import com.pepcap.taskmanagement.commons.logging.LoggingHelper;
import com.querydsl.core.BooleanBuilder;
import java.net.MalformedURLException;
import java.time.*;
import java.util.*;
import javax.persistence.EntityNotFoundException;

@Service("tasksAppService")
@RequiredArgsConstructor
public class TasksAppService implements ITasksAppService {
    
	@Qualifier("tasksRepository")
	@NonNull protected final ITasksRepository _tasksRepository;

	
    @Qualifier("projectsRepository")
	@NonNull protected final IProjectsRepository _projectsRepository;

    @Qualifier("usersRepository")
	@NonNull protected final IUsersRepository _usersRepository;

	@Qualifier("ITasksMapperImpl")
	@NonNull protected final ITasksMapper mapper;

	@NonNull protected final LoggingHelper logHelper;

    @Transactional(propagation = Propagation.REQUIRED)
	public CreateTasksOutput create(CreateTasksInput input) {

		Tasks tasks = mapper.createTasksInputToTasks(input);
		Projects foundProjects = null;
		Users foundUsers = null;
	  	if(input.getProjectId()!=null) {
			foundProjects = _projectsRepository.findById(input.getProjectId()).orElse(null);
			
			if(foundProjects!=null) {
				foundProjects.addTasks(tasks);
			}
		}
	  	if(input.getAssigneeId()!=null) {
			foundUsers = _usersRepository.findById(input.getAssigneeId()).orElse(null);
			
			if(foundUsers!=null) {
				foundUsers.addTasks(tasks);
			}
		}

		Tasks createdTasks = _tasksRepository.save(tasks);
		return mapper.tasksToCreateTasksOutput(createdTasks);
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public UpdateTasksOutput update(Integer tasksId, UpdateTasksInput input) {

		Tasks existing = _tasksRepository.findById(tasksId).orElseThrow(() -> new EntityNotFoundException("Tasks not found"));

		Tasks tasks = mapper.updateTasksInputToTasks(input);
		tasks.setTimesheetsSet(existing.getTimesheetsSet());
		Projects foundProjects = null;
		Users foundUsers = null;
        
	  	if(input.getProjectId()!=null) { 
			foundProjects = _projectsRepository.findById(input.getProjectId()).orElse(null);
		
			if(foundProjects!=null) {
				foundProjects.addTasks(tasks);
			}
		}
        
	  	if(input.getAssigneeId()!=null) { 
			foundUsers = _usersRepository.findById(input.getAssigneeId()).orElse(null);
		
			if(foundUsers!=null) {
				foundUsers.addTasks(tasks);
			}
		}
		
		Tasks updatedTasks = _tasksRepository.save(tasks);
		return mapper.tasksToUpdateTasksOutput(updatedTasks);
	}
	
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(Integer tasksId) {

		Tasks existing = _tasksRepository.findById(tasksId).orElseThrow(() -> new EntityNotFoundException("Tasks not found"));
	 	
        if(existing.getProjects() !=null)
        {
        existing.getProjects().removeTasks(existing);
        }
        if(existing.getUsers() !=null)
        {
        existing.getUsers().removeTasks(existing);
        }
        if(existing !=null) {
			_tasksRepository.delete(existing);
		}
	}
	
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public FindTasksByIdOutput findById(Integer tasksId) {

		Tasks foundTasks = _tasksRepository.findById(tasksId).orElse(null);
		if (foundTasks == null)  
			return null; 
 	   
 	    return mapper.tasksToFindTasksByIdOutput(foundTasks);
	}

    //Projects
	// ReST API Call - GET /tasks/1/projects
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
	public GetProjectsOutput getProjects(Integer tasksId) {

		Tasks foundTasks = _tasksRepository.findById(tasksId).orElse(null);
		if (foundTasks == null) {
			logHelper.getLogger().error("There does not exist a tasks wth a id='{}'", tasksId);
			return null;
		}
		Projects re = foundTasks.getProjects();
		return mapper.projectsToGetProjectsOutput(re, foundTasks);
	}
	
    //Users
	// ReST API Call - GET /tasks/1/users
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
	public GetUsersOutput getUsers(Integer tasksId) {

		Tasks foundTasks = _tasksRepository.findById(tasksId).orElse(null);
		if (foundTasks == null) {
			logHelper.getLogger().error("There does not exist a tasks wth a id='{}'", tasksId);
			return null;
		}
		Users re = foundTasks.getUsers();
		return mapper.usersToGetUsersOutput(re, foundTasks);
	}
	
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<FindTasksByIdOutput> find(SearchCriteria search, Pageable pageable) throws MalformedURLException  {

		Page<Tasks> foundTasks = _tasksRepository.findAll(search(search), pageable);
		List<Tasks> tasksList = foundTasks.getContent();
		Iterator<Tasks> tasksIterator = tasksList.iterator(); 
		List<FindTasksByIdOutput> output = new ArrayList<>();

		while (tasksIterator.hasNext()) {
		Tasks tasks = tasksIterator.next();
 	    output.add(mapper.tasksToFindTasksByIdOutput(tasks));
		}
		return output;
	}
	
	protected BooleanBuilder search(SearchCriteria search) throws MalformedURLException {

		QTasks tasks= QTasks.tasksEntity;
		if(search != null) {
			Map<String,SearchFields> map = new HashMap<>();
			for(SearchFields fieldDetails: search.getFields())
			{
				map.put(fieldDetails.getFieldName(),fieldDetails);
			}
			List<String> keysList = new ArrayList<String>(map.keySet());
			checkProperties(keysList);
			return searchKeyValuePair(tasks, map,search.getJoinColumns());
		}
		return null;
	}
	
	protected void checkProperties(List<String> list) throws MalformedURLException  {
		for (int i = 0; i < list.size(); i++) {
			if(!(
		        list.get(i).replace("%20","").trim().equals("projects") ||
				list.get(i).replace("%20","").trim().equals("projectId") ||
		        list.get(i).replace("%20","").trim().equals("users") ||
				list.get(i).replace("%20","").trim().equals("assigneeId") ||
				list.get(i).replace("%20","").trim().equals("createdAt") ||
				list.get(i).replace("%20","").trim().equals("description") ||
				list.get(i).replace("%20","").trim().equals("dueDate") ||
				list.get(i).replace("%20","").trim().equals("id") ||
				list.get(i).replace("%20","").trim().equals("name") ||
				list.get(i).replace("%20","").trim().equals("status")
			)) 
			{
			 throw new MalformedURLException("Wrong URL Format: Property " + list.get(i) + " not found!" );
			}
		}
	}
	
	protected BooleanBuilder searchKeyValuePair(QTasks tasks, Map<String,SearchFields> map,Map<String,String> joinColumns) {
		BooleanBuilder builder = new BooleanBuilder();
        
		Iterator<Map.Entry<String, SearchFields>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, SearchFields> details = iterator.next();

			if(details.getKey().replace("%20","").trim().equals("createdAt")) {
				if(details.getValue().getOperator().equals("equals") && SearchUtils.stringToLocalDateTime(details.getValue().getSearchValue()) !=null) {
					builder.and(tasks.createdAt.eq(SearchUtils.stringToLocalDateTime(details.getValue().getSearchValue())));
				} else if(details.getValue().getOperator().equals("notEqual") && SearchUtils.stringToLocalDateTime(details.getValue().getSearchValue()) !=null) {
					builder.and(tasks.createdAt.ne(SearchUtils.stringToLocalDateTime(details.getValue().getSearchValue())));
				} else if(details.getValue().getOperator().equals("range")) {
				   LocalDateTime startLocalDateTime= SearchUtils.stringToLocalDateTime(details.getValue().getStartingValue());
				   LocalDateTime endLocalDateTime= SearchUtils.stringToLocalDateTime(details.getValue().getEndingValue());
				   if(startLocalDateTime!=null && endLocalDateTime!=null) {
					   builder.and(tasks.createdAt.between(startLocalDateTime,endLocalDateTime));
				   } else if(endLocalDateTime!=null) {
					   builder.and(tasks.createdAt.loe(endLocalDateTime));
                   } else if(startLocalDateTime!=null) {
                	   builder.and(tasks.createdAt.goe(startLocalDateTime));  
                   }
                }     
			}
            if(details.getKey().replace("%20","").trim().equals("description")) {
				if(details.getValue().getOperator().equals("contains")) {
					builder.and(tasks.description.likeIgnoreCase("%"+ details.getValue().getSearchValue() + "%"));
				} else if(details.getValue().getOperator().equals("equals")) {
					builder.and(tasks.description.eq(details.getValue().getSearchValue()));
				} else if(details.getValue().getOperator().equals("notEqual")) {
					builder.and(tasks.description.ne(details.getValue().getSearchValue()));
				}
			}
			if(details.getKey().replace("%20","").trim().equals("dueDate")) {
				if(details.getValue().getOperator().equals("equals") && SearchUtils.stringToLocalDate(details.getValue().getSearchValue()) !=null) {
					builder.and(tasks.dueDate.eq(SearchUtils.stringToLocalDate(details.getValue().getSearchValue())));
				} else if(details.getValue().getOperator().equals("notEqual") && SearchUtils.stringToLocalDate(details.getValue().getSearchValue()) !=null) {
					builder.and(tasks.dueDate.ne(SearchUtils.stringToLocalDate(details.getValue().getSearchValue())));
				} else if(details.getValue().getOperator().equals("range")) {
				   LocalDate startLocalDate= SearchUtils.stringToLocalDate(details.getValue().getStartingValue());
				   LocalDate endLocalDate= SearchUtils.stringToLocalDate(details.getValue().getEndingValue());
				   if(startLocalDate!=null && endLocalDate!=null) {
					   builder.and(tasks.dueDate.between(startLocalDate,endLocalDate));
				   } else if(endLocalDate!=null) {
					   builder.and(tasks.dueDate.loe(endLocalDate));
                   } else if(startLocalDate!=null) {
                	   builder.and(tasks.dueDate.goe(startLocalDate));  
                   }
                }     
			}
			if(details.getKey().replace("%20","").trim().equals("id")) {
				if(details.getValue().getOperator().equals("contains") && StringUtils.isNumeric(details.getValue().getSearchValue())) {
					builder.and(tasks.id.like(details.getValue().getSearchValue() + "%"));
				} else if(details.getValue().getOperator().equals("equals") && StringUtils.isNumeric(details.getValue().getSearchValue())) {
					builder.and(tasks.id.eq(Integer.valueOf(details.getValue().getSearchValue())));
				} else if(details.getValue().getOperator().equals("notEqual") && StringUtils.isNumeric(details.getValue().getSearchValue())) {
					builder.and(tasks.id.ne(Integer.valueOf(details.getValue().getSearchValue())));
				} else if(details.getValue().getOperator().equals("range")) {
				   if(StringUtils.isNumeric(details.getValue().getStartingValue()) && StringUtils.isNumeric(details.getValue().getEndingValue())) {
                	   builder.and(tasks.id.between(Integer.valueOf(details.getValue().getStartingValue()), Integer.valueOf(details.getValue().getEndingValue())));
                   } else if(StringUtils.isNumeric(details.getValue().getStartingValue())) {
                	   builder.and(tasks.id.goe(Integer.valueOf(details.getValue().getStartingValue())));
                   } else if(StringUtils.isNumeric(details.getValue().getEndingValue())) {
                	   builder.and(tasks.id.loe(Integer.valueOf(details.getValue().getEndingValue())));
				   }
				}
			}
            if(details.getKey().replace("%20","").trim().equals("name")) {
				if(details.getValue().getOperator().equals("contains")) {
					builder.and(tasks.name.likeIgnoreCase("%"+ details.getValue().getSearchValue() + "%"));
				} else if(details.getValue().getOperator().equals("equals")) {
					builder.and(tasks.name.eq(details.getValue().getSearchValue()));
				} else if(details.getValue().getOperator().equals("notEqual")) {
					builder.and(tasks.name.ne(details.getValue().getSearchValue()));
				}
			}
            if(details.getKey().replace("%20","").trim().equals("status")) {
				if(details.getValue().getOperator().equals("contains")) {
					builder.and(tasks.status.likeIgnoreCase("%"+ details.getValue().getSearchValue() + "%"));
				} else if(details.getValue().getOperator().equals("equals")) {
					builder.and(tasks.status.eq(details.getValue().getSearchValue()));
				} else if(details.getValue().getOperator().equals("notEqual")) {
					builder.and(tasks.status.ne(details.getValue().getSearchValue()));
				}
			}
	    
		     if(details.getKey().replace("%20","").trim().equals("projects")) {
				if(details.getValue().getOperator().equals("contains") && StringUtils.isNumeric(details.getValue().getSearchValue())) {
					builder.and(tasks.projects.id.like(details.getValue().getSearchValue() + "%"));
				} else if(details.getValue().getOperator().equals("equals") && StringUtils.isNumeric(details.getValue().getSearchValue())) {
					builder.and(tasks.projects.id.eq(Integer.valueOf(details.getValue().getSearchValue())));
				} else if(details.getValue().getOperator().equals("notEqual") && StringUtils.isNumeric(details.getValue().getSearchValue())) {
					builder.and(tasks.projects.id.ne(Integer.valueOf(details.getValue().getSearchValue())));
				} else if(details.getValue().getOperator().equals("range")) {
				   if(StringUtils.isNumeric(details.getValue().getStartingValue()) && StringUtils.isNumeric(details.getValue().getEndingValue())) {
                	   builder.and(tasks.projects.id.between(Integer.valueOf(details.getValue().getStartingValue()), Integer.valueOf(details.getValue().getEndingValue())));
                   } else if(StringUtils.isNumeric(details.getValue().getStartingValue())) {
                	   builder.and(tasks.projects.id.goe(Integer.valueOf(details.getValue().getStartingValue())));
                   } else if(StringUtils.isNumeric(details.getValue().getEndingValue())) {
                	   builder.and(tasks.projects.id.loe(Integer.valueOf(details.getValue().getEndingValue())));
				   }
				}
			}
		     if(details.getKey().replace("%20","").trim().equals("users")) {
				if(details.getValue().getOperator().equals("contains") && StringUtils.isNumeric(details.getValue().getSearchValue())) {
					builder.and(tasks.users.id.like(details.getValue().getSearchValue() + "%"));
				} else if(details.getValue().getOperator().equals("equals") && StringUtils.isNumeric(details.getValue().getSearchValue())) {
					builder.and(tasks.users.id.eq(Integer.valueOf(details.getValue().getSearchValue())));
				} else if(details.getValue().getOperator().equals("notEqual") && StringUtils.isNumeric(details.getValue().getSearchValue())) {
					builder.and(tasks.users.id.ne(Integer.valueOf(details.getValue().getSearchValue())));
				} else if(details.getValue().getOperator().equals("range")) {
				   if(StringUtils.isNumeric(details.getValue().getStartingValue()) && StringUtils.isNumeric(details.getValue().getEndingValue())) {
                	   builder.and(tasks.users.id.between(Integer.valueOf(details.getValue().getStartingValue()), Integer.valueOf(details.getValue().getEndingValue())));
                   } else if(StringUtils.isNumeric(details.getValue().getStartingValue())) {
                	   builder.and(tasks.users.id.goe(Integer.valueOf(details.getValue().getStartingValue())));
                   } else if(StringUtils.isNumeric(details.getValue().getEndingValue())) {
                	   builder.and(tasks.users.id.loe(Integer.valueOf(details.getValue().getEndingValue())));
				   }
				}
			}
		}
		
		for (Map.Entry<String, String> joinCol : joinColumns.entrySet()) {
		if(joinCol != null && joinCol.getKey().equals("projectId")) {
		    builder.and(tasks.projects.id.eq(Integer.parseInt(joinCol.getValue())));
		}
        
		if(joinCol != null && joinCol.getKey().equals("projects")) {
		    builder.and(tasks.projects.id.eq(Integer.parseInt(joinCol.getValue())));
        }
        }
		for (Map.Entry<String, String> joinCol : joinColumns.entrySet()) {
		if(joinCol != null && joinCol.getKey().equals("assigneeId")) {
		    builder.and(tasks.users.id.eq(Integer.parseInt(joinCol.getValue())));
		}
        
		if(joinCol != null && joinCol.getKey().equals("users")) {
		    builder.and(tasks.users.id.eq(Integer.parseInt(joinCol.getValue())));
        }
        }
		return builder;
	}
	
    
	public Map<String,String> parseTimesheetsJoinColumn(String keysString) {
		
		Map<String,String> joinColumnMap = new HashMap<String,String>();
		joinColumnMap.put("taskId", keysString);
		  
		return joinColumnMap;
	}
    
    
}



