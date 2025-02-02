package com.pepcap.taskmanagement.application.core.timesheets;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import com.pepcap.taskmanagement.application.core.timesheets.dto.*;
import com.pepcap.taskmanagement.domain.core.timesheets.ITimesheetsRepository;
import com.pepcap.taskmanagement.domain.core.timesheets.QTimesheets;
import com.pepcap.taskmanagement.domain.core.timesheets.Timesheets;
import com.pepcap.taskmanagement.domain.core.tasks.ITasksRepository;
import com.pepcap.taskmanagement.domain.core.tasks.Tasks;
import com.pepcap.taskmanagement.domain.core.authorization.users.IUsersRepository;
import com.pepcap.taskmanagement.domain.core.authorization.users.Users;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page; 
import org.springframework.data.domain.Pageable; 
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import com.pepcap.taskmanagement.commons.search.*;
import com.pepcap.taskmanagement.commons.logging.LoggingHelper;
import com.querydsl.core.BooleanBuilder;
import java.net.MalformedURLException;
import java.math.BigDecimal;
import java.time.*;
import java.util.*;
import javax.persistence.EntityNotFoundException;

@Service("timesheetsAppService")
@RequiredArgsConstructor
public class TimesheetsAppService implements ITimesheetsAppService {
    
	@Qualifier("timesheetsRepository")
	@NonNull protected final ITimesheetsRepository _timesheetsRepository;

	
    @Qualifier("tasksRepository")
	@NonNull protected final ITasksRepository _tasksRepository;

    @Qualifier("usersRepository")
	@NonNull protected final IUsersRepository _usersRepository;

	@Qualifier("ITimesheetsMapperImpl")
	@NonNull protected final ITimesheetsMapper mapper;

	@NonNull protected final LoggingHelper logHelper;

