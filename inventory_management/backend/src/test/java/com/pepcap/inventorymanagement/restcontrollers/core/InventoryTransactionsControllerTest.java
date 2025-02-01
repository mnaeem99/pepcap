package com.pepcap.inventorymanagement.restcontrollers.core;

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
import com.pepcap.inventorymanagement.commons.logging.LoggingHelper;
import com.pepcap.inventorymanagement.commons.search.SearchUtils;
import com.pepcap.inventorymanagement.application.core.inventorytransactions.InventoryTransactionsAppService;
import com.pepcap.inventorymanagement.application.core.inventorytransactions.dto.*;
import com.pepcap.inventorymanagement.domain.core.inventorytransactions.IInventoryTransactionsRepository;
import com.pepcap.inventorymanagement.domain.core.inventorytransactions.InventoryTransactions;

import com.pepcap.inventorymanagement.domain.core.products.IProductsRepository;
import com.pepcap.inventorymanagement.domain.core.products.Products;
import com.pepcap.inventorymanagement.domain.core.categories.ICategoriesRepository;
import com.pepcap.inventorymanagement.domain.core.categories.Categories;
import com.pepcap.inventorymanagement.application.core.products.ProductsAppService;    
import com.pepcap.inventorymanagement.DatabaseContainerConfig;
import com.pepcap.inventorymanagement.domain.core.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(properties = "spring.profiles.active=test")
public class InventoryTransactionsControllerTest extends DatabaseContainerConfig{
	
	@Autowired
	protected SortHandlerMethodArgumentResolver sortArgumentResolver;

	@Autowired
	@Qualifier("inventoryTransactionsRepository") 
	protected IInventoryTransactionsRepository inventoryTransactions_repository;
	
	@Autowired
	@Qualifier("productsRepository") 
	protected IProductsRepository productsRepository;
	
	@Autowired
	@Qualifier("categoriesRepository") 
	protected ICategoriesRepository categoriesRepository;
	

	@SpyBean
	@Qualifier("inventoryTransactionsAppService")
	protected InventoryTransactionsAppService inventoryTransactionsAppService;
	
    @SpyBean
    @Qualifier("productsAppService")
	protected ProductsAppService  productsAppService;
	
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
    
    static int relationCount = 10;
    static int yearCount = 1971;
    static int dayCount = 10;
	private BigDecimal bigdec = new BigDecimal(1.2);
    
	int countProducts = 10;
	
	int countCategories = 10;
	
	@PostConstruct
	public void init() {
	emfs = emf;
	}

