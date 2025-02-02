package com.pepcap.taskmanagement.restcontrollers.core;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.*;
import java.time.*;
import java.math.BigDecimal;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import org.springframework.core.env.Environment;
import org.springframework.data.web.SortHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.pepcap.taskmanagement.commons.logging.LoggingHelper;
import com.pepcap.taskmanagement.commons.search.SearchUtils;
import com.pepcap.taskmanagement.application.core.timesheets.TimesheetsAppService;
import com.pepcap.taskmanagement.application.core.timesheets.dto.*;
import com.pepcap.taskmanagement.domain.core.timesheets.ITimesheetsRepository;
import com.pepcap.taskmanagement.domain.core.timesheets.Timesheets;

import com.pepcap.taskmanagement.domain.core.tasks.ITasksRepository;
import com.pepcap.taskmanagement.domain.core.tasks.Tasks;
import com.pepcap.taskmanagement.domain.core.authorization.users.IUsersRepository;
import com.pepcap.taskmanagement.domain.core.authorization.users.Users;
import com.pepcap.taskmanagement.domain.core.projects.IProjectsRepository;
import com.pepcap.taskmanagement.domain.core.projects.Projects;
import com.pepcap.taskmanagement.application.core.tasks.TasksAppService;    
import com.pepcap.taskmanagement.application.core.authorization.users.UsersAppService;    
import com.pepcap.taskmanagement.DatabaseContainerConfig;
import com.pepcap.taskmanagement.domain.core.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(properties = "spring.profiles.active=test")
public class TimesheetsControllerTest extends DatabaseContainerConfig{
	
	@Autowired
	protected SortHandlerMethodArgumentResolver sortArgumentResolver;

	@Autowired
	@Qualifier("timesheetsRepository") 
	protected ITimesheetsRepository timesheets_repository;
	
	@Autowired
	@Qualifier("tasksRepository") 
	protected ITasksRepository tasksRepository;
	
	@Autowired
	@Qualifier("usersRepository") 
	protected IUsersRepository usersRepository;
	
	@Autowired
	@Qualifier("projectsRepository") 
	protected IProjectsRepository projectsRepository;
	

	@SpyBean
	@Qualifier("timesheetsAppService")
	protected TimesheetsAppService timesheetsAppService;
	
    @SpyBean
    @Qualifier("tasksAppService")
	protected TasksAppService  tasksAppService;
	
    @SpyBean
    @Qualifier("usersAppService")
	protected UsersAppService  usersAppService;
	
	@SpyBean
	protected LoggingHelper logHelper;

	@SpyBean
	protected Environment env;

	@Mock
	protected Logger loggerMock;

	protected Timesheets timesheets;

	protected MockMvc mvc;
	
	@Autowired
	EntityManagerFactory emf;
	
    static EntityManagerFactory emfs;
    
    static int relationCount = 10;
    static int yearCount = 1971;
    static int dayCount = 10;
	private BigDecimal bigdec = new BigDecimal(1.2);
    
	int countTasks = 10;
	
	int countProjects = 10;
	
	int countUsers = 10;
	
	@PostConstruct
	public void init() {
	emfs = emf;
	}

	@AfterClass
	public static void cleanup() {
		EntityManager em = emfs.createEntityManager();
		em.getTransaction().begin();
		em.createNativeQuery("truncate table task_management.timesheets CASCADE").executeUpdate();
		em.createNativeQuery("truncate table task_management.tasks CASCADE").executeUpdate();
		em.createNativeQuery("truncate table task_management.users CASCADE").executeUpdate();
		em.createNativeQuery("truncate table task_management.projects CASCADE").executeUpdate();
		em.getTransaction().commit();
	}
	
