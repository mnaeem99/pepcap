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
import com.pepcap.ecommerce.application.core.orders.OrdersAppService;
import com.pepcap.ecommerce.application.core.orders.dto.*;
import com.pepcap.ecommerce.domain.core.orders.IOrdersRepository;
import com.pepcap.ecommerce.domain.core.orders.Orders;

import com.pepcap.ecommerce.domain.core.authorization.users.IUsersRepository;
import com.pepcap.ecommerce.domain.core.authorization.users.Users;
import com.pepcap.ecommerce.domain.core.orders.IOrdersRepository;
import com.pepcap.ecommerce.domain.core.orders.Orders;
import com.pepcap.ecommerce.application.core.orderitems.OrderItemsAppService;    
import com.pepcap.ecommerce.application.core.authorization.users.UsersAppService;    
import com.pepcap.ecommerce.DatabaseContainerConfig;
import com.pepcap.ecommerce.domain.core.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(properties = "spring.profiles.active=test")
public class OrdersControllerTest extends DatabaseContainerConfig{
	
	@Autowired
	protected SortHandlerMethodArgumentResolver sortArgumentResolver;

	@Autowired
	@Qualifier("ordersRepository") 
	protected IOrdersRepository orders_repository;
	
	@Autowired
	@Qualifier("usersRepository") 
	protected IUsersRepository usersRepository;
	
	@Autowired
	@Qualifier("ordersRepository") 
	protected IOrdersRepository ordersRepository;
	

	@SpyBean
	@Qualifier("ordersAppService")
	protected OrdersAppService ordersAppService;
	
    @SpyBean
    @Qualifier("orderItemsAppService")
	protected OrderItemsAppService  orderItemsAppService;
	
    @SpyBean
    @Qualifier("usersAppService")
	protected UsersAppService  usersAppService;
	
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
    
    static int relationCount = 10;
    static int yearCount = 1971;
    static int dayCount = 10;
	private BigDecimal bigdec = new BigDecimal(1.2);
    
	int countOrders = 10;
	
	int countUsers = 10;
	
	@PostConstruct
	public void init() {
	emfs = emf;
	}

	@AfterClass
	public static void cleanup() {
		EntityManager em = emfs.createEntityManager();
		em.getTransaction().begin();
		em.createNativeQuery("truncate table e_commerce.orders CASCADE").executeUpdate();
		em.createNativeQuery("truncate table e_commerce.users CASCADE").executeUpdate();
		em.createNativeQuery("truncate table e_commerce.orders CASCADE").executeUpdate();
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

	public Orders createEntity() {
		Users users = createUsersEntity();
	
		Orders ordersEntity = new Orders();
    	ordersEntity.setCreatedAt(SearchUtils.stringToLocalDateTime("1996-09-01 09:15:22"));
		ordersEntity.setId(1);
		ordersEntity.setStatus("1");
    	ordersEntity.setTotalAmount(new BigDecimal("1.1"));
		ordersEntity.setVersiono(0L);
		ordersEntity.setUsers(users);
		
		return ordersEntity;
	}
    public CreateOrdersInput createOrdersInput() {
	
	    CreateOrdersInput ordersInput = new CreateOrdersInput();
    	ordersInput.setCreatedAt(SearchUtils.stringToLocalDateTime("1996-08-10 05:25:22"));
  		ordersInput.setStatus("5");
    	ordersInput.setTotalAmount(new BigDecimal("5.8"));
		
		return ordersInput;
	}

	public Orders createNewEntity() {
		Orders orders = new Orders();
    	orders.setCreatedAt(SearchUtils.stringToLocalDateTime("1996-08-11 05:35:22"));
		orders.setId(3);
		orders.setStatus("3");
    	orders.setTotalAmount(new BigDecimal("3.3"));
		
		return orders;
	}
	
	public Orders createUpdateEntity() {
		Orders orders = new Orders();
    	orders.setCreatedAt(SearchUtils.stringToLocalDateTime("1996-09-09 05:45:22"));
		orders.setId(4);
		orders.setStatus("4");
    	orders.setTotalAmount(new BigDecimal("3.3"));
		
		return orders;
	}
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
    
		final OrdersController ordersController = new OrdersController(ordersAppService, orderItemsAppService, usersAppService,
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

		orders= createEntity();
		List<Orders> list= orders_repository.findAll();
		if(!list.contains(orders)) {
			orders=orders_repository.save(orders);
		}

	}
	
	@Test
	public void FindById_IdIsValid_ReturnStatusOk() throws Exception {
	
		mvc.perform(get("/orders/" + orders.getId()+"/")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
	}  

	@Test
	public void FindById_IdIsNotValid_ReturnStatusNotFound() {

		 org.assertj.core.api.Assertions.assertThatThrownBy(() -> mvc.perform(get("/orders/999")
				.contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk())).hasCause(new EntityNotFoundException("Not found"));

	}
	@Test
	public void CreateOrders_OrdersDoesNotExist_ReturnStatusOk() throws Exception {
		CreateOrdersInput ordersInput = createOrdersInput();	
		
	    
		Users users =  createUsersEntity();

		ordersInput.setUserId(Integer.parseInt(users.getId().toString()));
		
		ObjectWriter ow = new ObjectMapper().registerModule(new JavaTimeModule()).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS).writer().withDefaultPrettyPrinter();
	
		String json = ow.writeValueAsString(ordersInput);

		mvc.perform(post("/orders").contentType(MediaType.APPLICATION_JSON).content(json))
		.andExpect(status().isOk());
	}     
	
	

	@Test
	public void DeleteOrders_IdIsNotValid_ThrowEntityNotFoundException() {

        doReturn(null).when(ordersAppService).findById(999);
        org.assertj.core.api.Assertions.assertThatThrownBy(() ->  mvc.perform(delete("/orders/999")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())).hasCause(new EntityNotFoundException("There does not exist a orders with a id=999"));

	}  

	@Test
	public void Delete_IdIsValid_ReturnStatusNoContent() throws Exception {
	
	 	Orders entity =  createNewEntity();
	 	entity.setVersiono(0L);
		Users users = createUsersEntity();
		entity.setUsers(users);
		entity = orders_repository.save(entity);
		

		FindOrdersByIdOutput output= new FindOrdersByIdOutput();
		output.setId(entity.getId());
		output.setTotalAmount(entity.getTotalAmount());
		
         Mockito.doReturn(output).when(ordersAppService).findById(entity.getId());
       
    //    Mockito.when(ordersAppService.findById(entity.getId())).thenReturn(output);
        
		mvc.perform(delete("/orders/" + entity.getId()+"/")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isNoContent());
	}  


	@Test
	public void UpdateOrders_OrdersDoesNotExist_ReturnStatusNotFound() throws Exception {
   
        doReturn(null).when(ordersAppService).findById(999);
        
        UpdateOrdersInput orders = new UpdateOrdersInput();
		orders.setCreatedAt(SearchUtils.stringToLocalDateTime("1996-09-28 07:15:22"));
		orders.setId(999);
  		orders.setStatus("999");
		orders.setTotalAmount(new BigDecimal("999"));

		ObjectWriter ow = new ObjectMapper().registerModule(new JavaTimeModule()).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS).writer().withDefaultPrettyPrinter();
		String json = ow.writeValueAsString(orders);

		 org.assertj.core.api.Assertions.assertThatThrownBy(() -> mvc.perform(
		 	put("/orders/999")
		 	.contentType(MediaType.APPLICATION_JSON)
		 	.content(json))
			.andExpect(status().isOk())).hasCause(new EntityNotFoundException("Unable to update. Orders with id=999 not found."));
	}    

