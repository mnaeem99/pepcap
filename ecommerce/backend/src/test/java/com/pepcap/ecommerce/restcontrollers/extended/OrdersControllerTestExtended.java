package com.pepcap.ecommerce.restcontrollers.extended;

import com.pepcap.ecommerce.DatabaseContainerConfig;
import com.pepcap.ecommerce.commons.logging.LoggingHelper;
import com.pepcap.ecommerce.application.extended.orders.OrdersAppServiceExtended;
import com.pepcap.ecommerce.domain.extended.orders.IOrdersRepositoryExtended;
import com.pepcap.ecommerce.domain.core.orders.Orders;
import com.pepcap.ecommerce.domain.extended.authorization.users.IUsersRepositoryExtended;
import com.pepcap.ecommerce.domain.extended.orders.IOrdersRepositoryExtended;
import com.pepcap.ecommerce.domain.core.orders.Orders;
import com.pepcap.ecommerce.application.extended.orderitems.OrderItemsAppServiceExtended;    
import com.pepcap.ecommerce.application.extended.authorization.users.UsersAppServiceExtended;    

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
public class OrdersControllerTestExtended extends DatabaseContainerConfig {
	
	@Autowired
	protected SortHandlerMethodArgumentResolver sortArgumentResolver;

	@Autowired
	@Qualifier("ordersRepositoryExtended") 
	protected IOrdersRepositoryExtended orders_repositoryExtended;
	
	@Autowired
	@Qualifier("usersRepositoryExtended") 
	protected IUsersRepositoryExtended usersRepositoryExtended;
	
	@Autowired
	@Qualifier("ordersRepositoryExtended") 
	protected IOrdersRepositoryExtended ordersRepositoryExtended;
	

	@SpyBean
	@Qualifier("ordersAppServiceExtended")
	protected OrdersAppServiceExtended ordersAppServiceExtended;
	
    @SpyBean
    @Qualifier("orderItemsAppServiceExtended")
	protected OrderItemsAppServiceExtended  orderItemsAppServiceExtended;
	
    @SpyBean
    @Qualifier("usersAppServiceExtended")
	protected UsersAppServiceExtended  usersAppServiceExtended;
	
	@SpyBean
	protected LoggingHelper logHelper;

	@SpyBean
	protected Environment env;

	@Mock
	protected Logger loggerMock;

	protected Orders orders;

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
    
		final OrdersControllerExtended ordersController = new OrdersControllerExtended(ordersAppServiceExtended, orderItemsAppServiceExtended, usersAppServiceExtended,
	logHelper,env);
		when(logHelper.getLogger()).thenReturn(loggerMock);
		doNothing().when(loggerMock).error(anyString());

		this.mvc = MockMvcBuilders.standaloneSetup(ordersController)
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
