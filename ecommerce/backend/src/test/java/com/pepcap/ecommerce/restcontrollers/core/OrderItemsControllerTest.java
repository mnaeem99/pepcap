package com.pepcap.ecommerce.restcontrollers.core;

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
import com.pepcap.ecommerce.commons.logging.LoggingHelper;
import com.pepcap.ecommerce.commons.search.SearchUtils;
import com.pepcap.ecommerce.application.core.orderitems.OrderItemsAppService;
import com.pepcap.ecommerce.application.core.orderitems.dto.*;
import com.pepcap.ecommerce.domain.core.orderitems.IOrderItemsRepository;
import com.pepcap.ecommerce.domain.core.orderitems.OrderItems;

import com.pepcap.ecommerce.domain.core.orders.IOrdersRepository;
import com.pepcap.ecommerce.domain.core.orders.Orders;
import com.pepcap.ecommerce.domain.core.products.IProductsRepository;
import com.pepcap.ecommerce.domain.core.products.Products;
import com.pepcap.ecommerce.domain.core.categories.ICategoriesRepository;
import com.pepcap.ecommerce.domain.core.categories.Categories;
import com.pepcap.ecommerce.domain.core.authorization.users.IUsersRepository;
import com.pepcap.ecommerce.domain.core.authorization.users.Users;
import com.pepcap.ecommerce.application.core.orders.OrdersAppService;    
import com.pepcap.ecommerce.application.core.products.ProductsAppService;    
import com.pepcap.ecommerce.DatabaseContainerConfig;
import com.pepcap.ecommerce.domain.core.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(properties = "spring.profiles.active=test")
public class OrderItemsControllerTest extends DatabaseContainerConfig{
	
	@Autowired
	protected SortHandlerMethodArgumentResolver sortArgumentResolver;

	@Autowired
	@Qualifier("orderItemsRepository") 
	protected IOrderItemsRepository orderItems_repository;
	
	@Autowired
	@Qualifier("ordersRepository") 
	protected IOrdersRepository ordersRepository;
	
	@Autowired
	@Qualifier("productsRepository") 
	protected IProductsRepository productsRepository;
	
	@Autowired
	@Qualifier("categoriesRepository") 
	protected ICategoriesRepository categoriesRepository;
	
	@Autowired
	@Qualifier("usersRepository") 
	protected IUsersRepository usersRepository;
	

	@SpyBean
	@Qualifier("orderItemsAppService")
	protected OrderItemsAppService orderItemsAppService;
	
    @SpyBean
    @Qualifier("ordersAppService")
	protected OrdersAppService  ordersAppService;
	
    @SpyBean
    @Qualifier("productsAppService")
	protected ProductsAppService  productsAppService;
	
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
    
    static int relationCount = 10;
    static int yearCount = 1971;
    static int dayCount = 10;
	private BigDecimal bigdec = new BigDecimal(1.2);
    
	int countOrders = 10;
	
	int countProducts = 10;
	
	int countCategories = 10;
	
	int countUsers = 10;
	
	@PostConstruct
	public void init() {
	emfs = emf;
	}

	@AfterClass
	public static void cleanup() {
		EntityManager em = emfs.createEntityManager();
		em.getTransaction().begin();
		em.createNativeQuery("truncate table e_commerce.order_items CASCADE").executeUpdate();
		em.createNativeQuery("truncate table e_commerce.orders CASCADE").executeUpdate();
		em.createNativeQuery("truncate table e_commerce.products CASCADE").executeUpdate();
		em.createNativeQuery("truncate table e_commerce.categories CASCADE").executeUpdate();
		em.createNativeQuery("truncate table e_commerce.users CASCADE").executeUpdate();
		em.getTransaction().commit();
	}
	
