package com.pepcap.taskmanagement.restcontrollers.core;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.any;
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
import javax.persistence.EntityExistsException;

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
import com.pepcap.taskmanagement.application.core.authorization.users.UsersAppService;
import com.pepcap.taskmanagement.application.core.authorization.users.dto.*;
import com.pepcap.taskmanagement.domain.core.authorization.users.IUsersRepository;
import com.pepcap.taskmanagement.domain.core.authorization.users.Users;

import com.pepcap.taskmanagement.domain.core.tasks.ITasksRepository;
import com.pepcap.taskmanagement.domain.core.tasks.Tasks;
import com.pepcap.taskmanagement.domain.core.projects.IProjectsRepository;
import com.pepcap.taskmanagement.domain.core.projects.Projects;
import com.pepcap.taskmanagement.domain.core.authorization.users.IUsersRepository;
import com.pepcap.taskmanagement.domain.core.authorization.users.Users;
import com.pepcap.taskmanagement.application.core.tasks.TasksAppService;    
import com.pepcap.taskmanagement.application.core.timesheets.TimesheetsAppService;    
import com.pepcap.taskmanagement.application.core.authorization.userspermission.UserspermissionAppService;    
import com.pepcap.taskmanagement.application.core.authorization.usersrole.UsersroleAppService;    
import com.pepcap.taskmanagement.security.JWTAppService;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.pepcap.taskmanagement.application.core.authorization.usersrole.UsersroleAppService;
import com.pepcap.taskmanagement.application.core.authorization.userspermission.UserspermissionAppService;
import com.pepcap.taskmanagement.domain.core.authorization.userspreference.Userspreference;
import com.pepcap.taskmanagement.domain.core.authorization.userspreference.IUserspreferenceManager;
import com.pepcap.taskmanagement.DatabaseContainerConfig;
import com.pepcap.taskmanagement.domain.core.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(properties = "spring.profiles.active=test")
public class UsersControllerTest extends DatabaseContainerConfig{
	
	@Autowired
	protected SortHandlerMethodArgumentResolver sortArgumentResolver;

	@Autowired
	@Qualifier("usersRepository") 
	protected IUsersRepository users_repository;
	
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
	@Qualifier("usersAppService")
	protected UsersAppService usersAppService;
	
    @SpyBean
    @Qualifier("tasksAppService")
	protected TasksAppService  tasksAppService;
	
    @SpyBean
    @Qualifier("timesheetsAppService")
	protected TimesheetsAppService  timesheetsAppService;
	
    @SpyBean
    @Qualifier("userspermissionAppService")
	protected UserspermissionAppService  userspermissionAppService;
	
    @SpyBean
    @Qualifier("usersroleAppService")
	protected UsersroleAppService  usersroleAppService;
	
    @SpyBean
	protected IUserspreferenceManager userspreferenceManager;
	
	@SpyBean
	protected JWTAppService jwtAppService;
	
	@SpyBean
    protected PasswordEncoder pEncoder;
    
	@SpyBean
	protected LoggingHelper logHelper;

	@SpyBean
	protected Environment env;

	@Mock
	protected Logger loggerMock;

	protected Users users;

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
		em.createNativeQuery("truncate table task_management.users CASCADE").executeUpdate();
		em.createNativeQuery("truncate table task_management.tasks CASCADE").executeUpdate();
		em.createNativeQuery("truncate table task_management.projects CASCADE").executeUpdate();
		em.createNativeQuery("truncate table task_management.users CASCADE").executeUpdate();
		em.createNativeQuery("truncate table task_management.userspreference CASCADE").executeUpdate();
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
        usersEntity.setEmail("bbc"+countUsers+"@d.c");
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

	public Users createEntity() {
	
		Users usersEntity = new Users();
    	usersEntity.setCreatedAt(SearchUtils.stringToLocalDateTime("1996-09-01 09:15:22"));
        usersEntity.setEmail("bbc@d.c");
		usersEntity.setFirstName("1");
		usersEntity.setId(1);
		usersEntity.setIsActive(false);
		usersEntity.setIsEmailConfirmed(false);
		usersEntity.setLastName("1");
		usersEntity.setPassword("1");
		usersEntity.setPhoneNumber("1");
		usersEntity.setRole("1");
    	usersEntity.setUpdatedAt(SearchUtils.stringToLocalDateTime("1996-09-01 09:15:22"));
		usersEntity.setUsername("1");
		usersEntity.setVersiono(0L);
		
		return usersEntity;
	}
    public CreateUsersInput createUsersInput() {
	
	    CreateUsersInput usersInput = new CreateUsersInput();
    	usersInput.setCreatedAt(SearchUtils.stringToLocalDateTime("1996-08-10 05:25:22"));
        usersInput.setEmail("pmk@d.c");
  		usersInput.setFirstName("5");
		usersInput.setIsActive(false);
		usersInput.setIsEmailConfirmed(false);
  		usersInput.setLastName("5");
  		usersInput.setPassword("5");
  		usersInput.setPhoneNumber("5");
  		usersInput.setRole("5");
    	usersInput.setUpdatedAt(SearchUtils.stringToLocalDateTime("1996-08-10 05:25:22"));
  		usersInput.setUsername("5");
		
		return usersInput;
	}