	public Tasks createTasksEntity() {
	
		if(countTasks>60) {
			countTasks = 10;
		}

		if(dayCount>=31) {
			dayCount = 10;
			yearCount++;
		}
		
		Tasks tasksEntity = new Tasks();
		
		tasksEntity.setCreatedAt(SearchUtils.stringToLocalDateTime(yearCount+"-09-"+dayCount+" 05:25:22"));
  		tasksEntity.setDescription(String.valueOf(relationCount));
		tasksEntity.setDueDate(SearchUtils.stringToLocalDate(yearCount+"-09-"+dayCount));
		tasksEntity.setId(relationCount);
  		tasksEntity.setName(String.valueOf(relationCount));
  		tasksEntity.setStatus(String.valueOf(relationCount));
		tasksEntity.setVersiono(0L);
		relationCount++;
		Projects projects= createProjectsEntity();
		tasksEntity.setProjects(projects);
		Users users= createUsersEntity();
		tasksEntity.setUsers(users);
		if(!tasksRepository.findAll().contains(tasksEntity))
		{
			 tasksEntity = tasksRepository.save(tasksEntity);
		}
		countTasks++;
	    return tasksEntity;
	}
	public Projects createProjectsEntity() {
	
		if(countProjects>60) {
			countProjects = 10;
		}

		if(dayCount>=31) {
			dayCount = 10;
			yearCount++;
		}
		
		Projects projectsEntity = new Projects();
		
		projectsEntity.setCreatedAt(SearchUtils.stringToLocalDateTime(yearCount+"-09-"+dayCount+" 05:25:22"));
  		projectsEntity.setDescription(String.valueOf(relationCount));
		projectsEntity.setEndDate(SearchUtils.stringToLocalDate(yearCount+"-09-"+dayCount));
		projectsEntity.setId(relationCount);
  		projectsEntity.setName(String.valueOf(relationCount));
		projectsEntity.setStartDate(SearchUtils.stringToLocalDate(yearCount+"-09-"+dayCount));
  		projectsEntity.setStatus(String.valueOf(relationCount));
		projectsEntity.setVersiono(0L);
		relationCount++;
		if(!projectsRepository.findAll().contains(projectsEntity))
		{
			 projectsEntity = projectsRepository.save(projectsEntity);
		}
		countProjects++;
	    return projectsEntity;
	}
	public Users createUsersEntity() {
	
		if(countUsers>60) {
			countUsers = 10;
		}

		if(dayCount>=31) {
			dayCount = 10;
			yearCount++;
		}
		
		Users usersEntity = new Users();
		
		usersEntity.setCreatedAt(SearchUtils.stringToLocalDateTime(yearCount+"-09-"+dayCount+" 05:25:22"));
  		usersEntity.setEmail(String.valueOf(relationCount));
  		usersEntity.setFirstName(String.valueOf(relationCount));
		usersEntity.setId(relationCount);
		usersEntity.setIsActive(false);
		usersEntity.setIsEmailConfirmed(false);
  		usersEntity.setLastName(String.valueOf(relationCount));
  		usersEntity.setPassword(String.valueOf(relationCount));
  		usersEntity.setPhoneNumber(String.valueOf(relationCount));
  		usersEntity.setRole(String.valueOf(relationCount));
		usersEntity.setUpdatedAt(SearchUtils.stringToLocalDateTime(yearCount+"-09-"+dayCount+" 05:25:22"));
  		usersEntity.setUsername(String.valueOf(relationCount));
		usersEntity.setVersiono(0L);
		relationCount++;
		if(!usersRepository.findAll().contains(usersEntity))
		{
			 usersEntity = usersRepository.save(usersEntity);
		}
		countUsers++;
	    return usersEntity;
	}

	public Timesheets createEntity() {
		Tasks tasks = createTasksEntity();
		Users users = createUsersEntity();
	
		Timesheets timesheetsEntity = new Timesheets();
    	timesheetsEntity.setCreatedAt(SearchUtils.stringToLocalDateTime("1996-09-01 09:15:22"));
    	timesheetsEntity.setDate(SearchUtils.stringToLocalDate("1996-09-01"));
    	timesheetsEntity.setHoursWorked(new BigDecimal("1.1"));
		timesheetsEntity.setId(1);
		timesheetsEntity.setVersiono(0L);
		timesheetsEntity.setTasks(tasks);
		timesheetsEntity.setUsers(users);
		
		return timesheetsEntity;
	}
    public CreateTimesheetsInput createTimesheetsInput() {
	
	    CreateTimesheetsInput timesheetsInput = new CreateTimesheetsInput();
    	timesheetsInput.setCreatedAt(SearchUtils.stringToLocalDateTime("1996-08-10 05:25:22"));
    	timesheetsInput.setDate(SearchUtils.stringToLocalDate("1996-08-10"));
    	timesheetsInput.setHoursWorked(new BigDecimal("5.8"));
		
		return timesheetsInput;
	}

