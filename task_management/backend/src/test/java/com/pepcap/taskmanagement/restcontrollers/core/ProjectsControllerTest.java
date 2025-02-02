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
import com.pepcap.taskmanagement.application.core.projects.ProjectsAppService;
import com.pepcap.taskmanagement.application.core.projects.dto.*;
import com.pepcap.taskmanagement.domain.core.projects.IProjectsRepository;
import com.pepcap.taskmanagement.domain.core.projects.Projects;

import com.pepcap.taskmanagement.domain.core.tasks.ITasksRepository;
import com.pepcap.taskmanagement.domain.core.tasks.Tasks;
import com.pepcap.taskmanagement.domain.core.projects.IProjectsRepository;
import com.pepcap.taskmanagement.domain.core.projects.Projects;
import com.pepcap.taskmanagement.domain.core.authorization.users.IUsersRepository;
import com.pepcap.taskmanagement.domain.core.authorization.users.Users;
import com.pepcap.taskmanagement.application.core.tasks.TasksAppService;    
import com.pepcap.taskmanagement.DatabaseContainerConfig;
import com.pepcap.taskmanagement.domain.core.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(properties = "spring.profiles.active=test")
public class ProjectsControllerTest extends DatabaseContainerConfig{
	
	@Autowired
	protected SortHandlerMethodArgumentResolver sortArgumentResolver;

	@Autowired
	@Qualifier("projectsRepository") 
	protected IProjectsRepository projects_repository;
	
	@Autowired
	@Qualifier("tasksRepository") 
	protected ITasksRepository tasksRepository;
	
	@Autowired
	@Qualifier("projectsRepository") 
	protected IProjectsRepository projectsRepository;
	
	@Autowired
	@Qualifier("usersRepository") 
	protected IUsersRepository usersRepository;
	

	@SpyBean
	@Qualifier("projectsAppService")
	protected ProjectsAppService projectsAppService;
	
    @SpyBean
    @Qualifier("tasksAppService")
	protected TasksAppService  tasksAppService;
	
	@SpyBean
	protected LoggingHelper logHelper;

	@SpyBean
	protected Environment env;

	@Mock
	protected Logger loggerMock;

	protected Projects projects;

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
		em.createNativeQuery("truncate table task_management.projects CASCADE").executeUpdate();
		em.createNativeQuery("truncate table task_management.tasks CASCADE").executeUpdate();
		em.createNativeQuery("truncate table task_management.projects CASCADE").executeUpdate();
		em.createNativeQuery("truncate table task_management.users CASCADE").executeUpdate();
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

	public Projects createEntity() {
	
		Projects projectsEntity = new Projects();
    	projectsEntity.setCreatedAt(SearchUtils.stringToLocalDateTime("1996-09-01 09:15:22"));
		projectsEntity.setDescription("1");
    	projectsEntity.setEndDate(SearchUtils.stringToLocalDate("1996-09-01"));
		projectsEntity.setId(1);
		projectsEntity.setName("1");
    	projectsEntity.setStartDate(SearchUtils.stringToLocalDate("1996-09-01"));
		projectsEntity.setStatus("1");
		projectsEntity.setVersiono(0L);
		
		return projectsEntity;
	}
    public CreateProjectsInput createProjectsInput() {
	
	    CreateProjectsInput projectsInput = new CreateProjectsInput();
    	projectsInput.setCreatedAt(SearchUtils.stringToLocalDateTime("1996-08-10 05:25:22"));
  		projectsInput.setDescription("5");
    	projectsInput.setEndDate(SearchUtils.stringToLocalDate("1996-08-10"));
  		projectsInput.setName("5");
    	projectsInput.setStartDate(SearchUtils.stringToLocalDate("1996-08-10"));
  		projectsInput.setStatus("5");
		
		return projectsInput;
	}

	public Projects createNewEntity() {
		Projects projects = new Projects();
    	projects.setCreatedAt(SearchUtils.stringToLocalDateTime("1996-08-11 05:35:22"));
		projects.setDescription("3");
    	projects.setEndDate(SearchUtils.stringToLocalDate("1996-08-11"));
		projects.setId(3);
		projects.setName("3");
    	projects.setStartDate(SearchUtils.stringToLocalDate("1996-08-11"));
		projects.setStatus("3");
		
		return projects;
	}
	
	public Projects createUpdateEntity() {
		Projects projects = new Projects();
    	projects.setCreatedAt(SearchUtils.stringToLocalDateTime("1996-09-09 05:45:22"));
		projects.setDescription("4");
    	projects.setEndDate(SearchUtils.stringToLocalDate("1996-09-09"));
		projects.setId(4);
		projects.setName("4");
    	projects.setStartDate(SearchUtils.stringToLocalDate("1996-09-09"));
		projects.setStatus("4");
		
		return projects;
	}
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
    
		final ProjectsController projectsController = new ProjectsController(projectsAppService, tasksAppService,
		logHelper,env);
		when(logHelper.getLogger()).thenReturn(loggerMock);
		doNothing().when(loggerMock).error(anyString());