	public Orders createOrdersEntity() {
	
		if(countOrders>60) {
			countOrders = 10;
		}

		if(dayCount>=31) {
			dayCount = 10;
			yearCount++;
		}
		
		Orders ordersEntity = new Orders();
		
		ordersEntity.setCreatedAt(SearchUtils.stringToLocalDateTime(yearCount+"-09-"+dayCount+" 05:25:22"));
		ordersEntity.setId(relationCount);
  		ordersEntity.setStatus(String.valueOf(relationCount));
		ordersEntity.setTotalAmount(bigdec);
		bigdec = bigdec.add(BigDecimal.valueOf(1.2D));
		ordersEntity.setVersiono(0L);
		relationCount++;
		Users users= createUsersEntity();
		ordersEntity.setUsers(users);
		if(!ordersRepository.findAll().contains(ordersEntity))
		{
			 ordersEntity = ordersRepository.save(ordersEntity);
		}
		countOrders++;
	    return ordersEntity;
	}
	public Products createProductsEntity() {
	
		if(countProducts>60) {
			countProducts = 10;
		}

		if(dayCount>=31) {
			dayCount = 10;
			yearCount++;
		}
		
		Products productsEntity = new Products();
		
		productsEntity.setCreatedAt(SearchUtils.stringToLocalDateTime(yearCount+"-09-"+dayCount+" 05:25:22"));
  		productsEntity.setDescription(String.valueOf(relationCount));
		productsEntity.setId(relationCount);
  		productsEntity.setName(String.valueOf(relationCount));
		productsEntity.setPrice(bigdec);
		bigdec = bigdec.add(BigDecimal.valueOf(1.2D));
		productsEntity.setStock(relationCount);
		productsEntity.setUpdatedAt(SearchUtils.stringToLocalDateTime(yearCount+"-09-"+dayCount+" 05:25:22"));
		productsEntity.setVersiono(0L);
		relationCount++;
		Categories categories= createCategoriesEntity();
		productsEntity.setCategories(categories);
		if(!productsRepository.findAll().contains(productsEntity))
		{
			 productsEntity = productsRepository.save(productsEntity);
		}
		countProducts++;
	    return productsEntity;
	}
	public Categories createCategoriesEntity() {
	
		if(countCategories>60) {
			countCategories = 10;
		}

		if(dayCount>=31) {
			dayCount = 10;
			yearCount++;
		}
		
		Categories categoriesEntity = new Categories();
		
		categoriesEntity.setCreatedAt(SearchUtils.stringToLocalDateTime(yearCount+"-09-"+dayCount+" 05:25:22"));
  		categoriesEntity.setDescription(String.valueOf(relationCount));
		categoriesEntity.setId(relationCount);
  		categoriesEntity.setName(String.valueOf(relationCount));
		categoriesEntity.setUpdatedAt(SearchUtils.stringToLocalDateTime(yearCount+"-09-"+dayCount+" 05:25:22"));
		categoriesEntity.setVersiono(0L);
		relationCount++;
		if(!categoriesRepository.findAll().contains(categoriesEntity))
		{
			 categoriesEntity = categoriesRepository.save(categoriesEntity);
		}
		countCategories++;
	    return categoriesEntity;
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

	public OrderItems createEntity() {
		Orders orders = createOrdersEntity();
		Products products = createProductsEntity();
	
		OrderItems orderItemsEntity = new OrderItems();
		orderItemsEntity.setId(1);
    	orderItemsEntity.setPrice(new BigDecimal("1.1"));
		orderItemsEntity.setQuantity(1);
		orderItemsEntity.setVersiono(0L);
		orderItemsEntity.setOrders(orders);
		orderItemsEntity.setProducts(products);
		
		return orderItemsEntity;
	}
    public CreateOrderItemsInput createOrderItemsInput() {
	
	    CreateOrderItemsInput orderItemsInput = new CreateOrderItemsInput();
    	orderItemsInput.setPrice(new BigDecimal("5.8"));
		orderItemsInput.setQuantity(5);
		
		return orderItemsInput;
	}

	public OrderItems createNewEntity() {
		OrderItems orderItems = new OrderItems();
		orderItems.setId(3);
    	orderItems.setPrice(new BigDecimal("3.3"));
		orderItems.setQuantity(3);
		
		return orderItems;
	}
	
	public OrderItems createUpdateEntity() {
		OrderItems orderItems = new OrderItems();
		orderItems.setId(4);
    	orderItems.setPrice(new BigDecimal("3.3"));
		orderItems.setQuantity(4);
		
		return orderItems;
	}
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
    
		final OrderItemsController orderItemsController = new OrderItemsController(orderItemsAppService, ordersAppService, productsAppService,
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

		orderItems= createEntity();
		List<OrderItems> list= orderItems_repository.findAll();
		if(!list.contains(orderItems)) {
			orderItems=orderItems_repository.save(orderItems);
		}

	}
	
	@Test
	public void FindById_IdIsValid_ReturnStatusOk() throws Exception {
	
		mvc.perform(get("/orderItems/" + orderItems.getId()+"/")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
	}  

	@Test
	public void FindById_IdIsNotValid_ReturnStatusNotFound() {

		 org.assertj.core.api.Assertions.assertThatThrownBy(() -> mvc.perform(get("/orderItems/999")
				.contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk())).hasCause(new EntityNotFoundException("Not found"));

	}
	@Test
	public void CreateOrderItems_OrderItemsDoesNotExist_ReturnStatusOk() throws Exception {
		CreateOrderItemsInput orderItemsInput = createOrderItemsInput();	
		
	    
		Orders orders =  createOrdersEntity();

		orderItemsInput.setOrderId(Integer.parseInt(orders.getId().toString()));
	    
		Products products =  createProductsEntity();

		orderItemsInput.setProductId(Integer.parseInt(products.getId().toString()));
		
		ObjectWriter ow = new ObjectMapper().registerModule(new JavaTimeModule()).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS).writer().withDefaultPrettyPrinter();
	
		String json = ow.writeValueAsString(orderItemsInput);

		mvc.perform(post("/orderItems").contentType(MediaType.APPLICATION_JSON).content(json))
		.andExpect(status().isOk());
	}     
	
	

	@Test
	public void DeleteOrderItems_IdIsNotValid_ThrowEntityNotFoundException() {

        doReturn(null).when(orderItemsAppService).findById(999);
        org.assertj.core.api.Assertions.assertThatThrownBy(() ->  mvc.perform(delete("/orderItems/999")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())).hasCause(new EntityNotFoundException("There does not exist a orderItems with a id=999"));

	}  

	@Test
	public void Delete_IdIsValid_ReturnStatusNoContent() throws Exception {
	
	 	OrderItems entity =  createNewEntity();
	 	entity.setVersiono(0L);
		Orders orders = createOrdersEntity();
		entity.setOrders(orders);
		Products products = createProductsEntity();
		entity.setProducts(products);
		entity = orderItems_repository.save(entity);
		

		FindOrderItemsByIdOutput output= new FindOrderItemsByIdOutput();
		output.setId(entity.getId());
		output.setPrice(entity.getPrice());
		output.setQuantity(entity.getQuantity());
		
         Mockito.doReturn(output).when(orderItemsAppService).findById(entity.getId());
       
    //    Mockito.when(orderItemsAppService.findById(entity.getId())).thenReturn(output);
        
		mvc.perform(delete("/orderItems/" + entity.getId()+"/")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isNoContent());
	}  


	@Test
	public void UpdateOrderItems_OrderItemsDoesNotExist_ReturnStatusNotFound() throws Exception {
   
        doReturn(null).when(orderItemsAppService).findById(999);
        
        UpdateOrderItemsInput orderItems = new UpdateOrderItemsInput();
		orderItems.setId(999);
		orderItems.setPrice(new BigDecimal("999"));
		orderItems.setQuantity(999);

		ObjectWriter ow = new ObjectMapper().registerModule(new JavaTimeModule()).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS).writer().withDefaultPrettyPrinter();
		String json = ow.writeValueAsString(orderItems);

		 org.assertj.core.api.Assertions.assertThatThrownBy(() -> mvc.perform(
		 	put("/orderItems/999")
		 	.contentType(MediaType.APPLICATION_JSON)
		 	.content(json))
			.andExpect(status().isOk())).hasCause(new EntityNotFoundException("Unable to update. OrderItems with id=999 not found."));
	}    

