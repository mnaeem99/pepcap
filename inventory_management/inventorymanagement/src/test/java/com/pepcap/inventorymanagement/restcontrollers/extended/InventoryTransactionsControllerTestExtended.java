package com.pepcap.inventorymanagement.restcontrollers.extended;

import com.pepcap.inventorymanagement.DatabaseContainerConfig;
import com.pepcap.inventorymanagement.commons.logging.LoggingHelper;
import com.pepcap.inventorymanagement.application.extended.inventorytransactions.InventoryTransactionsAppServiceExtended;
import com.pepcap.inventorymanagement.domain.extended.inventorytransactions.IInventoryTransactionsRepositoryExtended;
import com.pepcap.inventorymanagement.domain.core.inventorytransactions.InventoryTransactions;
import com.pepcap.inventorymanagement.domain.extended.products.IProductsRepositoryExtended;
import com.pepcap.inventorymanagement.domain.extended.categories.ICategoriesRepositoryExtended;
import com.pepcap.inventorymanagement.application.extended.products.ProductsAppServiceExtended;    

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
public class InventoryTransactionsControllerTestExtended extends DatabaseContainerConfig {
	
	@Autowired
	protected SortHandlerMethodArgumentResolver sortArgumentResolver;

	@Autowired
	@Qualifier("inventoryTransactionsRepositoryExtended") 
	protected IInventoryTransactionsRepositoryExtended inventoryTransactions_repositoryExtended;
	
	@Autowired
	@Qualifier("productsRepositoryExtended") 
	protected IProductsRepositoryExtended productsRepositoryExtended;
	
	@Autowired
	@Qualifier("categoriesRepositoryExtended") 
	protected ICategoriesRepositoryExtended categoriesRepositoryExtended;
	

	@SpyBean
	@Qualifier("inventoryTransactionsAppServiceExtended")
	protected InventoryTransactionsAppServiceExtended inventoryTransactionsAppServiceExtended;
	
    @SpyBean
    @Qualifier("productsAppServiceExtended")
	protected ProductsAppServiceExtended  productsAppServiceExtended;
	
	@SpyBean
	protected LoggingHelper logHelper;

	@SpyBean
	protected Environment env;

	@Mock
	protected Logger loggerMock;

	protected InventoryTransactions inventoryTransactions;

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
    
		final InventoryTransactionsControllerExtended inventoryTransactionsController = new InventoryTransactionsControllerExtended(inventoryTransactionsAppServiceExtended, productsAppServiceExtended,
	logHelper,env);
		when(logHelper.getLogger()).thenReturn(loggerMock);
		doNothing().when(loggerMock).error(anyString());

		this.mvc = MockMvcBuilders.standaloneSetup(inventoryTransactionsController)
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