	@AfterClass
	public static void cleanup() {
		EntityManager em = emfs.createEntityManager();
		em.getTransaction().begin();
		em.createNativeQuery("truncate table inventory_management.inventory_transactions CASCADE").executeUpdate();
		em.createNativeQuery("truncate table inventory_management.products CASCADE").executeUpdate();
		em.createNativeQuery("truncate table inventory_management.categories CASCADE").executeUpdate();
		em.getTransaction().commit();
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

	public InventoryTransactions createEntity() {
		Products products = createProductsEntity();
	
		InventoryTransactions inventoryTransactionsEntity = new InventoryTransactions();
    	inventoryTransactionsEntity.setCreatedAt(SearchUtils.stringToLocalDateTime("1996-09-01 09:15:22"));
		inventoryTransactionsEntity.setId(1);
		inventoryTransactionsEntity.setQuantity(1);
		inventoryTransactionsEntity.setTransactionType("1");
		inventoryTransactionsEntity.setVersiono(0L);
		inventoryTransactionsEntity.setProducts(products);
		
		return inventoryTransactionsEntity;
	}
    public CreateInventoryTransactionsInput createInventoryTransactionsInput() {
	
	    CreateInventoryTransactionsInput inventoryTransactionsInput = new CreateInventoryTransactionsInput();
    	inventoryTransactionsInput.setCreatedAt(SearchUtils.stringToLocalDateTime("1996-08-10 05:25:22"));
		inventoryTransactionsInput.setQuantity(5);
  		inventoryTransactionsInput.setTransactionType("5");
		
		return inventoryTransactionsInput;
	}

	public InventoryTransactions createNewEntity() {
		InventoryTransactions inventoryTransactions = new InventoryTransactions();
    	inventoryTransactions.setCreatedAt(SearchUtils.stringToLocalDateTime("1996-08-11 05:35:22"));
		inventoryTransactions.setId(3);
		inventoryTransactions.setQuantity(3);
		inventoryTransactions.setTransactionType("3");
		
		return inventoryTransactions;
	}
	
	public InventoryTransactions createUpdateEntity() {
		InventoryTransactions inventoryTransactions = new InventoryTransactions();
    	inventoryTransactions.setCreatedAt(SearchUtils.stringToLocalDateTime("1996-09-09 05:45:22"));
		inventoryTransactions.setId(4);
		inventoryTransactions.setQuantity(4);
		inventoryTransactions.setTransactionType("4");
		
		return inventoryTransactions;
	}
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
    
		final InventoryTransactionsController inventoryTransactionsController = new InventoryTransactionsController(inventoryTransactionsAppService, productsAppService,
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

		inventoryTransactions= createEntity();
		List<InventoryTransactions> list= inventoryTransactions_repository.findAll();
		if(!list.contains(inventoryTransactions)) {
			inventoryTransactions=inventoryTransactions_repository.save(inventoryTransactions);
		}

	}
	
	@Test
	public void FindById_IdIsValid_ReturnStatusOk() throws Exception {
	
		mvc.perform(get("/inventoryTransactions/" + inventoryTransactions.getId()+"/")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
	}  

	@Test
	public void FindById_IdIsNotValid_ReturnStatusNotFound() {

		 org.assertj.core.api.Assertions.assertThatThrownBy(() -> mvc.perform(get("/inventoryTransactions/999")
				.contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk())).hasCause(new EntityNotFoundException("Not found"));

	}
	@Test
	public void CreateInventoryTransactions_InventoryTransactionsDoesNotExist_ReturnStatusOk() throws Exception {
		CreateInventoryTransactionsInput inventoryTransactionsInput = createInventoryTransactionsInput();	
		
	    
		Products products =  createProductsEntity();

		inventoryTransactionsInput.setProductId(Integer.parseInt(products.getId().toString()));
		
		ObjectWriter ow = new ObjectMapper().registerModule(new JavaTimeModule()).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS).writer().withDefaultPrettyPrinter();
	
		String json = ow.writeValueAsString(inventoryTransactionsInput);

		mvc.perform(post("/inventoryTransactions").contentType(MediaType.APPLICATION_JSON).content(json))
		.andExpect(status().isOk());
	}     
	

	@Test
	public void DeleteInventoryTransactions_IdIsNotValid_ThrowEntityNotFoundException() {

        doReturn(null).when(inventoryTransactionsAppService).findById(999);
        org.assertj.core.api.Assertions.assertThatThrownBy(() ->  mvc.perform(delete("/inventoryTransactions/999")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())).hasCause(new EntityNotFoundException("There does not exist a inventoryTransactions with a id=999"));

	}  

	@Test
	public void Delete_IdIsValid_ReturnStatusNoContent() throws Exception {
	
	 	InventoryTransactions entity =  createNewEntity();
	 	entity.setVersiono(0L);
		Products products = createProductsEntity();
		entity.setProducts(products);
		entity = inventoryTransactions_repository.save(entity);
		

		FindInventoryTransactionsByIdOutput output= new FindInventoryTransactionsByIdOutput();
		output.setId(entity.getId());
		output.setQuantity(entity.getQuantity());
		output.setTransactionType(entity.getTransactionType());
		
         Mockito.doReturn(output).when(inventoryTransactionsAppService).findById(entity.getId());
       
    //    Mockito.when(inventoryTransactionsAppService.findById(entity.getId())).thenReturn(output);
        
		mvc.perform(delete("/inventoryTransactions/" + entity.getId()+"/")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isNoContent());
	}  


	@Test
	public void UpdateInventoryTransactions_InventoryTransactionsDoesNotExist_ReturnStatusNotFound() throws Exception {
   
        doReturn(null).when(inventoryTransactionsAppService).findById(999);
        
        UpdateInventoryTransactionsInput inventoryTransactions = new UpdateInventoryTransactionsInput();
		inventoryTransactions.setCreatedAt(SearchUtils.stringToLocalDateTime("1996-09-28 07:15:22"));
		inventoryTransactions.setId(999);
		inventoryTransactions.setQuantity(999);
  		inventoryTransactions.setTransactionType("999");

		ObjectWriter ow = new ObjectMapper().registerModule(new JavaTimeModule()).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS).writer().withDefaultPrettyPrinter();
		String json = ow.writeValueAsString(inventoryTransactions);

		 org.assertj.core.api.Assertions.assertThatThrownBy(() -> mvc.perform(
		 	put("/inventoryTransactions/999")
		 	.contentType(MediaType.APPLICATION_JSON)
		 	.content(json))
			.andExpect(status().isOk())).hasCause(new EntityNotFoundException("Unable to update. InventoryTransactions with id=999 not found."));
	}    