    @Transactional(propagation = Propagation.REQUIRED)
	public CreateTimesheetsOutput create(CreateTimesheetsInput input) {

		Timesheets timesheets = mapper.createTimesheetsInputToTimesheets(input);
		Tasks foundTasks = null;
		Users foundUsers = null;
	  	if(input.getTaskId()!=null) {
			foundTasks = _tasksRepository.findById(input.getTaskId()).orElse(null);
			
			if(foundTasks!=null) {
				foundTasks.addTimesheets(timesheets);
			}
		}
	  	if(input.getUserId()!=null) {
			foundUsers = _usersRepository.findById(input.getUserId()).orElse(null);
			
			if(foundUsers!=null) {
				foundUsers.addTimesheets(timesheets);
			}
		}

		Timesheets createdTimesheets = _timesheetsRepository.save(timesheets);
		return mapper.timesheetsToCreateTimesheetsOutput(createdTimesheets);
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public UpdateTimesheetsOutput update(Integer timesheetsId, UpdateTimesheetsInput input) {

		Timesheets existing = _timesheetsRepository.findById(timesheetsId).orElseThrow(() -> new EntityNotFoundException("Timesheets not found"));

		Timesheets timesheets = mapper.updateTimesheetsInputToTimesheets(input);
		Tasks foundTasks = null;
		Users foundUsers = null;
        
	  	if(input.getTaskId()!=null) { 
			foundTasks = _tasksRepository.findById(input.getTaskId()).orElse(null);
		
			if(foundTasks!=null) {
				foundTasks.addTimesheets(timesheets);
			}
		}
        
	  	if(input.getUserId()!=null) { 
			foundUsers = _usersRepository.findById(input.getUserId()).orElse(null);
		
			if(foundUsers!=null) {
				foundUsers.addTimesheets(timesheets);
			}
		}
		
		Timesheets updatedTimesheets = _timesheetsRepository.save(timesheets);
		return mapper.timesheetsToUpdateTimesheetsOutput(updatedTimesheets);
	}
	
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(Integer timesheetsId) {

		Timesheets existing = _timesheetsRepository.findById(timesheetsId).orElseThrow(() -> new EntityNotFoundException("Timesheets not found"));
	 	
        if(existing.getTasks() !=null)
        {
        existing.getTasks().removeTimesheets(existing);
        }
        if(existing.getUsers() !=null)
        {
        existing.getUsers().removeTimesheets(existing);
        }
        if(existing !=null) {
			_timesheetsRepository.delete(existing);
		}
	}
	
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public FindTimesheetsByIdOutput findById(Integer timesheetsId) {

		Timesheets foundTimesheets = _timesheetsRepository.findById(timesheetsId).orElse(null);
		if (foundTimesheets == null)  
			return null; 
 	   
 	    return mapper.timesheetsToFindTimesheetsByIdOutput(foundTimesheets);
	}

    //Tasks
	// ReST API Call - GET /timesheets/1/tasks
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
	public GetTasksOutput getTasks(Integer timesheetsId) {

		Timesheets foundTimesheets = _timesheetsRepository.findById(timesheetsId).orElse(null);
		if (foundTimesheets == null) {
			logHelper.getLogger().error("There does not exist a timesheets wth a id='{}'", timesheetsId);
			return null;
		}
		Tasks re = foundTimesheets.getTasks();
		return mapper.tasksToGetTasksOutput(re, foundTimesheets);
	}
	
    //Users
	// ReST API Call - GET /timesheets/1/users
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
	public GetUsersOutput getUsers(Integer timesheetsId) {

		Timesheets foundTimesheets = _timesheetsRepository.findById(timesheetsId).orElse(null);
		if (foundTimesheets == null) {
			logHelper.getLogger().error("There does not exist a timesheets wth a id='{}'", timesheetsId);
			return null;
		}
		Users re = foundTimesheets.getUsers();
		return mapper.usersToGetUsersOutput(re, foundTimesheets);
	}
	
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<FindTimesheetsByIdOutput> find(SearchCriteria search, Pageable pageable) throws MalformedURLException  {

		Page<Timesheets> foundTimesheets = _timesheetsRepository.findAll(search(search), pageable);
		List<Timesheets> timesheetsList = foundTimesheets.getContent();
		Iterator<Timesheets> timesheetsIterator = timesheetsList.iterator(); 
		List<FindTimesheetsByIdOutput> output = new ArrayList<>();

		while (timesheetsIterator.hasNext()) {
		Timesheets timesheets = timesheetsIterator.next();
 	    output.add(mapper.timesheetsToFindTimesheetsByIdOutput(timesheets));
		}
		return output;
	}
	
	protected BooleanBuilder search(SearchCriteria search) throws MalformedURLException {

		QTimesheets timesheets= QTimesheets.timesheetsEntity;
		if(search != null) {
			Map<String,SearchFields> map = new HashMap<>();
			for(SearchFields fieldDetails: search.getFields())
			{
				map.put(fieldDetails.getFieldName(),fieldDetails);
			}
			List<String> keysList = new ArrayList<String>(map.keySet());
			checkProperties(keysList);
			return searchKeyValuePair(timesheets, map,search.getJoinColumns());
		}
		return null;
	}
	
	protected void checkProperties(List<String> list) throws MalformedURLException  {
		for (int i = 0; i < list.size(); i++) {
			if(!(
		        list.get(i).replace("%20","").trim().equals("tasks") ||
				list.get(i).replace("%20","").trim().equals("taskId") ||
		        list.get(i).replace("%20","").trim().equals("users") ||
				list.get(i).replace("%20","").trim().equals("userId") ||
				list.get(i).replace("%20","").trim().equals("createdAt") ||
				list.get(i).replace("%20","").trim().equals("date") ||
				list.get(i).replace("%20","").trim().equals("hoursWorked") ||
				list.get(i).replace("%20","").trim().equals("id")
			)) 
			{
			 throw new MalformedURLException("Wrong URL Format: Property " + list.get(i) + " not found!" );
			}
		}
	}
	
	protected BooleanBuilder searchKeyValuePair(QTimesheets timesheets, Map<String,SearchFields> map,Map<String,String> joinColumns) {
		BooleanBuilder builder = new BooleanBuilder();
        
		Iterator<Map.Entry<String, SearchFields>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, SearchFields> details = iterator.next();

			if(details.getKey().replace("%20","").trim().equals("createdAt")) {
				if(details.getValue().getOperator().equals("equals") && SearchUtils.stringToLocalDateTime(details.getValue().getSearchValue()) !=null) {
					builder.and(timesheets.createdAt.eq(SearchUtils.stringToLocalDateTime(details.getValue().getSearchValue())));
				} else if(details.getValue().getOperator().equals("notEqual") && SearchUtils.stringToLocalDateTime(details.getValue().getSearchValue()) !=null) {
					builder.and(timesheets.createdAt.ne(SearchUtils.stringToLocalDateTime(details.getValue().getSearchValue())));
				} else if(details.getValue().getOperator().equals("range")) {
				   LocalDateTime startLocalDateTime= SearchUtils.stringToLocalDateTime(details.getValue().getStartingValue());
				   LocalDateTime endLocalDateTime= SearchUtils.stringToLocalDateTime(details.getValue().getEndingValue());
				   if(startLocalDateTime!=null && endLocalDateTime!=null) {
					   builder.and(timesheets.createdAt.between(startLocalDateTime,endLocalDateTime));
				   } else if(endLocalDateTime!=null) {
					   builder.and(timesheets.createdAt.loe(endLocalDateTime));
                   } else if(startLocalDateTime!=null) {
                	   builder.and(timesheets.createdAt.goe(startLocalDateTime));  
                   }
                }     
			}
			if(details.getKey().replace("%20","").trim().equals("date")) {
				if(details.getValue().getOperator().equals("equals") && SearchUtils.stringToLocalDate(details.getValue().getSearchValue()) !=null) {
					builder.and(timesheets.date.eq(SearchUtils.stringToLocalDate(details.getValue().getSearchValue())));
				} else if(details.getValue().getOperator().equals("notEqual") && SearchUtils.stringToLocalDate(details.getValue().getSearchValue()) !=null) {
					builder.and(timesheets.date.ne(SearchUtils.stringToLocalDate(details.getValue().getSearchValue())));
				} else if(details.getValue().getOperator().equals("range")) {
				   LocalDate startLocalDate= SearchUtils.stringToLocalDate(details.getValue().getStartingValue());
				   LocalDate endLocalDate= SearchUtils.stringToLocalDate(details.getValue().getEndingValue());
				   if(startLocalDate!=null && endLocalDate!=null) {
					   builder.and(timesheets.date.between(startLocalDate,endLocalDate));
				   } else if(endLocalDate!=null) {
					   builder.and(timesheets.date.loe(endLocalDate));
                   } else if(startLocalDate!=null) {
                	   builder.and(timesheets.date.goe(startLocalDate));  
                   }
                }     
			}
			if(details.getKey().replace("%20","").trim().equals("hoursWorked")) {
				if(details.getValue().getOperator().equals("contains") && NumberUtils.isCreatable(details.getValue().getSearchValue())) {
					builder.and(timesheets.hoursWorked.like(details.getValue().getSearchValue() + "%"));
				} else if(details.getValue().getOperator().equals("equals") && NumberUtils.isCreatable(details.getValue().getSearchValue())) {
					builder.and(timesheets.hoursWorked.eq(new BigDecimal(details.getValue().getSearchValue())));
				} else if(details.getValue().getOperator().equals("notEqual") && NumberUtils.isCreatable(details.getValue().getSearchValue())) {
					builder.and(timesheets.hoursWorked.ne(new BigDecimal(details.getValue().getSearchValue())));
				} else if(details.getValue().getOperator().equals("range")) {
				   if(NumberUtils.isCreatable(details.getValue().getStartingValue()) && NumberUtils.isCreatable(details.getValue().getEndingValue())) {
                	   builder.and(timesheets.hoursWorked.between(new BigDecimal(details.getValue().getStartingValue()), new BigDecimal(details.getValue().getEndingValue())));
                   } else if(NumberUtils.isCreatable(details.getValue().getStartingValue())) {
                	   builder.and(timesheets.hoursWorked.goe(new BigDecimal(details.getValue().getStartingValue())));
                   } else if(NumberUtils.isCreatable(details.getValue().getEndingValue())) {
                	   builder.and(timesheets.hoursWorked.loe(new BigDecimal(details.getValue().getEndingValue())));
				   }
				}
			}		
			if(details.getKey().replace("%20","").trim().equals("id")) {
				if(details.getValue().getOperator().equals("contains") && StringUtils.isNumeric(details.getValue().getSearchValue())) {
					builder.and(timesheets.id.like(details.getValue().getSearchValue() + "%"));
				} else if(details.getValue().getOperator().equals("equals") && StringUtils.isNumeric(details.getValue().getSearchValue())) {
					builder.and(timesheets.id.eq(Integer.valueOf(details.getValue().getSearchValue())));
				} else if(details.getValue().getOperator().equals("notEqual") && StringUtils.isNumeric(details.getValue().getSearchValue())) {
					builder.and(timesheets.id.ne(Integer.valueOf(details.getValue().getSearchValue())));
				} else if(details.getValue().getOperator().equals("range")) {
				   if(StringUtils.isNumeric(details.getValue().getStartingValue()) && StringUtils.isNumeric(details.getValue().getEndingValue())) {
                	   builder.and(timesheets.id.between(Integer.valueOf(details.getValue().getStartingValue()), Integer.valueOf(details.getValue().getEndingValue())));
                   } else if(StringUtils.isNumeric(details.getValue().getStartingValue())) {
                	   builder.and(timesheets.id.goe(Integer.valueOf(details.getValue().getStartingValue())));
                   } else if(StringUtils.isNumeric(details.getValue().getEndingValue())) {
                	   builder.and(timesheets.id.loe(Integer.valueOf(details.getValue().getEndingValue())));
				   }
				}
			}
	    
		     if(details.getKey().replace("%20","").trim().equals("tasks")) {
				if(details.getValue().getOperator().equals("contains") && StringUtils.isNumeric(details.getValue().getSearchValue())) {
					builder.and(timesheets.tasks.id.like(details.getValue().getSearchValue() + "%"));
				} else if(details.getValue().getOperator().equals("equals") && StringUtils.isNumeric(details.getValue().getSearchValue())) {
					builder.and(timesheets.tasks.id.eq(Integer.valueOf(details.getValue().getSearchValue())));
				} else if(details.getValue().getOperator().equals("notEqual") && StringUtils.isNumeric(details.getValue().getSearchValue())) {
					builder.and(timesheets.tasks.id.ne(Integer.valueOf(details.getValue().getSearchValue())));
				} else if(details.getValue().getOperator().equals("range")) {
				   if(StringUtils.isNumeric(details.getValue().getStartingValue()) && StringUtils.isNumeric(details.getValue().getEndingValue())) {
                	   builder.and(timesheets.tasks.id.between(Integer.valueOf(details.getValue().getStartingValue()), Integer.valueOf(details.getValue().getEndingValue())));
                   } else if(StringUtils.isNumeric(details.getValue().getStartingValue())) {
                	   builder.and(timesheets.tasks.id.goe(Integer.valueOf(details.getValue().getStartingValue())));
                   } else if(StringUtils.isNumeric(details.getValue().getEndingValue())) {
                	   builder.and(timesheets.tasks.id.loe(Integer.valueOf(details.getValue().getEndingValue())));
				   }
				}
			}
		     if(details.getKey().replace("%20","").trim().equals("users")) {
				if(details.getValue().getOperator().equals("contains") && StringUtils.isNumeric(details.getValue().getSearchValue())) {
					builder.and(timesheets.users.id.like(details.getValue().getSearchValue() + "%"));
				} else if(details.getValue().getOperator().equals("equals") && StringUtils.isNumeric(details.getValue().getSearchValue())) {
					builder.and(timesheets.users.id.eq(Integer.valueOf(details.getValue().getSearchValue())));
				} else if(details.getValue().getOperator().equals("notEqual") && StringUtils.isNumeric(details.getValue().getSearchValue())) {
					builder.and(timesheets.users.id.ne(Integer.valueOf(details.getValue().getSearchValue())));
				} else if(details.getValue().getOperator().equals("range")) {
				   if(StringUtils.isNumeric(details.getValue().getStartingValue()) && StringUtils.isNumeric(details.getValue().getEndingValue())) {
                	   builder.and(timesheets.users.id.between(Integer.valueOf(details.getValue().getStartingValue()), Integer.valueOf(details.getValue().getEndingValue())));
                   } else if(StringUtils.isNumeric(details.getValue().getStartingValue())) {
                	   builder.and(timesheets.users.id.goe(Integer.valueOf(details.getValue().getStartingValue())));
                   } else if(StringUtils.isNumeric(details.getValue().getEndingValue())) {
                	   builder.and(timesheets.users.id.loe(Integer.valueOf(details.getValue().getEndingValue())));
				   }
				}
			}
		}
		
		for (Map.Entry<String, String> joinCol : joinColumns.entrySet()) {
		if(joinCol != null && joinCol.getKey().equals("taskId")) {
		    builder.and(timesheets.tasks.id.eq(Integer.parseInt(joinCol.getValue())));
		}
        
		if(joinCol != null && joinCol.getKey().equals("tasks")) {
		    builder.and(timesheets.tasks.id.eq(Integer.parseInt(joinCol.getValue())));
        }
        }
		for (Map.Entry<String, String> joinCol : joinColumns.entrySet()) {
		if(joinCol != null && joinCol.getKey().equals("userId")) {
		    builder.and(timesheets.users.id.eq(Integer.parseInt(joinCol.getValue())));
		}
        
		if(joinCol != null && joinCol.getKey().equals("users")) {
		    builder.and(timesheets.users.id.eq(Integer.parseInt(joinCol.getValue())));
        }
        }
		return builder;
	}
	
    
    
}



