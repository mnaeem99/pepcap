package com.pepcap.taskmanagement.application.core.tasks;

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

import com.pepcap.taskmanagement.domain.core.tasks.*;
import com.pepcap.taskmanagement.commons.search.*;
import com.pepcap.taskmanagement.application.core.tasks.dto.*;
import com.pepcap.taskmanagement.domain.core.tasks.QTasks;
import com.pepcap.taskmanagement.domain.core.tasks.Tasks;

import com.pepcap.taskmanagement.domain.core.projects.Projects;
import com.pepcap.taskmanagement.domain.core.projects.IProjectsRepository;
import com.pepcap.taskmanagement.domain.core.authorization.users.Users;
import com.pepcap.taskmanagement.domain.core.authorization.users.IUsersRepository;
import com.pepcap.taskmanagement.commons.logging.LoggingHelper;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import java.time.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class TasksAppServiceTest {

	@InjectMocks
	@Spy
	protected TasksAppService _appService;
	@Mock
	protected ITasksRepository _tasksRepository;
	
    @Mock
	protected IProjectsRepository _projectsRepository;

    @Mock
	protected IUsersRepository _usersRepository;

	@Mock
	protected ITasksMapper _mapper;

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
	public void findTasksById_IdIsNotNullAndIdDoesNotExist_ReturnNull() {
		Optional<Tasks> nullOptional = Optional.ofNullable(null);
		Mockito.when(_tasksRepository.findById(any(Integer.class))).thenReturn(nullOptional);
		Assertions.assertThat(_appService.findById(ID )).isEqualTo(null);
	}
	
	@Test
	public void findTasksById_IdIsNotNullAndIdExists_ReturnTasks() {

		Tasks tasks = mock(Tasks.class);
		Optional<Tasks> tasksOptional = Optional.of((Tasks) tasks);
		Mockito.when(_tasksRepository.findById(any(Integer.class))).thenReturn(tasksOptional);
		
	    Assertions.assertThat(_appService.findById(ID )).isEqualTo(_mapper.tasksToFindTasksByIdOutput(tasks));
	}
	
	
	@Test 
    public void createTasks_TasksIsNotNullAndTasksDoesNotExist_StoreTasks() { 
 
        Tasks tasksEntity = mock(Tasks.class); 
    	CreateTasksInput tasksInput = new CreateTasksInput();
		
        Projects projects = mock(Projects.class);
		Optional<Projects> projectsOptional = Optional.of((Projects) projects);
        tasksInput.setProjectId(15);
		
        Mockito.when(_projectsRepository.findById(any(Integer.class))).thenReturn(projectsOptional);
        
		
        Users users = mock(Users.class);
		Optional<Users> usersOptional = Optional.of((Users) users);
        tasksInput.setAssigneeId(15);
		
        Mockito.when(_usersRepository.findById(any(Integer.class))).thenReturn(usersOptional);
        
		
        Mockito.when(_mapper.createTasksInputToTasks(any(CreateTasksInput.class))).thenReturn(tasksEntity); 
        Mockito.when(_tasksRepository.save(any(Tasks.class))).thenReturn(tasksEntity);

	   	Assertions.assertThat(_appService.create(tasksInput)).isEqualTo(_mapper.tasksToCreateTasksOutput(tasksEntity));

    } 
    @Test
	public void createTasks_TasksIsNotNullAndTasksDoesNotExistAndChildIsNullAndChildIsNotMandatory_StoreTasks() {

		Tasks tasks = mock(Tasks.class);
		CreateTasksInput tasksInput = mock(CreateTasksInput.class);
		
		
		Mockito.when(_mapper.createTasksInputToTasks(any(CreateTasksInput.class))).thenReturn(tasks);
		Mockito.when(_tasksRepository.save(any(Tasks.class))).thenReturn(tasks);
	    Assertions.assertThat(_appService.create(tasksInput)).isEqualTo(_mapper.tasksToCreateTasksOutput(tasks)); 
	}
	
    @Test
	public void updateTasks_TasksIsNotNullAndTasksDoesNotExistAndChildIsNullAndChildIsNotMandatory_ReturnUpdatedTasks() {

		Tasks tasks = mock(Tasks.class);
		UpdateTasksInput tasksInput = mock(UpdateTasksInput.class);
		
		Optional<Tasks> tasksOptional = Optional.of((Tasks) tasks);
		Mockito.when(_tasksRepository.findById(any(Integer.class))).thenReturn(tasksOptional);
		
		Mockito.when(_mapper.updateTasksInputToTasks(any(UpdateTasksInput.class))).thenReturn(tasks);
		Mockito.when(_tasksRepository.save(any(Tasks.class))).thenReturn(tasks);
		Assertions.assertThat(_appService.update(ID,tasksInput)).isEqualTo(_mapper.tasksToUpdateTasksOutput(tasks));
	}
	
		
	@Test
	public void updateTasks_TasksIdIsNotNullAndIdExists_ReturnUpdatedTasks() {

		Tasks tasksEntity = mock(Tasks.class);
		UpdateTasksInput tasks= mock(UpdateTasksInput.class);
		
		Optional<Tasks> tasksOptional = Optional.of((Tasks) tasksEntity);
		Mockito.when(_tasksRepository.findById(any(Integer.class))).thenReturn(tasksOptional);
	 		
		Mockito.when(_mapper.updateTasksInputToTasks(any(UpdateTasksInput.class))).thenReturn(tasksEntity);
		Mockito.when(_tasksRepository.save(any(Tasks.class))).thenReturn(tasksEntity);
		Assertions.assertThat(_appService.update(ID,tasks)).isEqualTo(_mapper.tasksToUpdateTasksOutput(tasksEntity));
	}
    
	@Test
	public void deleteTasks_TasksIsNotNullAndTasksExists_TasksRemoved() {

		Tasks tasks = mock(Tasks.class);
		Optional<Tasks> tasksOptional = Optional.of((Tasks) tasks);
		Mockito.when(_tasksRepository.findById(any(Integer.class))).thenReturn(tasksOptional);
 		
		_appService.delete(ID); 
		verify(_tasksRepository).delete(tasks);
	}
	
	@Test
	public void find_ListIsEmpty_ReturnList() throws Exception {

		List<Tasks> list = new ArrayList<>();
		Page<Tasks> foundPage = new PageImpl(list);
		Pageable pageable = mock(Pageable.class);
		List<FindTasksByIdOutput> output = new ArrayList<>();
		SearchCriteria search= new SearchCriteria();

		Mockito.when(_appService.search(any(SearchCriteria.class))).thenReturn(new BooleanBuilder());
		Mockito.when(_tasksRepository.findAll(any(Predicate.class),any(Pageable.class))).thenReturn(foundPage);
		Assertions.assertThat(_appService.find(search, pageable)).isEqualTo(output);
	}
	
	@Test
	public void find_ListIsNotEmpty_ReturnList() throws Exception {

		List<Tasks> list = new ArrayList<>();
		Tasks tasks = mock(Tasks.class);
		list.add(tasks);
    	Page<Tasks> foundPage = new PageImpl(list);
		Pageable pageable = mock(Pageable.class);
		List<FindTasksByIdOutput> output = new ArrayList<>();
        SearchCriteria search= new SearchCriteria();

		output.add(_mapper.tasksToFindTasksByIdOutput(tasks));
		
		Mockito.when(_appService.search(any(SearchCriteria.class))).thenReturn(new BooleanBuilder());
    	Mockito.when(_tasksRepository.findAll(any(Predicate.class),any(Pageable.class))).thenReturn(foundPage);
		Assertions.assertThat(_appService.find(search, pageable)).isEqualTo(output);
	}
	
	@Test
	public void searchKeyValuePair_PropertyExists_ReturnBooleanBuilder() {
		QTasks tasks = QTasks.tasksEntity;
	    SearchFields searchFields = new SearchFields();
		searchFields.setOperator("equals");
		searchFields.setSearchValue("xyz");
	    Map<String,SearchFields> map = new HashMap<>();
        map.put("description",searchFields);
		Map<String,String> searchMap = new HashMap<>();
        searchMap.put("xyz",String.valueOf(ID));
		BooleanBuilder builder = new BooleanBuilder();
        builder.and(tasks.description.eq("xyz"));
		Assertions.assertThat(_appService.searchKeyValuePair(tasks,map,searchMap)).isEqualTo(builder);
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
		QTasks tasks = QTasks.tasksEntity;
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
        builder.or(tasks.description.eq("xyz"));
        Mockito.doNothing().when(_appService).checkProperties(any(List.class));
		Mockito.doReturn(builder).when(_appService).searchKeyValuePair(any(QTasks.class), any(HashMap.class), any(HashMap.class));
        
		Assertions.assertThat(_appService.search(search)).isEqualTo(builder);
	}
	
	@Test
	public void search_StringIsNull_ReturnNull() throws Exception {

		Assertions.assertThat(_appService.search(null)).isEqualTo(null);
	}
   
    //Projects
	@Test
	public void GetProjects_IfTasksIdAndProjectsIdIsNotNullAndTasksExists_ReturnProjects() {
		Tasks tasks = mock(Tasks.class);
		Optional<Tasks> tasksOptional = Optional.of((Tasks) tasks);
		Projects projectsEntity = mock(Projects.class);

		Mockito.when(_tasksRepository.findById(any(Integer.class))).thenReturn(tasksOptional);

		Mockito.when(tasks.getProjects()).thenReturn(projectsEntity);
		Assertions.assertThat(_appService.getProjects(ID)).isEqualTo(_mapper.projectsToGetProjectsOutput(projectsEntity, tasks));
	}

	@Test 
	public void GetProjects_IfTasksIdAndProjectsIdIsNotNullAndTasksDoesNotExist_ReturnNull() {
		Optional<Tasks> nullOptional = Optional.ofNullable(null);;
		Mockito.when(_tasksRepository.findById(any(Integer.class))).thenReturn(nullOptional);
		Assertions.assertThat(_appService.getProjects(ID)).isEqualTo(null);
	}
   
    //Users
	@Test
	public void GetUsers_IfTasksIdAndUsersIdIsNotNullAndTasksExists_ReturnUsers() {
		Tasks tasks = mock(Tasks.class);
		Optional<Tasks> tasksOptional = Optional.of((Tasks) tasks);
		Users usersEntity = mock(Users.class);

		Mockito.when(_tasksRepository.findById(any(Integer.class))).thenReturn(tasksOptional);

		Mockito.when(tasks.getUsers()).thenReturn(usersEntity);
		Assertions.assertThat(_appService.getUsers(ID)).isEqualTo(_mapper.usersToGetUsersOutput(usersEntity, tasks));
	}

	@Test 
	public void GetUsers_IfTasksIdAndUsersIdIsNotNullAndTasksDoesNotExist_ReturnNull() {
		Optional<Tasks> nullOptional = Optional.ofNullable(null);;
		Mockito.when(_tasksRepository.findById(any(Integer.class))).thenReturn(nullOptional);
		Assertions.assertThat(_appService.getUsers(ID)).isEqualTo(null);
	}

	@Test
	public void ParsetimesheetsJoinColumn_KeysStringIsNotEmptyAndKeyValuePairDoesNotExist_ReturnNull()
	{
	    Map<String,String> joinColumnMap = new HashMap<String,String>();
		String keyString= "15";
		joinColumnMap.put("taskId", keyString);
		Assertions.assertThat(_appService.parseTimesheetsJoinColumn(keyString)).isEqualTo(joinColumnMap);
	}
}
