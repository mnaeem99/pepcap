package com.pepcap.taskmanagement.restcontrollers.extended;

import com.pepcap.taskmanagement.DatabaseContainerConfig;
import com.pepcap.taskmanagement.commons.logging.LoggingHelper;
import com.pepcap.taskmanagement.application.extended.projects.ProjectsAppServiceExtended;
import com.pepcap.taskmanagement.domain.extended.projects.IProjectsRepositoryExtended;
import com.pepcap.taskmanagement.domain.core.projects.Projects;
import com.pepcap.taskmanagement.domain.extended.tasks.ITasksRepositoryExtended;
import com.pepcap.taskmanagement.domain.extended.projects.IProjectsRepositoryExtended;
import com.pepcap.taskmanagement.domain.core.projects.Projects;
import com.pepcap.taskmanagement.domain.extended.authorization.users.IUsersRepositoryExtended;
import com.pepcap.taskmanagement.application.extended.tasks.TasksAppServiceExtended;    

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.junit.AfterClass;
import org.junit.Before;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.core.env.Environment;
import org.springframework.data.web.SortHandlerMethodArgumentResolver;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


/*Uncomment below annotations before running tests*/
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest(properties = "spring.profiles.active=test")
public class ProjectsControllerTestExtended extends DatabaseContainerConfig {
	
	@Autowired
	protected SortHandlerMethodArgumentResolver sortArgumentResolver;

	@Autowired
	@Qualifier("projectsRepositoryExtended") 
	protected IProjectsRepositoryExtended projects_repositoryExtended;
	
	@Autowired
	@Qualifier("tasksRepositoryExtended") 
	protected ITasksRepositoryExtended tasksRepositoryExtended;
	
	@Autowired
	@Qualifier("projectsRepositoryExtended") 
	protected IProjectsRepositoryExtended projectsRepositoryExtended;
	
	@Autowired
	@Qualifier("usersRepositoryExtended") 
	protected IUsersRepositoryExtended usersRepositoryExtended;
	

	@SpyBean
	@Qualifier("projectsAppServiceExtended")
	protected ProjectsAppServiceExtended projectsAppServiceExtended;
	
    @SpyBean
    @Qualifier("tasksAppServiceExtended")
	protected TasksAppServiceExtended  tasksAppServiceExtended;
	
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
    
 	@PostConstruct
	public void init() {
	emfs = emf;
	}

	@AfterClass
	public static void cleanup() {
		EntityManager em = emfs.createEntityManager();
		//add your code you want to execute after class  
	}
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
    
		final ProjectsControllerExtended projectsController = new ProjectsControllerExtended(projectsAppServiceExtended, tasksAppServiceExtended,
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
     //add code required for initialization 
	}
		
	//Add your custom code here	
}