	@Test
	public void UpdateOrders_OrdersExists_ReturnStatusOk() throws Exception {
		Orders entity =  createUpdateEntity();
		entity.setVersiono(0L);
		
		Users users = createUsersEntity();
		entity.setUsers(users);
		entity = orders_repository.save(entity);
		FindOrdersByIdOutput output= new FindOrdersByIdOutput();
		output.setCreatedAt(entity.getCreatedAt());
		output.setId(entity.getId());
		output.setStatus(entity.getStatus());
		output.setTotalAmount(entity.getTotalAmount());
		output.setVersiono(entity.getVersiono());
		
        Mockito.when(ordersAppService.findById(entity.getId())).thenReturn(output);
        
        
		
		UpdateOrdersInput ordersInput = new UpdateOrdersInput();
		ordersInput.setId(entity.getId());
		ordersInput.setTotalAmount(entity.getTotalAmount());
		
		ordersInput.setUserId(Integer.parseInt(users.getId().toString()));

		ObjectWriter ow = new ObjectMapper().registerModule(new JavaTimeModule()).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS).writer().withDefaultPrettyPrinter();
		String json = ow.writeValueAsString(ordersInput);
	
		mvc.perform(put("/orders/" + entity.getId()+"/").contentType(MediaType.APPLICATION_JSON).content(json))
		.andExpect(status().isOk());

		Orders de = createUpdateEntity();
		de.setId(entity.getId());
		orders_repository.delete(de);
		

	}    
	@Test
	public void FindAll_SearchIsNotNullAndPropertyIsValid_ReturnStatusOk() throws Exception {

		mvc.perform(get("/orders?search=id[equals]=1&limit=10&offset=1")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
	}
		
	
	
	@Test
	public void GetOrderItems_searchIsNotEmptyAndPropertyIsValid_ReturnList() throws Exception {

		Map<String,String> joinCol = new HashMap<String,String>();
		joinCol.put("id", "1");
		
        Mockito.when(ordersAppService.parseOrderItemsJoinColumn("orderId")).thenReturn(joinCol);
		mvc.perform(get("/orders/1/orderItems?search=orderId[equals]=1&limit=10&offset=1")
				.contentType(MediaType.APPLICATION_JSON))
	    		  .andExpect(status().isOk());
	}  
	
	@Test
	public void GetOrderItems_searchIsNotEmpty() {
	
		Mockito.when(ordersAppService.parseOrderItemsJoinColumn(anyString())).thenReturn(null);
	 		  		    		  
	    org.assertj.core.api.Assertions.assertThatThrownBy(() -> mvc.perform(get("/orders/1/orderItems?search=orderId[equals]=1&limit=10&offset=1")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())).hasCause(new EntityNotFoundException("Invalid join column"));
	}    
	
	@Test
	public void GetUsers_IdIsNotEmptyAndIdDoesNotExist_ReturnNotFound() {
  
	   org.assertj.core.api.Assertions.assertThatThrownBy(() ->  mvc.perform(get("/orders/999/users")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())).hasCause(new EntityNotFoundException("Not found"));
	
	}    
	
	@Test
	public void GetUsers_searchIsNotEmptyAndPropertyIsValid_ReturnList() throws Exception {
	
	   mvc.perform(get("/orders/" + orders.getId()+ "/users")
				.contentType(MediaType.APPLICATION_JSON))
	    		  .andExpect(status().isOk());
	}  
	
    
}