	@Test
	public void UpdateOrderItems_OrderItemsExists_ReturnStatusOk() throws Exception {
		OrderItems entity =  createUpdateEntity();
		entity.setVersiono(0L);
		
		Orders orders = createOrdersEntity();
		entity.setOrders(orders);
		Products products = createProductsEntity();
		entity.setProducts(products);
		entity = orderItems_repository.save(entity);
		FindOrderItemsByIdOutput output= new FindOrderItemsByIdOutput();
		output.setId(entity.getId());
		output.setPrice(entity.getPrice());
		output.setQuantity(entity.getQuantity());
		output.setVersiono(entity.getVersiono());
		
        Mockito.when(orderItemsAppService.findById(entity.getId())).thenReturn(output);
        
        
		
		UpdateOrderItemsInput orderItemsInput = new UpdateOrderItemsInput();
		orderItemsInput.setId(entity.getId());
		orderItemsInput.setPrice(entity.getPrice());
		orderItemsInput.setQuantity(entity.getQuantity());
		
		orderItemsInput.setOrderId(Integer.parseInt(orders.getId().toString()));
		orderItemsInput.setProductId(Integer.parseInt(products.getId().toString()));

		ObjectWriter ow = new ObjectMapper().registerModule(new JavaTimeModule()).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS).writer().withDefaultPrettyPrinter();
		String json = ow.writeValueAsString(orderItemsInput);
	
