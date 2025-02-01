package com.pepcap.ecommerce.restcontrollers.extended;

import com.pepcap.ecommerce.DatabaseContainerConfig;
import com.pepcap.ecommerce.commons.logging.LoggingHelper;
import com.pepcap.ecommerce.application.extended.authorization.userspermission.UserspermissionAppServiceExtended;
import com.pepcap.ecommerce.domain.extended.authorization.userspermission.IUserspermissionRepositoryExtended;
import com.pepcap.ecommerce.domain.core.authorization.userspermission.Userspermission;
import com.pepcap.ecommerce.domain.extended.authorization.permission.IPermissionRepositoryExtended;
import com.pepcap.ecommerce.domain.extended.authorization.users.IUsersRepositoryExtended;
import com.pepcap.ecommerce.domain.extended.orders.IOrdersRepositoryExtended;
import com.pepcap.ecommerce.application.extended.authorization.permission.PermissionAppServiceExtended;    
import com.pepcap.ecommerce.application.extended.authorization.users.UsersAppServiceExtended;    
import com.pepcap.ecommerce.security.JWTAppService;

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
public class UserspermissionControllerTestExtended extends DatabaseContainerConfig {
	
	@Autowired
	protected SortHandlerMethodArgumentResolver sortArgumentResolver;

	@Autowired
	@Qualifier("userspermissionRepositoryExtended") 
	protected IUserspermissionRepositoryExtended userspermission_repositoryExtended;
	
	@Autowired
	@Qualifier("permissionRepositoryExtended") 
	protected IPermissionRepositoryExtended permissionRepositoryExtended;
	
	@Autowired
	@Qualifier("usersRepositoryExtended") 
	protected IUsersRepositoryExtended usersRepositoryExtended;
	
	@Autowired
	@Qualifier("ordersRepositoryExtended") 
	protected IOrdersRepositoryExtended ordersRepositoryExtended;
	

	@SpyBean
	@Qualifier("userspermissionAppServiceExtended")
	protected UserspermissionAppServiceExtended userspermissionAppServiceExtended;
	
    @SpyBean
    @Qualifier("permissionAppServiceExtended")
	protected PermissionAppServiceExtended  permissionAppServiceExtended;
	
    @SpyBean
    @Qualifier("usersAppServiceExtended")
	protected UsersAppServiceExtended  usersAppServiceExtended;
	
	@SpyBean
	protected JWTAppService jwtAppService;
	
	@SpyBean
	protected LoggingHelper logHelper;

	@SpyBean
	protected Environment env;

	@Mock
	protected Logger loggerMock;

	protected Userspermission userspermission;

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
    
		final UserspermissionControllerExtended userspermissionController = new UserspermissionControllerExtended(userspermissionAppServiceExtended, permissionAppServiceExtended, usersAppServiceExtended,
	jwtAppService,logHelper,env);
		when(logHelper.getLogger()).thenReturn(loggerMock);
		doNothing().when(loggerMock).error(anyString());

		this.mvc = MockMvcBuilders.standaloneSetup(userspermissionController)
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