	public Users createNewEntity() {
		Users users = new Users();
    	users.setCreatedAt(SearchUtils.stringToLocalDateTime("1996-08-11 05:35:22"));
        users.setEmail("bmc@d.c");
		users.setFirstName("3");
		users.setId(3);
		users.setIsActive(false);
		users.setIsEmailConfirmed(false);
		users.setLastName("3");
		users.setPassword("3");
		users.setPhoneNumber("3");
		users.setRole("3");
    	users.setUpdatedAt(SearchUtils.stringToLocalDateTime("1996-08-11 05:35:22"));
		users.setUsername("3");
		
		return users;
	}
	
	public Users createUpdateEntity() {
		Users users = new Users();
    	users.setCreatedAt(SearchUtils.stringToLocalDateTime("1996-09-09 05:45:22"));
        users.setEmail("pmlp@d.c");
		users.setFirstName("4");
		users.setId(4);
		users.setIsActive(false);
		users.setIsEmailConfirmed(false);
		users.setLastName("4");
		users.setPassword("4");
		users.setPhoneNumber("4");
		users.setRole("4");
    	users.setUpdatedAt(SearchUtils.stringToLocalDateTime("1996-09-09 05:45:22"));
		users.setUsername("4");
		
		return users;
	}
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
    
		final UsersController usersController = new UsersController(usersAppService, tasksAppService, timesheetsAppService, userspermissionAppService, usersroleAppService,
		pEncoder,jwtAppService,logHelper,env);
		when(logHelper.getLogger()).thenReturn(loggerMock);
		doNothing().when(loggerMock).error(anyString());

