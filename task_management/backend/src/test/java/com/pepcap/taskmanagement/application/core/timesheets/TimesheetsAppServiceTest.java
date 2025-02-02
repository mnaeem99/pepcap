package com.pepcap.taskmanagement.application.core.timesheets;

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

import com.pepcap.taskmanagement.domain.core.timesheets.*;
import com.pepcap.taskmanagement.commons.search.*;
import com.pepcap.taskmanagement.application.core.timesheets.dto.*;
import com.pepcap.taskmanagement.domain.core.timesheets.QTimesheets;
import com.pepcap.taskmanagement.domain.core.timesheets.Timesheets;

import com.pepcap.taskmanagement.domain.core.tasks.Tasks;
import com.pepcap.taskmanagement.domain.core.tasks.ITasksRepository;
import com.pepcap.taskmanagement.domain.core.authorization.users.Users;
import com.pepcap.taskmanagement.domain.core.authorization.users.IUsersRepository;
import com.pepcap.taskmanagement.commons.logging.LoggingHelper;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import java.time.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class TimesheetsAppServiceTest {

	@InjectMocks
	@Spy
	protected TimesheetsAppService _appService;
	@Mock
	protected ITimesheetsRepository _timesheetsRepository;
	
    @Mock
	protected ITasksRepository _tasksRepository;

    @Mock
	protected IUsersRepository _usersRepository;

	@Mock
	protected ITimesheetsMapper _mapper;

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
	public void findTimesheetsById_IdIsNotNullAndIdDoesNotExist_ReturnNull() {
		Optional<Timesheets> nullOptional = Optional.ofNullable(null);
		Mockito.when(_timesheetsRepository.findById(any(Integer.class))).thenReturn(nullOptional);
		Assertions.assertThat(_appService.findById(ID )).isEqualTo(null);
	}
	
	@Test
	public void findTimesheetsById_IdIsNotNullAndIdExists_ReturnTimesheets() {

		Timesheets timesheets = mock(Timesheets.class);
		Optional<Timesheets> timesheetsOptional = Optional.of((Timesheets) timesheets);
		Mockito.when(_timesheetsRepository.findById(any(Integer.class))).thenReturn(timesheetsOptional);
		
	    Assertions.assertThat(_appService.findById(ID )).isEqualTo(_mapper.timesheetsToFindTimesheetsByIdOutput(timesheets));
	}
	
	
	@Test 
    public void createTimesheets_TimesheetsIsNotNullAndTimesheetsDoesNotExist_StoreTimesheets() { 
 
        Timesheets timesheetsEntity = mock(Timesheets.class); 
    	CreateTimesheetsInput timesheetsInput = new CreateTimesheetsInput();
		
        Tasks tasks = mock(Tasks.class);
		Optional<Tasks> tasksOptional = Optional.of((Tasks) tasks);
        timesheetsInput.setTaskId(15);
		
        Mockito.when(_tasksRepository.findById(any(Integer.class))).thenReturn(tasksOptional);
        
		
        Users users = mock(Users.class);
		Optional<Users> usersOptional = Optional.of((Users) users);
        timesheetsInput.setUserId(15);
		
        Mockito.when(_usersRepository.findById(any(Integer.class))).thenReturn(usersOptional);
        
		
        Mockito.when(_mapper.createTimesheetsInputToTimesheets(any(CreateTimesheetsInput.class))).thenReturn(timesheetsEntity); 
        Mockito.when(_timesheetsRepository.save(any(Timesheets.class))).thenReturn(timesheetsEntity);

	   	Assertions.assertThat(_appService.create(timesheetsInput)).isEqualTo(_mapper.timesheetsToCreateTimesheetsOutput(timesheetsEntity));

    } 
    @Test
	public void createTimesheets_TimesheetsIsNotNullAndTimesheetsDoesNotExistAndChildIsNullAndChildIsNotMandatory_StoreTimesheets() {

		Timesheets timesheets = mock(Timesheets.class);
		CreateTimesheetsInput timesheetsInput = mock(CreateTimesheetsInput.class);
		
		
		Mockito.when(_mapper.createTimesheetsInputToTimesheets(any(CreateTimesheetsInput.class))).thenReturn(timesheets);
		Mockito.when(_timesheetsRepository.save(any(Timesheets.class))).thenReturn(timesheets);
	    Assertions.assertThat(_appService.create(timesheetsInput)).isEqualTo(_mapper.timesheetsToCreateTimesheetsOutput(timesheets)); 
	}
	
    @Test
	public void updateTimesheets_TimesheetsIsNotNullAndTimesheetsDoesNotExistAndChildIsNullAndChildIsNotMandatory_ReturnUpdatedTimesheets() {

		Timesheets timesheets = mock(Timesheets.class);
		UpdateTimesheetsInput timesheetsInput = mock(UpdateTimesheetsInput.class);
		
		Optional<Timesheets> timesheetsOptional = Optional.of((Timesheets) timesheets);
		Mockito.when(_timesheetsRepository.findById(any(Integer.class))).thenReturn(timesheetsOptional);
		
		Mockito.when(_mapper.updateTimesheetsInputToTimesheets(any(UpdateTimesheetsInput.class))).thenReturn(timesheets);
		Mockito.when(_timesheetsRepository.save(any(Timesheets.class))).thenReturn(timesheets);
		Assertions.assertThat(_appService.update(ID,timesheetsInput)).isEqualTo(_mapper.timesheetsToUpdateTimesheetsOutput(timesheets));
	}
	
		
	@Test
	public void updateTimesheets_TimesheetsIdIsNotNullAndIdExists_ReturnUpdatedTimesheets() {

		Timesheets timesheetsEntity = mock(Timesheets.class);
		UpdateTimesheetsInput timesheets= mock(UpdateTimesheetsInput.class);
		
		Optional<Timesheets> timesheetsOptional = Optional.of((Timesheets) timesheetsEntity);
		Mockito.when(_timesheetsRepository.findById(any(Integer.class))).thenReturn(timesheetsOptional);
	 		
		Mockito.when(_mapper.updateTimesheetsInputToTimesheets(any(UpdateTimesheetsInput.class))).thenReturn(timesheetsEntity);
		Mockito.when(_timesheetsRepository.save(any(Timesheets.class))).thenReturn(timesheetsEntity);
		Assertions.assertThat(_appService.update(ID,timesheets)).isEqualTo(_mapper.timesheetsToUpdateTimesheetsOutput(timesheetsEntity));
	}
    
	@Test
	public void deleteTimesheets_TimesheetsIsNotNullAndTimesheetsExists_TimesheetsRemoved() {

		Timesheets timesheets = mock(Timesheets.class);
		Optional<Timesheets> timesheetsOptional = Optional.of((Timesheets) timesheets);
		Mockito.when(_timesheetsRepository.findById(any(Integer.class))).thenReturn(timesheetsOptional);
 		
		_appService.delete(ID); 
		verify(_timesheetsRepository).delete(timesheets);
	}
	
	@Test
	public void find_ListIsEmpty_ReturnList() throws Exception {

		List<Timesheets> list = new ArrayList<>();
		Page<Timesheets> foundPage = new PageImpl(list);
		Pageable pageable = mock(Pageable.class);
		List<FindTimesheetsByIdOutput> output = new ArrayList<>();
		SearchCriteria search= new SearchCriteria();

		Mockito.when(_appService.search(any(SearchCriteria.class))).thenReturn(new BooleanBuilder());
		Mockito.when(_timesheetsRepository.findAll(any(Predicate.class),any(Pageable.class))).thenReturn(foundPage);
		Assertions.assertThat(_appService.find(search, pageable)).isEqualTo(output);
	}
	
	@Test
	public void find_ListIsNotEmpty_ReturnList() throws Exception {

		List<Timesheets> list = new ArrayList<>();
		Timesheets timesheets = mock(Timesheets.class);
		list.add(timesheets);
    	Page<Timesheets> foundPage = new PageImpl(list);
		Pageable pageable = mock(Pageable.class);
		List<FindTimesheetsByIdOutput> output = new ArrayList<>();
        SearchCriteria search= new SearchCriteria();

		output.add(_mapper.timesheetsToFindTimesheetsByIdOutput(timesheets));
		
		Mockito.when(_appService.search(any(SearchCriteria.class))).thenReturn(new BooleanBuilder());
    	Mockito.when(_timesheetsRepository.findAll(any(Predicate.class),any(Pageable.class))).thenReturn(foundPage);
		Assertions.assertThat(_appService.find(search, pageable)).isEqualTo(output);
	}
	
	@Test
	public void searchKeyValuePair_PropertyExists_ReturnBooleanBuilder() {
		QTimesheets timesheets = QTimesheets.timesheetsEntity;
	    SearchFields searchFields = new SearchFields();
		searchFields.setOperator("equals");
		searchFields.setSearchValue("xyz");
	    Map<String,SearchFields> map = new HashMap<>();
		Map<String,String> searchMap = new HashMap<>();
        searchMap.put("xyz",String.valueOf(ID));
		BooleanBuilder builder = new BooleanBuilder();
		Assertions.assertThat(_appService.searchKeyValuePair(timesheets,map,searchMap)).isEqualTo(builder);
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
		_appService.checkProperties(list);
	}
	
	@Test
	public void search_SearchIsNotNullAndSearchContainsCaseThree_ReturnBooleanBuilder() throws Exception {
	
		Map<String,SearchFields> map = new HashMap<>();
		QTimesheets timesheets = QTimesheets.timesheetsEntity;
		List<SearchFields> fieldsList= new ArrayList<>();
		SearchFields fields=new SearchFields();
		SearchCriteria search= new SearchCriteria();
		search.setType(3);
		search.setValue("xyz");
		search.setOperator("equals");
        fields.setOperator("equals");
		fields.setSearchValue("xyz");
        fieldsList.add(fields);
        search.setFields(fieldsList);
		BooleanBuilder builder = new BooleanBuilder();
        Mockito.doNothing().when(_appService).checkProperties(any(List.class));
		Mockito.doReturn(builder).when(_appService).searchKeyValuePair(any(QTimesheets.class), any(HashMap.class), any(HashMap.class));
        
		Assertions.assertThat(_appService.search(search)).isEqualTo(builder);
	}
	
	@Test
	public void search_StringIsNull_ReturnNull() throws Exception {

		Assertions.assertThat(_appService.search(null)).isEqualTo(null);
	}
   
    //Tasks
	@Test
	public void GetTasks_IfTimesheetsIdAndTasksIdIsNotNullAndTimesheetsExists_ReturnTasks() {
		Timesheets timesheets = mock(Timesheets.class);
		Optional<Timesheets> timesheetsOptional = Optional.of((Timesheets) timesheets);
		Tasks tasksEntity = mock(Tasks.class);

		Mockito.when(_timesheetsRepository.findById(any(Integer.class))).thenReturn(timesheetsOptional);

		Mockito.when(timesheets.getTasks()).thenReturn(tasksEntity);
		Assertions.assertThat(_appService.getTasks(ID)).isEqualTo(_mapper.tasksToGetTasksOutput(tasksEntity, timesheets));
	}

	@Test 
	public void GetTasks_IfTimesheetsIdAndTasksIdIsNotNullAndTimesheetsDoesNotExist_ReturnNull() {
		Optional<Timesheets> nullOptional = Optional.ofNullable(null);;
		Mockito.when(_timesheetsRepository.findById(any(Integer.class))).thenReturn(nullOptional);
		Assertions.assertThat(_appService.getTasks(ID)).isEqualTo(null);
	}
   
    //Users
	@Test
	public void GetUsers_IfTimesheetsIdAndUsersIdIsNotNullAndTimesheetsExists_ReturnUsers() {
		Timesheets timesheets = mock(Timesheets.class);
		Optional<Timesheets> timesheetsOptional = Optional.of((Timesheets) timesheets);
		Users usersEntity = mock(Users.class);

		Mockito.when(_timesheetsRepository.findById(any(Integer.class))).thenReturn(timesheetsOptional);

		Mockito.when(timesheets.getUsers()).thenReturn(usersEntity);
		Assertions.assertThat(_appService.getUsers(ID)).isEqualTo(_mapper.usersToGetUsersOutput(usersEntity, timesheets));
	}

	@Test 
	public void GetUsers_IfTimesheetsIdAndUsersIdIsNotNullAndTimesheetsDoesNotExist_ReturnNull() {
		Optional<Timesheets> nullOptional = Optional.ofNullable(null);;
		Mockito.when(_timesheetsRepository.findById(any(Integer.class))).thenReturn(nullOptional);
		Assertions.assertThat(_appService.getUsers(ID)).isEqualTo(null);
	}

}
