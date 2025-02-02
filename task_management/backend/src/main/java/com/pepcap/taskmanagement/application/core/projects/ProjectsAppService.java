package com.pepcap.taskmanagement.application.core.projects;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import com.pepcap.taskmanagement.application.core.projects.dto.*;
import com.pepcap.taskmanagement.domain.core.projects.IProjectsRepository;
import com.pepcap.taskmanagement.domain.core.projects.QProjects;
import com.pepcap.taskmanagement.domain.core.projects.Projects;


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

@Service("projectsAppService")
@RequiredArgsConstructor
public class ProjectsAppService implements IProjectsAppService {
    
	@Qualifier("projectsRepository")
	@NonNull protected final IProjectsRepository _projectsRepository;

	
	@Qualifier("IProjectsMapperImpl")
	@NonNull protected final IProjectsMapper mapper;

	@NonNull protected final LoggingHelper logHelper;

    @Transactional(propagation = Propagation.REQUIRED)
	public CreateProjectsOutput create(CreateProjectsInput input) {

		Projects projects = mapper.createProjectsInputToProjects(input);

		Projects createdProjects = _projectsRepository.save(projects);
		return mapper.projectsToCreateProjectsOutput(createdProjects);
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public UpdateProjectsOutput update(Integer projectsId, UpdateProjectsInput input) {

		Projects existing = _projectsRepository.findById(projectsId).orElseThrow(() -> new EntityNotFoundException("Projects not found"));

		Projects projects = mapper.updateProjectsInputToProjects(input);
		projects.setTasksSet(existing.getTasksSet());
		
		Projects updatedProjects = _projectsRepository.save(projects);
		return mapper.projectsToUpdateProjectsOutput(updatedProjects);
	}
	
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(Integer projectsId) {

		Projects existing = _projectsRepository.findById(projectsId).orElseThrow(() -> new EntityNotFoundException("Projects not found"));
	 	
        if(existing !=null) {
			_projectsRepository.delete(existing);
		}
	}
	
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public FindProjectsByIdOutput findById(Integer projectsId) {

		Projects foundProjects = _projectsRepository.findById(projectsId).orElse(null);
		if (foundProjects == null)  
			return null; 
 	   
 	    return mapper.projectsToFindProjectsByIdOutput(foundProjects);
	}

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<FindProjectsByIdOutput> find(SearchCriteria search, Pageable pageable) throws MalformedURLException  {

		Page<Projects> foundProjects = _projectsRepository.findAll(search(search), pageable);
		List<Projects> projectsList = foundProjects.getContent();
		Iterator<Projects> projectsIterator = projectsList.iterator(); 
		List<FindProjectsByIdOutput> output = new ArrayList<>();

		while (projectsIterator.hasNext()) {
		Projects projects = projectsIterator.next();
 	    output.add(mapper.projectsToFindProjectsByIdOutput(projects));
		}
		return output;
	}
	
	protected BooleanBuilder search(SearchCriteria search) throws MalformedURLException {

		QProjects projects= QProjects.projectsEntity;
		if(search != null) {
			Map<String,SearchFields> map = new HashMap<>();
			for(SearchFields fieldDetails: search.getFields())
			{
				map.put(fieldDetails.getFieldName(),fieldDetails);
			}
			List<String> keysList = new ArrayList<String>(map.keySet());
			checkProperties(keysList);
			return searchKeyValuePair(projects, map,search.getJoinColumns());
		}
		return null;
	}
	
	protected void checkProperties(List<String> list) throws MalformedURLException  {
		for (int i = 0; i < list.size(); i++) {
			if(!(
				list.get(i).replace("%20","").trim().equals("createdAt") ||
				list.get(i).replace("%20","").trim().equals("description") ||
				list.get(i).replace("%20","").trim().equals("endDate") ||
				list.get(i).replace("%20","").trim().equals("id") ||
				list.get(i).replace("%20","").trim().equals("name") ||
				list.get(i).replace("%20","").trim().equals("startDate") ||
				list.get(i).replace("%20","").trim().equals("status")
			)) 
			{
			 throw new MalformedURLException("Wrong URL Format: Property " + list.get(i) + " not found!" );
			}
		}
	}
	
	protected BooleanBuilder searchKeyValuePair(QProjects projects, Map<String,SearchFields> map,Map<String,String> joinColumns) {
		BooleanBuilder builder = new BooleanBuilder();
        
		Iterator<Map.Entry<String, SearchFields>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, SearchFields> details = iterator.next();

			if(details.getKey().replace("%20","").trim().equals("createdAt")) {
				if(details.getValue().getOperator().equals("equals") && SearchUtils.stringToLocalDateTime(details.getValue().getSearchValue()) !=null) {
					builder.and(projects.createdAt.eq(SearchUtils.stringToLocalDateTime(details.getValue().getSearchValue())));
				} else if(details.getValue().getOperator().equals("notEqual") && SearchUtils.stringToLocalDateTime(details.getValue().getSearchValue()) !=null) {
					builder.and(projects.createdAt.ne(SearchUtils.stringToLocalDateTime(details.getValue().getSearchValue())));
				} else if(details.getValue().getOperator().equals("range")) {
				   LocalDateTime startLocalDateTime= SearchUtils.stringToLocalDateTime(details.getValue().getStartingValue());
				   LocalDateTime endLocalDateTime= SearchUtils.stringToLocalDateTime(details.getValue().getEndingValue());
				   if(startLocalDateTime!=null && endLocalDateTime!=null) {
					   builder.and(projects.createdAt.between(startLocalDateTime,endLocalDateTime));
				   } else if(endLocalDateTime!=null) {
					   builder.and(projects.createdAt.loe(endLocalDateTime));
                   } else if(startLocalDateTime!=null) {
                	   builder.and(projects.createdAt.goe(startLocalDateTime));  
                   }
                }     
			}
            if(details.getKey().replace("%20","").trim().equals("description")) {
				if(details.getValue().getOperator().equals("contains")) {
					builder.and(projects.description.likeIgnoreCase("%"+ details.getValue().getSearchValue() + "%"));
				} else if(details.getValue().getOperator().equals("equals")) {
					builder.and(projects.description.eq(details.getValue().getSearchValue()));
				} else if(details.getValue().getOperator().equals("notEqual")) {
					builder.and(projects.description.ne(details.getValue().getSearchValue()));
				}
			}
			if(details.getKey().replace("%20","").trim().equals("endDate")) {
				if(details.getValue().getOperator().equals("equals") && SearchUtils.stringToLocalDate(details.getValue().getSearchValue()) !=null) {
					builder.and(projects.endDate.eq(SearchUtils.stringToLocalDate(details.getValue().getSearchValue())));
				} else if(details.getValue().getOperator().equals("notEqual") && SearchUtils.stringToLocalDate(details.getValue().getSearchValue()) !=null) {
					builder.and(projects.endDate.ne(SearchUtils.stringToLocalDate(details.getValue().getSearchValue())));
				} else if(details.getValue().getOperator().equals("range")) {
				   LocalDate startLocalDate= SearchUtils.stringToLocalDate(details.getValue().getStartingValue());
				   LocalDate endLocalDate= SearchUtils.stringToLocalDate(details.getValue().getEndingValue());
				   if(startLocalDate!=null && endLocalDate!=null) {
					   builder.and(projects.endDate.between(startLocalDate,endLocalDate));
				   } else if(endLocalDate!=null) {
					   builder.and(projects.endDate.loe(endLocalDate));
                   } else if(startLocalDate!=null) {
                	   builder.and(projects.endDate.goe(startLocalDate));  
                   }
                }     
			}
			if(details.getKey().replace("%20","").trim().equals("id")) {
				if(details.getValue().getOperator().equals("contains") && StringUtils.isNumeric(details.getValue().getSearchValue())) {
					builder.and(projects.id.like(details.getValue().getSearchValue() + "%"));
				} else if(details.getValue().getOperator().equals("equals") && StringUtils.isNumeric(details.getValue().getSearchValue())) {
					builder.and(projects.id.eq(Integer.valueOf(details.getValue().getSearchValue())));
				} else if(details.getValue().getOperator().equals("notEqual") && StringUtils.isNumeric(details.getValue().getSearchValue())) {
					builder.and(projects.id.ne(Integer.valueOf(details.getValue().getSearchValue())));
				} else if(details.getValue().getOperator().equals("range")) {
				   if(StringUtils.isNumeric(details.getValue().getStartingValue()) && StringUtils.isNumeric(details.getValue().getEndingValue())) {
                	   builder.and(projects.id.between(Integer.valueOf(details.getValue().getStartingValue()), Integer.valueOf(details.getValue().getEndingValue())));
                   } else if(StringUtils.isNumeric(details.getValue().getStartingValue())) {
                	   builder.and(projects.id.goe(Integer.valueOf(details.getValue().getStartingValue())));
                   } else if(StringUtils.isNumeric(details.getValue().getEndingValue())) {
                	   builder.and(projects.id.loe(Integer.valueOf(details.getValue().getEndingValue())));
				   }
				}
			}
            if(details.getKey().replace("%20","").trim().equals("name")) {
				if(details.getValue().getOperator().equals("contains")) {
					builder.and(projects.name.likeIgnoreCase("%"+ details.getValue().getSearchValue() + "%"));
				} else if(details.getValue().getOperator().equals("equals")) {
					builder.and(projects.name.eq(details.getValue().getSearchValue()));
				} else if(details.getValue().getOperator().equals("notEqual")) {
					builder.and(projects.name.ne(details.getValue().getSearchValue()));
				}
			}
			if(details.getKey().replace("%20","").trim().equals("startDate")) {
				if(details.getValue().getOperator().equals("equals") && SearchUtils.stringToLocalDate(details.getValue().getSearchValue()) !=null) {
					builder.and(projects.startDate.eq(SearchUtils.stringToLocalDate(details.getValue().getSearchValue())));
				} else if(details.getValue().getOperator().equals("notEqual") && SearchUtils.stringToLocalDate(details.getValue().getSearchValue()) !=null) {
					builder.and(projects.startDate.ne(SearchUtils.stringToLocalDate(details.getValue().getSearchValue())));
				} else if(details.getValue().getOperator().equals("range")) {
				   LocalDate startLocalDate= SearchUtils.stringToLocalDate(details.getValue().getStartingValue());
				   LocalDate endLocalDate= SearchUtils.stringToLocalDate(details.getValue().getEndingValue());
				   if(startLocalDate!=null && endLocalDate!=null) {
					   builder.and(projects.startDate.between(startLocalDate,endLocalDate));
				   } else if(endLocalDate!=null) {
					   builder.and(projects.startDate.loe(endLocalDate));
                   } else if(startLocalDate!=null) {
                	   builder.and(projects.startDate.goe(startLocalDate));  
                   }
                }     
			}
            if(details.getKey().replace("%20","").trim().equals("status")) {
				if(details.getValue().getOperator().equals("contains")) {
					builder.and(projects.status.likeIgnoreCase("%"+ details.getValue().getSearchValue() + "%"));
				} else if(details.getValue().getOperator().equals("equals")) {
					builder.and(projects.status.eq(details.getValue().getSearchValue()));
				} else if(details.getValue().getOperator().equals("notEqual")) {
					builder.and(projects.status.ne(details.getValue().getSearchValue()));
				}
			}
	    
		}
		
		return builder;
	}
	
	public Map<String,String> parseTasksJoinColumn(String keysString) {
		
		Map<String,String> joinColumnMap = new HashMap<String,String>();
		joinColumnMap.put("projectId", keysString);
		  
		return joinColumnMap;
	}
    
}



