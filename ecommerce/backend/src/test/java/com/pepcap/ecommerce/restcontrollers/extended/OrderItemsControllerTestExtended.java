package com.pepcap.ecommerce.restcontrollers.extended;

import com.pepcap.ecommerce.DatabaseContainerConfig;
import com.pepcap.ecommerce.commons.logging.LoggingHelper;
import com.pepcap.ecommerce.application.extended.orderitems.OrderItemsAppServiceExtended;
import com.pepcap.ecommerce.domain.extended.orderitems.IOrderItemsRepositoryExtended;
import com.pepcap.ecommerce.domain.core.orderitems.OrderItems;
import com.pepcap.ecommerce.domain.extended.orders.IOrdersRepositoryExtended;
import com.pepcap.ecommerce.domain.extended.products.IProductsRepositoryExtended;
import com.pepcap.ecommerce.domain.extended.categories.ICategoriesRepositoryExtended;
import com.pepcap.ecommerce.domain.extended.authorization.users.IUsersRepositoryExtended;
import com.pepcap.ecommerce.application.extended.orders.OrdersAppServiceExtended;    
import com.pepcap.ecommerce.application.extended.products.ProductsAppServiceExtended;    

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
public class OrderItemsControllerTestExtended extends DatabaseContainerConfig {
	
	@Autowired
	protected SortHandlerMethodArgumentResolver sortArgumentResolver;

	@Autowired
	@Qualifier("orderItemsRepositoryExtended") 
	protected IOrderItemsRepositoryExtended orderItems_repositoryExtended;
	
	@Autowired
	@Qualifier("ordersRepositoryExtended") 
	protected IOrdersRepositoryExtended ordersRepositoryExtended;
	
	@Autowired
	@Qualifier("productsRepositoryExtended") 
	protected IProductsRepositoryExtended productsRepositoryExtended;
	
	@Autowired
	@Qualifier("categoriesRepositoryExtended") 
	protected ICategoriesRepositoryExtended categoriesRepositoryExtended;
	
	@Autowired
	@Qualifier("usersRepositoryExtended") 
	protected IUsersRepositoryExtended usersRepositoryExtended;
	

	@SpyBean
	@Qualifier("orderItemsAppServiceExtended")
	protected OrderItemsAppServiceExtended orderItemsAppServiceExtended;
	
    @SpyBean
    @Qualifier("ordersAppServiceExtended")
	protected OrdersAppServiceExtended  ordersAppServiceExtended;
	
    @SpyBean
    @Qualifier("productsAppServiceExtended")
	protected ProductsAppServiceExtended  productsAppServiceExtended;
	
	@SpyBean
	protected LoggingHelper logHelper;

	@SpyBean
	protected Environment env;

	@Mock
	protected Logger loggerMock;

	protected OrderItems orderItems;

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
    
		final OrderItemsControllerExtended orderItemsController = new OrderItemsControllerExtended(orderItemsAppServiceExtended, ordersAppServiceExtended, productsAppServiceExtended,
	logHelper,env);
		when(logHelper.getLogger()).thenReturn(loggerMock);
		doNothing().when(loggerMock).error(anyString());

		this.mvc = MockMvcBuilders.standaloneSetup(orderItemsController)
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
