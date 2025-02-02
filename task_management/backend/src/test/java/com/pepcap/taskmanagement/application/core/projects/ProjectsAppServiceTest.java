package com.pepcap.taskmanagement.application.core.projects;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.doNothing;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pepcap.taskmanagement.domain.core.projects.*;
import com.pepcap.taskmanagement.commons.search.*;
import com.pepcap.taskmanagement.application.core.projects.dto.*;
import com.pepcap.taskmanagement.domain.core.projects.QProjects;
import com.pepcap.taskmanagement.domain.core.projects.Projects;

import com.pepcap.taskmanagement.commons.logging.LoggingHelper;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import java.time.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class ProjectsAppServiceTest {

	@InjectMocks
	@Spy
	protected ProjectsAppService _appService;
	@Mock
	protected IProjectsRepository _projectsRepository;
	
	@Mock
	protected IProjectsMapper _mapper;

	@Mock
	protected Logger loggerMock;

	@Mock
	protected LoggingHelper logHelper;
	
    protected static Integer ID=15;
	 
	@Before
	public void setUp() {

		MockitoAnnotations.initMocks(_appService);
		when(logHelper.getLogger()).thenReturn(loggerMock);
		doNothing().when(loggerMock).error(anyString());
	}
	
	@Test
	public void findProjectsById_IdIsNotNullAndIdDoesNotExist_ReturnNull() {
		Optional<Projects> nullOptional = Optional.ofNullable(null);
		Mockito.when(_projectsRepository.findById(any(Integer.class))).thenReturn(nullOptional);
		Assertions.assertThat(_appService.findById(ID )).isEqualTo(null);
	}
	
	@Test
	public void findProjectsById_IdIsNotNullAndIdExists_ReturnProjects() {

		Projects projects = mock(Projects.class);
		Optional<Projects> projectsOptional = Optional.of((Projects) projects);
		Mockito.when(_projectsRepository.findById(any(Integer.class))).thenReturn(projectsOptional);
		
	    Assertions.assertThat(_appService.findById(ID )).isEqualTo(_mapper.projectsToFindProjectsByIdOutput(projects));
	}
	
	
	@Test 
    public void createProjects_ProjectsIsNotNullAndProjectsDoesNotExist_StoreProjects() { 
 
        Projects projectsEntity = mock(Projects.class); 
    	CreateProjectsInput projectsInput = new CreateProjectsInput();
		
        Mockito.when(_mapper.createProjectsInputToProjects(any(CreateProjectsInput.class))).thenReturn(projectsEntity); 
        Mockito.when(_projectsRepository.save(any(Projects.class))).thenReturn(projectsEntity);

	   	Assertions.assertThat(_appService.create(projectsInput)).isEqualTo(_mapper.projectsToCreateProjectsOutput(projectsEntity));

    } 
	@Test
	public void updateProjects_ProjectsIdIsNotNullAndIdExists_ReturnUpdatedProjects() {

		Projects projectsEntity = mock(Projects.class);
		UpdateProjectsInput projects= mock(UpdateProjectsInput.class);
		
		Optional<Projects> projectsOptional = Optional.of((Projects) projectsEntity);
		Mockito.when(_projectsRepository.findById(any(Integer.class))).thenReturn(projectsOptional);
	 		
		Mockito.when(_mapper.updateProjectsInputToProjects(any(UpdateProjectsInput.class))).thenReturn(projectsEntity);
		Mockito.when(_projectsRepository.save(any(Projects.class))).thenReturn(projectsEntity);
		Assertions.assertThat(_appService.update(ID,projects)).isEqualTo(_mapper.projectsToUpdateProjectsOutput(projectsEntity));
	}
    
	@Test
	public void deleteProjects_ProjectsIsNotNullAndProjectsExists_ProjectsRemoved() {

		Projects projects = mock(Projects.class);
		Optional<Projects> projectsOptional = Optional.of((Projects) projects);
		Mockito.when(_projectsRepository.findById(any(Integer.class))).thenReturn(projectsOptional);
 		
		_appService.delete(ID); 
		verify(_projectsRepository).delete(projects);
	}
	
	@Test
	public void find_ListIsEmpty_ReturnList() throws Exception {

		List<Projects> list = new ArrayList<>();
		Page<Projects> foundPage = new PageImpl(list);
		Pageable pageable = mock(Pageable.class);
		List<FindProjectsByIdOutput> output = new ArrayList<>();
		SearchCriteria search= new SearchCriteria();

		Mockito.when(_appService.search(any(SearchCriteria.class))).thenReturn(new BooleanBuilder());
		Mockito.when(_projectsRepository.findAll(any(Predicate.class),any(Pageable.class))).thenReturn(foundPage);
		Assertions.assertThat(_appService.find(search, pageable)).isEqualTo(output);
	}
	
	@Test
	public void find_ListIsNotEmpty_ReturnList() throws Exception {

		List<Projects> list = new ArrayList<>();
		Projects projects = mock(Projects.class);
		list.add(projects);
    	Page<Projects> foundPage = new PageImpl(list);
		Pageable pageable = mock(Pageable.class);
		List<FindProjectsByIdOutput> output = new ArrayList<>();
        SearchCriteria search= new SearchCriteria();

		output.add(_mapper.projectsToFindProjectsByIdOutput(projects));
		
		Mockito.when(_appService.search(any(SearchCriteria.class))).thenReturn(new BooleanBuilder());
    	Mockito.when(_projectsRepository.findAll(any(Predicate.class),any(Pageable.class))).thenReturn(foundPage);
		Assertions.assertThat(_appService.find(search, pageable)).isEqualTo(output);
	}
	
	@Test
	public void searchKeyValuePair_PropertyExists_ReturnBooleanBuilder() {
		QProjects projects = QProjects.projectsEntity;
	    SearchFields searchFields = new SearchFields();
		searchFields.setOperator("equals");
		searchFields.setSearchValue("xyz");
	    Map<String,SearchFields> map = new HashMap<>();
        map.put("description",searchFields);
		Map<String,String> searchMap = new HashMap<>();
        searchMap.put("xyz",String.valueOf(ID));
		BooleanBuilder builder = new BooleanBuilder();
        builder.and(projects.description.eq("xyz"));
		Assertions.assertThat(_appService.searchKeyValuePair(projects,map,searchMap)).isEqualTo(builder);
	}
	
	@Test (expected = Exception.class)
	public void checkProperties_PropertyDoesNotExist_ThrowException() throws Exception {
		List<String> list = new ArrayList<>();
		list.add("xyz");
		_appService.checkProperties(list);
	}
	
	@Test
	public void checkProperties_PropertyExists_ReturnNothing() throws Exception {
		List<String> list = new ArrayList<>();
        list.add("description");
        list.add("name");
        list.add("status");
		_appService.checkProperties(list);
	}
	
	@Test
	public void search_SearchIsNotNullAndSearchContainsCaseThree_ReturnBooleanBuilder() throws Exception {
	
		Map<String,SearchFields> map = new HashMap<>();
		QProjects projects = QProjects.projectsEntity;
		List<SearchFields> fieldsList= new ArrayList<>();
		SearchFields fields=new SearchFields();
		SearchCriteria search= new SearchCriteria();
		search.setType(3);
		search.setValue("xyz");
		search.setOperator("equals");
        fields.setFieldName("description");
        fields.setOperator("equals");
		fields.setSearchValue("xyz");
        fieldsList.add(fields);
        search.setFields(fieldsList);
		BooleanBuilder builder = new BooleanBuilder();
        builder.or(projects.description.eq("xyz"));
        Mockito.doNothing().when(_appService).checkProperties(any(List.class));
		Mockito.doReturn(builder).when(_appService).searchKeyValuePair(any(QProjects.class), any(HashMap.class), any(HashMap.class));
        
		Assertions.assertThat(_appService.search(search)).isEqualTo(builder);
	}
	
	@Test
	public void search_StringIsNull_ReturnNull() throws Exception {

		Assertions.assertThat(_appService.search(null)).isEqualTo(null);
	}

	@Test
	public void ParsetasksJoinColumn_KeysStringIsNotEmptyAndKeyValuePairDoesNotExist_ReturnNull()
	{
	    Map<String,String> joinColumnMap = new HashMap<String,String>();
		String keyString= "15";
		joinColumnMap.put("projectId", keyString);
		Assertions.assertThat(_appService.parseTasksJoinColumn(keyString)).isEqualTo(joinColumnMap);
	}
}