	@Test
	public void UpdateInventoryTransactions_InventoryTransactionsExists_ReturnStatusOk() throws Exception {
		InventoryTransactions entity =  createUpdateEntity();
		entity.setVersiono(0L);
		
		Products products = createProductsEntity();
		entity.setProducts(products);
		entity = inventoryTransactions_repository.save(entity);
		FindInventoryTransactionsByIdOutput output= new FindInventoryTransactionsByIdOutput();
		output.setCreatedAt(entity.getCreatedAt());
		output.setId(entity.getId());
		output.setQuantity(entity.getQuantity());
		output.setTransactionType(entity.getTransactionType());
		output.setVersiono(entity.getVersiono());
		
        Mockito.when(inventoryTransactionsAppService.findById(entity.getId())).thenReturn(output);
        
        
		
		UpdateInventoryTransactionsInput inventoryTransactionsInput = new UpdateInventoryTransactionsInput();
		inventoryTransactionsInput.setId(entity.getId());
		inventoryTransactionsInput.setQuantity(entity.getQuantity());
		inventoryTransactionsInput.setTransactionType(entity.getTransactionType());
		
		inventoryTransactionsInput.setProductId(Integer.parseInt(products.getId().toString()));

		ObjectWriter ow = new ObjectMapper().registerModule(new JavaTimeModule()).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS).writer().withDefaultPrettyPrinter();
		String json = ow.writeValueAsString(inventoryTransactionsInput);
	
		mvc.perform(put("/inventoryTransactions/" + entity.getId()+"/").contentType(MediaType.APPLICATION_JSON).content(json))
		.andExpect(status().isOk());

		InventoryTransactions de = createUpdateEntity();
		de.setId(entity.getId());
		inventoryTransactions_repository.delete(de);
		

	}    
	@Test
	public void FindAll_SearchIsNotNullAndPropertyIsValid_ReturnStatusOk() throws Exception {

		mvc.perform(get("/inventoryTransactions?search=id[equals]=1&limit=10&offset=1")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
	}
		
	
	@Test
	public void GetProducts_IdIsNotEmptyAndIdDoesNotExist_ReturnNotFound() {
  
	   org.assertj.core.api.Assertions.assertThatThrownBy(() ->  mvc.perform(get("/inventoryTransactions/999/products")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())).hasCause(new EntityNotFoundException("Not found"));
	
	}    
	
	@Test
	public void GetProducts_searchIsNotEmptyAndPropertyIsValid_ReturnList() throws Exception {
	
	   mvc.perform(get("/inventoryTransactions/" + inventoryTransactions.getId()+ "/products")
				.contentType(MediaType.APPLICATION_JSON))
	    		  .andExpect(status().isOk());
	}  
	
    
}