		mvc.perform(put("/orderItems/" + entity.getId()+"/").contentType(MediaType.APPLICATION_JSON).content(json))
		.andExpect(status().isOk());

		OrderItems de = createUpdateEntity();
		de.setId(entity.getId());
		orderItems_repository.delete(de);
		

	}    
	@Test
	public void FindAll_SearchIsNotNullAndPropertyIsValid_ReturnStatusOk() throws Exception {

		mvc.perform(get("/orderItems?search=id[equals]=1&limit=10&offset=1")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
	}
		
	
	@Test
	public void GetOrders_IdIsNotEmptyAndIdDoesNotExist_ReturnNotFound() {
  
	   org.assertj.core.api.Assertions.assertThatThrownBy(() ->  mvc.perform(get("/orderItems/999/orders")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())).hasCause(new EntityNotFoundException("Not found"));
	
	}    
	
	@Test
	public void GetOrders_searchIsNotEmptyAndPropertyIsValid_ReturnList() throws Exception {
	
	   mvc.perform(get("/orderItems/" + orderItems.getId()+ "/orders")
				.contentType(MediaType.APPLICATION_JSON))
	    		  .andExpect(status().isOk());
	}  
	
	
	@Test
	public void GetProducts_IdIsNotEmptyAndIdDoesNotExist_ReturnNotFound() {
  
	   org.assertj.core.api.Assertions.assertThatThrownBy(() ->  mvc.perform(get("/orderItems/999/products")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())).hasCause(new EntityNotFoundException("Not found"));
	
	}    
	
	@Test
	public void GetProducts_searchIsNotEmptyAndPropertyIsValid_ReturnList() throws Exception {
	
	   mvc.perform(get("/orderItems/" + orderItems.getId()+ "/products")
				.contentType(MediaType.APPLICATION_JSON))
	    		  .andExpect(status().isOk());
	}  
	
    
}