		this.mvc = MockMvcBuilders.standaloneSetup(projectsController)
				.setCustomArgumentResolvers(sortArgumentResolver)
				.setControllerAdvice()
				.build();
	}

	@Before
	public void initTest() {

		projects= createEntity();
		List<Projects> list= projects_repository.findAll();
		if(!list.contains(projects)) {
			projects=projects_repository.save(projects);
		}

	}
	
	@Test
	public void FindById_IdIsValid_ReturnStatusOk() throws Exception {
	
		mvc.perform(get("/projects/" + projects.getId()+"/")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
	}  

	@Test
	public void FindById_IdIsNotValid_ReturnStatusNotFound() {

		 org.assertj.core.api.Assertions.assertThatThrownBy(() -> mvc.perform(get("/projects/999")
				.contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk())).hasCause(new EntityNotFoundException("Not found"));

	}
	@Test
	public void CreateProjects_ProjectsDoesNotExist_ReturnStatusOk() throws Exception {
		CreateProjectsInput projectsInput = createProjectsInput();	
		
		
		ObjectWriter ow = new ObjectMapper().registerModule(new JavaTimeModule()).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS).writer().withDefaultPrettyPrinter();
	
		String json = ow.writeValueAsString(projectsInput);

		mvc.perform(post("/projects").contentType(MediaType.APPLICATION_JSON).content(json))
		.andExpect(status().isOk());
	}     
	

	@Test
	public void DeleteProjects_IdIsNotValid_ThrowEntityNotFoundException() {

        doReturn(null).when(projectsAppService).findById(999);
        org.assertj.core.api.Assertions.assertThatThrownBy(() ->  mvc.perform(delete("/projects/999")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())).hasCause(new EntityNotFoundException("There does not exist a projects with a id=999"));

	}  

	@Test
	public void Delete_IdIsValid_ReturnStatusNoContent() throws Exception {
	
	 	Projects entity =  createNewEntity();
	 	entity.setVersiono(0L);
		entity = projects_repository.save(entity);
		

		FindProjectsByIdOutput output= new FindProjectsByIdOutput();
		output.setId(entity.getId());
		output.setName(entity.getName());
		
         Mockito.doReturn(output).when(projectsAppService).findById(entity.getId());
       
    //    Mockito.when(projectsAppService.findById(entity.getId())).thenReturn(output);
        
		mvc.perform(delete("/projects/" + entity.getId()+"/")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isNoContent());
	}  


	@Test
	public void UpdateProjects_ProjectsDoesNotExist_ReturnStatusNotFound() throws Exception {
   
        doReturn(null).when(projectsAppService).findById(999);
        
        UpdateProjectsInput projects = new UpdateProjectsInput();
		projects.setCreatedAt(SearchUtils.stringToLocalDateTime("1996-09-28 07:15:22"));
  		projects.setDescription("999");
		projects.setEndDate(SearchUtils.stringToLocalDate("1996-09-28"));
		projects.setId(999);
  		projects.setName("999");
		projects.setStartDate(SearchUtils.stringToLocalDate("1996-09-28"));
  		projects.setStatus("999");

		ObjectWriter ow = new ObjectMapper().registerModule(new JavaTimeModule()).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS).writer().withDefaultPrettyPrinter();
		String json = ow.writeValueAsString(projects);

		 org.assertj.core.api.Assertions.assertThatThrownBy(() -> mvc.perform(
		 	put("/projects/999")
		 	.contentType(MediaType.APPLICATION_JSON)
		 	.content(json))
			.andExpect(status().isOk())).hasCause(new EntityNotFoundException("Unable to update. Projects with id=999 not found."));
	}    

	@Test
	public void UpdateProjects_ProjectsExists_ReturnStatusOk() throws Exception {
		Projects entity =  createUpdateEntity();
		entity.setVersiono(0L);
		
		entity = projects_repository.save(entity);
		FindProjectsByIdOutput output= new FindProjectsByIdOutput();
		output.setCreatedAt(entity.getCreatedAt());
		output.setDescription(entity.getDescription());
		output.setEndDate(entity.getEndDate());
		output.setId(entity.getId());
		output.setName(entity.getName());
		output.setStartDate(entity.getStartDate());
		output.setStatus(entity.getStatus());
		output.setVersiono(entity.getVersiono());
		
        Mockito.when(projectsAppService.findById(entity.getId())).thenReturn(output);
        
        
		
		UpdateProjectsInput projectsInput = new UpdateProjectsInput();
		projectsInput.setId(entity.getId());
		projectsInput.setName(entity.getName());
		

		ObjectWriter ow = new ObjectMapper().registerModule(new JavaTimeModule()).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS).writer().withDefaultPrettyPrinter();
		String json = ow.writeValueAsString(projectsInput);
	
		mvc.perform(put("/projects/" + entity.getId()+"/").contentType(MediaType.APPLICATION_JSON).content(json))
		.andExpect(status().isOk());

		Projects de = createUpdateEntity();
		de.setId(entity.getId());
		projects_repository.delete(de);
		

	}    
	@Test
	public void FindAll_SearchIsNotNullAndPropertyIsValid_ReturnStatusOk() throws Exception {

		mvc.perform(get("/projects?search=id[equals]=1&limit=10&offset=1")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
	}
		
	
	
	@Test
	public void GetTasks_searchIsNotEmptyAndPropertyIsValid_ReturnList() throws Exception {

		Map<String,String> joinCol = new HashMap<String,String>();
		joinCol.put("id", "1");
		
        Mockito.when(projectsAppService.parseTasksJoinColumn("projectId")).thenReturn(joinCol);
		mvc.perform(get("/projects/1/tasks?search=projectId[equals]=1&limit=10&offset=1")
				.contentType(MediaType.APPLICATION_JSON))
	    		  .andExpect(status().isOk());
	}  
	
	@Test
	public void GetTasks_searchIsNotEmpty() {
	
		Mockito.when(projectsAppService.parseTasksJoinColumn(anyString())).thenReturn(null);
	 		  		    		  
	    org.assertj.core.api.Assertions.assertThatThrownBy(() -> mvc.perform(get("/projects/1/tasks?search=projectId[equals]=1&limit=10&offset=1")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())).hasCause(new EntityNotFoundException("Invalid join column"));
	}    
    
}