		this.mvc = MockMvcBuilders.standaloneSetup(usersController)
				.setCustomArgumentResolvers(sortArgumentResolver)
				.setControllerAdvice()
				.build();
	}

	@Before
	public void initTest() {

		users= createEntity();
		List<Users> list= users_repository.findAll();
		if(!list.contains(users)) {
			users=users_repository.save(users);
		}

	}
	
	@Test
	public void FindById_IdIsValid_ReturnStatusOk() throws Exception {
	
		mvc.perform(get("/users/" + users.getId()+"/")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
	}  

	@Test
	public void FindById_IdIsNotValid_ReturnStatusNotFound() {

		 org.assertj.core.api.Assertions.assertThatThrownBy(() -> mvc.perform(get("/users/999")
				.contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk())).hasCause(new EntityNotFoundException("Not found"));

	}
	@Test
	public void CreateUsers_UsersDoesNotExist_ReturnStatusOk() throws Exception {
        Mockito.doReturn(null).when(usersAppService).findByUsername(anyString());
	  
		CreateUsersInput users = createUsersInput();
		ObjectWriter ow = new ObjectMapper().registerModule(new JavaTimeModule()).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS).writer().withDefaultPrettyPrinter();
		String json = ow.writeValueAsString(users);
		
		
		mvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON).content(json))
		  .andExpect(status().isOk());
		 
		 users_repository.delete(createNewEntity());
		 
	}  
    
	@Test
	public void CreateUsers_UsersAlreadyExists_ThrowEntityExistsException() throws Exception {
	    FindUsersByUsernameOutput output= new FindUsersByUsernameOutput();
        output.setEmail("bpc@g.c");
  		output.setFirstName("1");
		output.setIsActive(false);
  		output.setLastName("1");
  		output.setRole("1");
  		output.setUsername("1");

        Mockito.doReturn(output).when(usersAppService).findByUsername(anyString());
	    CreateUsersInput users = createUsersInput();
	    ObjectWriter ow = new ObjectMapper().registerModule(new JavaTimeModule()).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS).writer().withDefaultPrettyPrinter();
	
		String json = ow.writeValueAsString(users);
       
        org.assertj.core.api.Assertions.assertThatThrownBy(() -> mvc.perform(post("/users")
        		.contentType(MediaType.APPLICATION_JSON).content(json))
         .andExpect(status().isOk())).hasCause(new EntityExistsException("There already exists a users with Username =" + users.getUsername()));
	} 
	
	
	
	

	@Test
	public void DeleteUsers_IdIsNotValid_ThrowEntityNotFoundException() {

        doReturn(null).when(usersAppService).findById(999);
        org.assertj.core.api.Assertions.assertThatThrownBy(() ->  mvc.perform(delete("/users/999")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())).hasCause(new EntityNotFoundException("There does not exist a users with a id=999"));

	}  

	@Test
	public void Delete_IdIsValid_ReturnStatusNoContent() throws Exception {
	
	 	Users entity =  createNewEntity();
	 	entity.setVersiono(0L);
		entity = users_repository.save(entity);
		
		Userspreference userspreference = new Userspreference();
		userspreference.setId(entity.getId());
		userspreference.setUsers(entity);
		userspreference.setTheme("Abc");
		userspreference.setLanguage("abc");
	 	userspreference=userspreferenceManager.create(userspreference);

		FindUsersByIdOutput output= new FindUsersByIdOutput();
		output.setEmail(entity.getEmail());
		output.setFirstName(entity.getFirstName());
		output.setId(entity.getId());
		output.setIsActive(entity.getIsActive());
		output.setLastName(entity.getLastName());
		output.setRole(entity.getRole());
		output.setUsername(entity.getUsername());
		
         Mockito.doReturn(output).when(usersAppService).findById(any(Integer.class));
       
      //   Mockito.when(usersAppService.findById(any(Integer.class))).thenReturn(output);
        
		mvc.perform(delete("/users/" + entity.getId()+"/")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isNoContent());
	}  


	@Test
	public void UpdateUsers_UsersDoesNotExist_ReturnStatusNotFound() throws Exception {
   
        doReturn(null).when(usersAppService).findById(999);
        
        UpdateUsersInput users = new UpdateUsersInput();
		users.setCreatedAt(SearchUtils.stringToLocalDateTime("1996-09-28 07:15:22"));
        users.setEmail("bmc@g.c");
  		users.setFirstName("999");
		users.setId(999);
		users.setIsActive(false);
		users.setIsEmailConfirmed(false);
  		users.setLastName("999");
  		users.setPhoneNumber("999");
  		users.setRole("999");
		users.setUpdatedAt(SearchUtils.stringToLocalDateTime("1996-09-28 07:15:22"));
  		users.setUsername("999");

		ObjectWriter ow = new ObjectMapper().registerModule(new JavaTimeModule()).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS).writer().withDefaultPrettyPrinter();
		String json = ow.writeValueAsString(users);

		 org.assertj.core.api.Assertions.assertThatThrownBy(() -> mvc.perform(
		 	put("/users/999")
		 	.contentType(MediaType.APPLICATION_JSON)
		 	.content(json))
			.andExpect(status().isOk())).hasCause(new EntityNotFoundException("Unable to update. Users with id=999 not found."));
	}    

	@Test
	public void UpdateUsers_UsersExists_ReturnStatusOk() throws Exception {
		Users entity =  createUpdateEntity();
		entity.setVersiono(0L);
		
		entity = users_repository.save(entity);
		FindUsersWithAllFieldsByIdOutput output= new FindUsersWithAllFieldsByIdOutput();
		output.setCreatedAt(entity.getCreatedAt());
		output.setEmail(entity.getEmail());
		output.setFirstName(entity.getFirstName());
		output.setId(entity.getId());
		output.setIsActive(entity.getIsActive());
		output.setIsEmailConfirmed(entity.getIsEmailConfirmed());
		output.setLastName(entity.getLastName());
		output.setPassword(entity.getPassword());
		output.setPhoneNumber(entity.getPhoneNumber());
		output.setRole(entity.getRole());
		output.setUpdatedAt(entity.getUpdatedAt());
		output.setUsername(entity.getUsername());
		output.setVersiono(entity.getVersiono());
		
		Mockito.when(usersAppService.findWithAllFieldsById(entity.getId())).thenReturn(output);
        
		
		UpdateUsersInput usersInput = new UpdateUsersInput();
		usersInput.setEmail(entity.getEmail());
		usersInput.setFirstName(entity.getFirstName());
		usersInput.setId(entity.getId());
		usersInput.setIsActive(entity.getIsActive());
		usersInput.setLastName(entity.getLastName());
		usersInput.setPassword(entity.getPassword());
		usersInput.setRole(entity.getRole());
		usersInput.setUsername(entity.getUsername());
		

		ObjectWriter ow = new ObjectMapper().registerModule(new JavaTimeModule()).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS).writer().withDefaultPrettyPrinter();
		String json = ow.writeValueAsString(usersInput);
	
		mvc.perform(put("/users/" + entity.getId()+"/").contentType(MediaType.APPLICATION_JSON).content(json))
		.andExpect(status().isOk());

		Users de = createUpdateEntity();
		de.setId(entity.getId());
		users_repository.delete(de);
		

	}    
	@Test
	public void FindAll_SearchIsNotNullAndPropertyIsValid_ReturnStatusOk() throws Exception {

		mvc.perform(get("/users?search=id[equals]=1&limit=10&offset=1")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
	}
		
	
	
	@Test
	public void GetTasks_searchIsNotEmptyAndPropertyIsValid_ReturnList() throws Exception {

		Map<String,String> joinCol = new HashMap<String,String>();
		joinCol.put("id", "1");
		
        Mockito.when(usersAppService.parseTasksJoinColumn("assigneeId")).thenReturn(joinCol);
		mvc.perform(get("/users/1/tasks?search=assigneeId[equals]=1&limit=10&offset=1")
				.contentType(MediaType.APPLICATION_JSON))
	    		  .andExpect(status().isOk());
	}  
	
	@Test
	public void GetTasks_searchIsNotEmpty() {
	
		Mockito.when(usersAppService.parseTasksJoinColumn(anyString())).thenReturn(null);
	 		  		    		  
	    org.assertj.core.api.Assertions.assertThatThrownBy(() -> mvc.perform(get("/users/1/tasks?search=assigneeId[equals]=1&limit=10&offset=1")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())).hasCause(new EntityNotFoundException("Invalid join column"));
	}    
	
	
	@Test
	public void GetTimesheets_searchIsNotEmptyAndPropertyIsValid_ReturnList() throws Exception {

		Map<String,String> joinCol = new HashMap<String,String>();
		joinCol.put("id", "1");
		
        Mockito.when(usersAppService.parseTimesheetsJoinColumn("userId")).thenReturn(joinCol);
		mvc.perform(get("/users/1/timesheets?search=userId[equals]=1&limit=10&offset=1")
				.contentType(MediaType.APPLICATION_JSON))
	    		  .andExpect(status().isOk());
	}  
	
	@Test
	public void GetTimesheets_searchIsNotEmpty() {
	
		Mockito.when(usersAppService.parseTimesheetsJoinColumn(anyString())).thenReturn(null);
	 		  		    		  
	    org.assertj.core.api.Assertions.assertThatThrownBy(() -> mvc.perform(get("/users/1/timesheets?search=userId[equals]=1&limit=10&offset=1")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())).hasCause(new EntityNotFoundException("Invalid join column"));
	}    
	
	
	@Test
	public void GetUserspermissions_searchIsNotEmptyAndPropertyIsValid_ReturnList() throws Exception {

		Map<String,String> joinCol = new HashMap<String,String>();
		joinCol.put("id", "1");
		
        Mockito.when(usersAppService.parseUserspermissionsJoinColumn("usersId")).thenReturn(joinCol);
		mvc.perform(get("/users/1/userspermissions?search=usersId[equals]=1&limit=10&offset=1")
				.contentType(MediaType.APPLICATION_JSON))
	    		  .andExpect(status().isOk());
	}  
	
	@Test
	public void GetUserspermissions_searchIsNotEmpty() {
	
		Mockito.when(usersAppService.parseUserspermissionsJoinColumn(anyString())).thenReturn(null);
	 		  		    		  
	    org.assertj.core.api.Assertions.assertThatThrownBy(() -> mvc.perform(get("/users/1/userspermissions?search=usersId[equals]=1&limit=10&offset=1")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())).hasCause(new EntityNotFoundException("Invalid join column"));
	}    
	
	
	@Test
	public void GetUsersroles_searchIsNotEmptyAndPropertyIsValid_ReturnList() throws Exception {

		Map<String,String> joinCol = new HashMap<String,String>();
		joinCol.put("id", "1");
		
        Mockito.when(usersAppService.parseUsersrolesJoinColumn("usersId")).thenReturn(joinCol);
		mvc.perform(get("/users/1/usersroles?search=usersId[equals]=1&limit=10&offset=1")
				.contentType(MediaType.APPLICATION_JSON))
	    		  .andExpect(status().isOk());
	}  
	
	@Test
	public void GetUsersroles_searchIsNotEmpty() {
	
		Mockito.when(usersAppService.parseUsersrolesJoinColumn(anyString())).thenReturn(null);
	 		  		    		  
	    org.assertj.core.api.Assertions.assertThatThrownBy(() -> mvc.perform(get("/users/1/usersroles?search=usersId[equals]=1&limit=10&offset=1")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())).hasCause(new EntityNotFoundException("Invalid join column"));
	}    
    
}