	public Timesheets createNewEntity() {
		Timesheets timesheets = new Timesheets();
    	timesheets.setCreatedAt(SearchUtils.stringToLocalDateTime("1996-08-11 05:35:22"));
    	timesheets.setDate(SearchUtils.stringToLocalDate("1996-08-11"));
    	timesheets.setHoursWorked(new BigDecimal("3.3"));
		timesheets.setId(3);
		
		return timesheets;
	}
	
	public Timesheets createUpdateEntity() {
		Timesheets timesheets = new Timesheets();
    	timesheets.setCreatedAt(SearchUtils.stringToLocalDateTime("1996-09-09 05:45:22"));
    	timesheets.setDate(SearchUtils.stringToLocalDate("1996-09-09"));
    	timesheets.setHoursWorked(new BigDecimal("3.3"));
		timesheets.setId(4);
		
		return timesheets;
	}
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
    
		final TimesheetsController timesheetsController = new TimesheetsController(timesheetsAppService, tasksAppService, usersAppService,
		logHelper,env);
		when(logHelper.getLogger()).thenReturn(loggerMock);
		doNothing().when(loggerMock).error(anyString());

		this.mvc = MockMvcBuilders.standaloneSetup(timesheetsController)
				.setCustomArgumentResolvers(sortArgumentResolver)
				.setControllerAdvice()
				.build();
	}

	@Before
	public void initTest() {

		timesheets= createEntity();
		List<Timesheets> list= timesheets_repository.findAll();
		if(!list.contains(timesheets)) {
			timesheets=timesheets_repository.save(timesheets);
		}

	}
	
	@Test
	public void FindById_IdIsValid_ReturnStatusOk() throws Exception {
	
		mvc.perform(get("/timesheets/" + timesheets.getId()+"/")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
	}  

	@Test
	public void FindById_IdIsNotValid_ReturnStatusNotFound() {

		 org.assertj.core.api.Assertions.assertThatThrownBy(() -> mvc.perform(get("/timesheets/999")
				.contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk())).hasCause(new EntityNotFoundException("Not found"));

	}
	@Test
	public void CreateTimesheets_TimesheetsDoesNotExist_ReturnStatusOk() throws Exception {
		CreateTimesheetsInput timesheetsInput = createTimesheetsInput();	
		
	    
		Tasks tasks =  createTasksEntity();

		timesheetsInput.setTaskId(Integer.parseInt(tasks.getId().toString()));
	    
		Users users =  createUsersEntity();

		timesheetsInput.setUserId(Integer.parseInt(users.getId().toString()));
		
		ObjectWriter ow = new ObjectMapper().registerModule(new JavaTimeModule()).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS).writer().withDefaultPrettyPrinter();
	
		String json = ow.writeValueAsString(timesheetsInput);

		mvc.perform(post("/timesheets").contentType(MediaType.APPLICATION_JSON).content(json))
		.andExpect(status().isOk());
	}     
	
	

	@Test
	public void DeleteTimesheets_IdIsNotValid_ThrowEntityNotFoundException() {

        doReturn(null).when(timesheetsAppService).findById(999);
        org.assertj.core.api.Assertions.assertThatThrownBy(() ->  mvc.perform(delete("/timesheets/999")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())).hasCause(new EntityNotFoundException("There does not exist a timesheets with a id=999"));

	}  

	@Test
	public void Delete_IdIsValid_ReturnStatusNoContent() throws Exception {
	
	 	Timesheets entity =  createNewEntity();
	 	entity.setVersiono(0L);
		Tasks tasks = createTasksEntity();
		entity.setTasks(tasks);
		Users users = createUsersEntity();
		entity.setUsers(users);
		entity = timesheets_repository.save(entity);
		

		FindTimesheetsByIdOutput output= new FindTimesheetsByIdOutput();
		output.setDate(entity.getDate());
		output.setHoursWorked(entity.getHoursWorked());
		output.setId(entity.getId());
		
         Mockito.doReturn(output).when(timesheetsAppService).findById(entity.getId());
       
    //    Mockito.when(timesheetsAppService.findById(entity.getId())).thenReturn(output);
        
		mvc.perform(delete("/timesheets/" + entity.getId()+"/")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isNoContent());
	}  


	@Test
	public void UpdateTimesheets_TimesheetsDoesNotExist_ReturnStatusNotFound() throws Exception {
   
        doReturn(null).when(timesheetsAppService).findById(999);
        
        UpdateTimesheetsInput timesheets = new UpdateTimesheetsInput();
		timesheets.setCreatedAt(SearchUtils.stringToLocalDateTime("1996-09-28 07:15:22"));
		timesheets.setDate(SearchUtils.stringToLocalDate("1996-09-28"));
		timesheets.setHoursWorked(new BigDecimal("999"));
		timesheets.setId(999);

		ObjectWriter ow = new ObjectMapper().registerModule(new JavaTimeModule()).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS).writer().withDefaultPrettyPrinter();
		String json = ow.writeValueAsString(timesheets);

		 org.assertj.core.api.Assertions.assertThatThrownBy(() -> mvc.perform(
		 	put("/timesheets/999")
		 	.contentType(MediaType.APPLICATION_JSON)
		 	.content(json))
			.andExpect(status().isOk())).hasCause(new EntityNotFoundException("Unable to update. Timesheets with id=999 not found."));
	}    

	@Test
	public void UpdateTimesheets_TimesheetsExists_ReturnStatusOk() throws Exception {
		Timesheets entity =  createUpdateEntity();
		entity.setVersiono(0L);
		
		Tasks tasks = createTasksEntity();
		entity.setTasks(tasks);
		Users users = createUsersEntity();
		entity.setUsers(users);
		entity = timesheets_repository.save(entity);
		FindTimesheetsByIdOutput output= new FindTimesheetsByIdOutput();
		output.setCreatedAt(entity.getCreatedAt());
		output.setDate(entity.getDate());
		output.setHoursWorked(entity.getHoursWorked());
		output.setId(entity.getId());
		output.setVersiono(entity.getVersiono());
		
        Mockito.when(timesheetsAppService.findById(entity.getId())).thenReturn(output);
        
        
		
		UpdateTimesheetsInput timesheetsInput = new UpdateTimesheetsInput();
		timesheetsInput.setDate(entity.getDate());
		timesheetsInput.setHoursWorked(entity.getHoursWorked());
		timesheetsInput.setId(entity.getId());
		
		timesheetsInput.setTaskId(Integer.parseInt(tasks.getId().toString()));
		timesheetsInput.setUserId(Integer.parseInt(users.getId().toString()));

		ObjectWriter ow = new ObjectMapper().registerModule(new JavaTimeModule()).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS).writer().withDefaultPrettyPrinter();
		String json = ow.writeValueAsString(timesheetsInput);
	
		mvc.perform(put("/timesheets/" + entity.getId()+"/").contentType(MediaType.APPLICATION_JSON).content(json))
		.andExpect(status().isOk());

		Timesheets de = createUpdateEntity();
		de.setId(entity.getId());
		timesheets_repository.delete(de);
		

	}    
	@Test
	public void FindAll_SearchIsNotNullAndPropertyIsValid_ReturnStatusOk() throws Exception {

		mvc.perform(get("/timesheets?search=id[equals]=1&limit=10&offset=1")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
	}
		
	
	@Test
	public void GetTasks_IdIsNotEmptyAndIdDoesNotExist_ReturnNotFound() {
  
	   org.assertj.core.api.Assertions.assertThatThrownBy(() ->  mvc.perform(get("/timesheets/999/tasks")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())).hasCause(new EntityNotFoundException("Not found"));
	
	}    
	
	@Test
	public void GetTasks_searchIsNotEmptyAndPropertyIsValid_ReturnList() throws Exception {
	
	   mvc.perform(get("/timesheets/" + timesheets.getId()+ "/tasks")
				.contentType(MediaType.APPLICATION_JSON))
	    		  .andExpect(status().isOk());
	}  
	
	
	@Test
	public void GetUsers_IdIsNotEmptyAndIdDoesNotExist_ReturnNotFound() {
  
	   org.assertj.core.api.Assertions.assertThatThrownBy(() ->  mvc.perform(get("/timesheets/999/users")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())).hasCause(new EntityNotFoundException("Not found"));
	
	}    
	
	@Test
	public void GetUsers_searchIsNotEmptyAndPropertyIsValid_ReturnList() throws Exception {
	
	   mvc.perform(get("/timesheets/" + timesheets.getId()+ "/users")
				.contentType(MediaType.APPLICATION_JSON))
	    		  .andExpect(status().isOk());
	}  
	
    
}

