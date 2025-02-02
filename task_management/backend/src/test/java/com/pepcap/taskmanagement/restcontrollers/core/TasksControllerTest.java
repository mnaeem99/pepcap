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
import com.pepcap.taskmanagement.application.core.tasks.TasksAppService;
import com.pepcap.taskmanagement.application.core.tasks.dto.*;
import com.pepcap.taskmanagement.domain.core.tasks.ITasksRepository;
import com.pepcap.taskmanagement.domain.core.tasks.Tasks;

import com.pepcap.taskmanagement.domain.core.projects.IProjectsRepository;
import com.pepcap.taskmanagement.domain.core.projects.Projects;
import com.pepcap.taskmanagement.domain.core.authorization.users.IUsersRepository;
import com.pepcap.taskmanagement.domain.core.authorization.users.Users;
import com.pepcap.taskmanagement.domain.core.tasks.ITasksRepository;
import com.pepcap.taskmanagement.domain.core.tasks.Tasks;
import com.pepcap.taskmanagement.application.core.projects.ProjectsAppService;    
import com.pepcap.taskmanagement.application.core.timesheets.TimesheetsAppService;    
import com.pepcap.taskmanagement.application.core.authorization.users.UsersAppService;    
import com.pepcap.taskmanagement.DatabaseContainerConfig;
import com.pepcap.taskmanagement.domain.core.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(properties = "spring.profiles.active=test")
public class TasksControllerTest extends DatabaseContainerConfig{
	
	@Autowired
	protected SortHandlerMethodArgumentResolver sortArgumentResolver;

	@Autowired
	@Qualifier("tasksRepository") 
	protected ITasksRepository tasks_repository;
	
	@Autowired
	@Qualifier("projectsRepository") 
	protected IProjectsRepository projectsRepository;
	
	@Autowired
	@Qualifier("usersRepository") 
	protected IUsersRepository usersRepository;
	
	@Autowired
	@Qualifier("tasksRepository") 
	protected ITasksRepository tasksRepository;
	

	@SpyBean
	@Qualifier("tasksAppService")
	protected TasksAppService tasksAppService;
	
    @SpyBean
    @Qualifier("projectsAppService")
	protected ProjectsAppService  projectsAppService;
	
    @SpyBean
    @Qualifier("timesheetsAppService")
	protected TimesheetsAppService  timesheetsAppService;
	
    @SpyBean
    @Qualifier("usersAppService")
	protected UsersAppService  usersAppService;
	
	@SpyBean
	protected LoggingHelper logHelper;

	@SpyBean
	protected Environment env;

	@Mock
	protected Logger loggerMock;

	protected Tasks tasks;

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
		em.createNativeQuery("truncate table task_management.tasks CASCADE").executeUpdate();
		em.createNativeQuery("truncate table task_management.projects CASCADE").executeUpdate();
		em.createNativeQuery("truncate table task_management.users CASCADE").executeUpdate();
		em.createNativeQuery("truncate table task_management.tasks CASCADE").executeUpdate();
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

	public Tasks createEntity() {
		Projects projects = createProjectsEntity();
		Users users = createUsersEntity();
	
		Tasks tasksEntity = new Tasks();
    	tasksEntity.setCreatedAt(SearchUtils.stringToLocalDateTime("1996-09-01 09:15:22"));
		tasksEntity.setDescription("1");
    	tasksEntity.setDueDate(SearchUtils.stringToLocalDate("1996-09-01"));
		tasksEntity.setId(1);
		tasksEntity.setName("1");
		tasksEntity.setStatus("1");
		tasksEntity.setVersiono(0L);
		tasksEntity.setProjects(projects);
		tasksEntity.setUsers(users);
		
		return tasksEntity;
	}
    public CreateTasksInput createTasksInput() {
	
	    CreateTasksInput tasksInput = new CreateTasksInput();
    	tasksInput.setCreatedAt(SearchUtils.stringToLocalDateTime("1996-08-10 05:25:22"));
  		tasksInput.setDescription("5");
    	tasksInput.setDueDate(SearchUtils.stringToLocalDate("1996-08-10"));
  		tasksInput.setName("5");
  		tasksInput.setStatus("5");
		
		return tasksInput;
	}

	public Tasks createNewEntity() {
		Tasks tasks = new Tasks();
    	tasks.setCreatedAt(SearchUtils.stringToLocalDateTime("1996-08-11 05:35:22"));
		tasks.setDescription("3");
    	tasks.setDueDate(SearchUtils.stringToLocalDate("1996-08-11"));
		tasks.setId(3);
		tasks.setName("3");
		tasks.setStatus("3");
		
		return tasks;
	}
	
	public Tasks createUpdateEntity() {
		Tasks tasks = new Tasks();
    	tasks.setCreatedAt(SearchUtils.stringToLocalDateTime("1996-09-09 05:45:22"));
		tasks.setDescription("4");
    	tasks.setDueDate(SearchUtils.stringToLocalDate("1996-09-09"));
		tasks.setId(4);
		tasks.setName("4");
		tasks.setStatus("4");
		
		return tasks;
	}
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
    
		final TasksController tasksController = new TasksController(tasksAppService, projectsAppService, timesheetsAppService, usersAppService,
		logHelper,env);
		when(logHelper.getLogger()).thenReturn(loggerMock);
		doNothing().when(loggerMock).error(anyString());

		this.mvc = MockMvcBuilders.standaloneSetup(tasksController)
				.setCustomArgumentResolvers(sortArgumentResolver)
				.setControllerAdvice()
				.build();
	}

	@Before
	public void initTest() {

		tasks= createEntity();
		List<Tasks> list= tasks_repository.findAll();
		if(!list.contains(tasks)) {
			tasks=tasks_repository.save(tasks);
		}

	}
	
	@Test
	public void FindById_IdIsValid_ReturnStatusOk() throws Exception {
	
		mvc.perform(get("/tasks/" + tasks.getId()+"/")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
	}  

	@Test
	public void FindById_IdIsNotValid_ReturnStatusNotFound() {

		 org.assertj.core.api.Assertions.assertThatThrownBy(() -> mvc.perform(get("/tasks/999")
				.contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk())).hasCause(new EntityNotFoundException("Not found"));

	}
	@Test
	public void CreateTasks_TasksDoesNotExist_ReturnStatusOk() throws Exception {
		CreateTasksInput tasksInput = createTasksInput();	
		
	    
		Projects projects =  createProjectsEntity();

		tasksInput.setProjectId(Integer.parseInt(projects.getId().toString()));
	    
		Users users =  createUsersEntity();

		tasksInput.setAssigneeId(Integer.parseInt(users.getId().toString()));
		
		ObjectWriter ow = new ObjectMapper().registerModule(new JavaTimeModule()).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS).writer().withDefaultPrettyPrinter();
	
		String json = ow.writeValueAsString(tasksInput);

		mvc.perform(post("/tasks").contentType(MediaType.APPLICATION_JSON).content(json))
		.andExpect(status().isOk());
	}     
	
	
	

	@Test
	public void DeleteTasks_IdIsNotValid_ThrowEntityNotFoundException() {

        doReturn(null).when(tasksAppService).findById(999);
        org.assertj.core.api.Assertions.assertThatThrownBy(() ->  mvc.perform(delete("/tasks/999")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())).hasCause(new EntityNotFoundException("There does not exist a tasks with a id=999"));

	}  

	@Test
	public void Delete_IdIsValid_ReturnStatusNoContent() throws Exception {
	
	 	Tasks entity =  createNewEntity();
	 	entity.setVersiono(0L);
		Projects projects = createProjectsEntity();
		entity.setProjects(projects);
		Users users = createUsersEntity();
		entity.setUsers(users);
		entity = tasks_repository.save(entity);
		

		FindTasksByIdOutput output= new FindTasksByIdOutput();
		output.setId(entity.getId());
		output.setName(entity.getName());
		
         Mockito.doReturn(output).when(tasksAppService).findById(entity.getId());
       
    //    Mockito.when(tasksAppService.findById(entity.getId())).thenReturn(output);
        
		mvc.perform(delete("/tasks/" + entity.getId()+"/")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isNoContent());
	}  


	@Test
	public void UpdateTasks_TasksDoesNotExist_ReturnStatusNotFound() throws Exception {
   
        doReturn(null).when(tasksAppService).findById(999);
        
        UpdateTasksInput tasks = new UpdateTasksInput();
		tasks.setCreatedAt(SearchUtils.stringToLocalDateTime("1996-09-28 07:15:22"));
  		tasks.setDescription("999");
		tasks.setDueDate(SearchUtils.stringToLocalDate("1996-09-28"));
		tasks.setId(999);
  		tasks.setName("999");
  		tasks.setStatus("999");

		ObjectWriter ow = new ObjectMapper().registerModule(new JavaTimeModule()).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS).writer().withDefaultPrettyPrinter();
		String json = ow.writeValueAsString(tasks);

		 org.assertj.core.api.Assertions.assertThatThrownBy(() -> mvc.perform(
		 	put("/tasks/999")
		 	.contentType(MediaType.APPLICATION_JSON)
		 	.content(json))
			.andExpect(status().isOk())).hasCause(new EntityNotFoundException("Unable to update. Tasks with id=999 not found."));
	}    

	@Test
	public void UpdateTasks_TasksExists_ReturnStatusOk() throws Exception {
		Tasks entity =  createUpdateEntity();
		entity.setVersiono(0L);
		
		Projects projects = createProjectsEntity();
		entity.setProjects(projects);
		Users users = createUsersEntity();
		entity.setUsers(users);
		entity = tasks_repository.save(entity);
		FindTasksByIdOutput output= new FindTasksByIdOutput();
		output.setCreatedAt(entity.getCreatedAt());
		output.setDescription(entity.getDescription());
		output.setDueDate(entity.getDueDate());
		output.setId(entity.getId());
		output.setName(entity.getName());
		output.setStatus(entity.getStatus());
		output.setVersiono(entity.getVersiono());
		
        Mockito.when(tasksAppService.findById(entity.getId())).thenReturn(output);
        
        
		
		UpdateTasksInput tasksInput = new UpdateTasksInput();
		tasksInput.setId(entity.getId());
		tasksInput.setName(entity.getName());
		
		tasksInput.setProjectId(Integer.parseInt(projects.getId().toString()));
		tasksInput.setAssigneeId(Integer.parseInt(users.getId().toString()));

		ObjectWriter ow = new ObjectMapper().registerModule(new JavaTimeModule()).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS).writer().withDefaultPrettyPrinter();
		String json = ow.writeValueAsString(tasksInput);
	
		mvc.perform(put("/tasks/" + entity.getId()+"/").contentType(MediaType.APPLICATION_JSON).content(json))
		.andExpect(status().isOk());

		Tasks de = createUpdateEntity();
		de.setId(entity.getId());
		tasks_repository.delete(de);
		

	}    
	@Test
	public void FindAll_SearchIsNotNullAndPropertyIsValid_ReturnStatusOk() throws Exception {

		mvc.perform(get("/tasks?search=id[equals]=1&limit=10&offset=1")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
	}
		
	
	@Test
	public void GetProjects_IdIsNotEmptyAndIdDoesNotExist_ReturnNotFound() {
  
	   org.assertj.core.api.Assertions.assertThatThrownBy(() ->  mvc.perform(get("/tasks/999/projects")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())).hasCause(new EntityNotFoundException("Not found"));
	
	}    
	
	@Test
	public void GetProjects_searchIsNotEmptyAndPropertyIsValid_ReturnList() throws Exception {
	
	   mvc.perform(get("/tasks/" + tasks.getId()+ "/projects")
				.contentType(MediaType.APPLICATION_JSON))
	    		  .andExpect(status().isOk());
	}  
	
	
	
	@Test
	public void GetTimesheets_searchIsNotEmptyAndPropertyIsValid_ReturnList() throws Exception {

		Map<String,String> joinCol = new HashMap<String,String>();
		joinCol.put("id", "1");
		
        Mockito.when(tasksAppService.parseTimesheetsJoinColumn("taskId")).thenReturn(joinCol);
		mvc.perform(get("/tasks/1/timesheets?search=taskId[equals]=1&limit=10&offset=1")
				.contentType(MediaType.APPLICATION_JSON))
	    		  .andExpect(status().isOk());
	}  
	
	@Test
	public void GetTimesheets_searchIsNotEmpty() {
	
		Mockito.when(tasksAppService.parseTimesheetsJoinColumn(anyString())).thenReturn(null);
	 		  		    		  
	    org.assertj.core.api.Assertions.assertThatThrownBy(() -> mvc.perform(get("/tasks/1/timesheets?search=taskId[equals]=1&limit=10&offset=1")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())).hasCause(new EntityNotFoundException("Invalid join column"));
	}    
	
	@Test
	public void GetUsers_IdIsNotEmptyAndIdDoesNotExist_ReturnNotFound() {
  
	   org.assertj.core.api.Assertions.assertThatThrownBy(() ->  mvc.perform(get("/tasks/999/users")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())).hasCause(new EntityNotFoundException("Not found"));
	
	}    
	
	@Test
	public void GetUsers_searchIsNotEmptyAndPropertyIsValid_ReturnList() throws Exception {
	
	   mvc.perform(get("/tasks/" + tasks.getId()+ "/users")
				.contentType(MediaType.APPLICATION_JSON))
	    		  .andExpect(status().isOk());
	}  
	
    
}

