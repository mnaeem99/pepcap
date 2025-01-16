package com.pepcap.adminpanel.restcontrollers.core;

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
import com.pepcap.adminpanel.commons.logging.LoggingHelper;
import com.pepcap.adminpanel.commons.search.SearchUtils;
import com.pepcap.adminpanel.application.core.products.ProductsAppService;
import com.pepcap.adminpanel.application.core.products.dto.*;
import com.pepcap.adminpanel.domain.core.products.IProductsRepository;
import com.pepcap.adminpanel.domain.core.products.Products;

import com.pepcap.adminpanel.domain.core.categories.ICategoriesRepository;
import com.pepcap.adminpanel.domain.core.categories.Categories;
import com.pepcap.adminpanel.application.core.categories.CategoriesAppService;    
import com.pepcap.adminpanel.DatabaseContainerConfig;
import com.pepcap.adminpanel.domain.core.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(properties = "spring.profiles.active=test")
public class ProductsControllerTest extends DatabaseContainerConfig{
	
	@Autowired
	protected SortHandlerMethodArgumentResolver sortArgumentResolver;

	@Autowired
	@Qualifier("productsRepository") 
	protected IProductsRepository products_repository;
	
	@Autowired
	@Qualifier("categoriesRepository") 
	protected ICategoriesRepository categoriesRepository;
	

	@SpyBean
	@Qualifier("productsAppService")
	protected ProductsAppService productsAppService;
	
    @SpyBean
    @Qualifier("categoriesAppService")
	protected CategoriesAppService  categoriesAppService;
	
	@SpyBean
	protected LoggingHelper logHelper;

	@SpyBean
	protected Environment env;

	@Mock
	protected Logger loggerMock;

	protected Products products;

	protected MockMvc mvc;
	
	@Autowired
	EntityManagerFactory emf;
	
    static EntityManagerFactory emfs;
    
    static int relationCount = 10;
    static int yearCount = 1971;
    static int dayCount = 10;
	private BigDecimal bigdec = new BigDecimal(1.2);
    
	int countCategories = 10;
	
	@PostConstruct
	public void init() {
	emfs = emf;
	}

	@AfterClass
	public static void cleanup() {
		EntityManager em = emfs.createEntityManager();
		em.getTransaction().begin();
		em.createNativeQuery("truncate table admin_panel.products CASCADE").executeUpdate();
		em.createNativeQuery("truncate table admin_panel.categories CASCADE").executeUpdate();
		em.getTransaction().commit();
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

	public Products createEntity() {
		Categories categories = createCategoriesEntity();
	
		Products productsEntity = new Products();
    	productsEntity.setCreatedAt(SearchUtils.stringToLocalDateTime("1996-09-01 09:15:22"));
		productsEntity.setDescription("1");
		productsEntity.setId(1);
		productsEntity.setName("1");
    	productsEntity.setPrice(new BigDecimal("1.1"));
		productsEntity.setStock(1);
    	productsEntity.setUpdatedAt(SearchUtils.stringToLocalDateTime("1996-09-01 09:15:22"));
		productsEntity.setVersiono(0L);
		productsEntity.setCategories(categories);
		
		return productsEntity;
	}
    public CreateProductsInput createProductsInput() {
	
	    CreateProductsInput productsInput = new CreateProductsInput();
    	productsInput.setCreatedAt(SearchUtils.stringToLocalDateTime("1996-08-10 05:25:22"));
  		productsInput.setDescription("5");
  		productsInput.setName("5");
    	productsInput.setPrice(new BigDecimal("5.8"));
		productsInput.setStock(5);
    	productsInput.setUpdatedAt(SearchUtils.stringToLocalDateTime("1996-08-10 05:25:22"));
		
		return productsInput;
	}

	public Products createNewEntity() {
		Products products = new Products();
    	products.setCreatedAt(SearchUtils.stringToLocalDateTime("1996-08-11 05:35:22"));
		products.setDescription("3");
		products.setId(3);
		products.setName("3");
    	products.setPrice(new BigDecimal("3.3"));
		products.setStock(3);
    	products.setUpdatedAt(SearchUtils.stringToLocalDateTime("1996-08-11 05:35:22"));
		
		return products;
	}
	
	public Products createUpdateEntity() {
		Products products = new Products();
    	products.setCreatedAt(SearchUtils.stringToLocalDateTime("1996-09-09 05:45:22"));
		products.setDescription("4");
		products.setId(4);
		products.setName("4");
    	products.setPrice(new BigDecimal("3.3"));
		products.setStock(4);
    	products.setUpdatedAt(SearchUtils.stringToLocalDateTime("1996-09-09 05:45:22"));
		
		return products;
	}
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
    
		final ProductsController productsController = new ProductsController(productsAppService, categoriesAppService,
		logHelper,env);
		when(logHelper.getLogger()).thenReturn(loggerMock);
		doNothing().when(loggerMock).error(anyString());

		this.mvc = MockMvcBuilders.standaloneSetup(productsController)
				.setCustomArgumentResolvers(sortArgumentResolver)
				.setControllerAdvice()
				.build();
	}

	@Before
	public void initTest() {

		products= createEntity();
		List<Products> list= products_repository.findAll();
		if(!list.contains(products)) {
			products=products_repository.save(products);
		}

	}
	
	@Test
	public void FindById_IdIsValid_ReturnStatusOk() throws Exception {
	
		mvc.perform(get("/products/" + products.getId()+"/")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
	}  

	@Test
	public void FindById_IdIsNotValid_ReturnStatusNotFound() {

		 org.assertj.core.api.Assertions.assertThatThrownBy(() -> mvc.perform(get("/products/999")
				.contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk())).hasCause(new EntityNotFoundException("Not found"));

	}
	@Test
	public void CreateProducts_ProductsDoesNotExist_ReturnStatusOk() throws Exception {
		CreateProductsInput productsInput = createProductsInput();	
		
	    
		Categories categories =  createCategoriesEntity();

		productsInput.setCategoryId(Integer.parseInt(categories.getId().toString()));
		
		ObjectWriter ow = new ObjectMapper().registerModule(new JavaTimeModule()).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS).writer().withDefaultPrettyPrinter();
	
		String json = ow.writeValueAsString(productsInput);

		mvc.perform(post("/products").contentType(MediaType.APPLICATION_JSON).content(json))
		.andExpect(status().isOk());
	}     
	

	@Test
	public void DeleteProducts_IdIsNotValid_ThrowEntityNotFoundException() {

        doReturn(null).when(productsAppService).findById(999);
        org.assertj.core.api.Assertions.assertThatThrownBy(() ->  mvc.perform(delete("/products/999")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())).hasCause(new EntityNotFoundException("There does not exist a products with a id=999"));

	}  

	@Test
	public void Delete_IdIsValid_ReturnStatusNoContent() throws Exception {
	
	 	Products entity =  createNewEntity();
	 	entity.setVersiono(0L);
		Categories categories = createCategoriesEntity();
		entity.setCategories(categories);
		entity = products_repository.save(entity);
		

		FindProductsByIdOutput output= new FindProductsByIdOutput();
		output.setId(entity.getId());
		output.setName(entity.getName());
		output.setPrice(entity.getPrice());
		
         Mockito.doReturn(output).when(productsAppService).findById(entity.getId());
       
    //    Mockito.when(productsAppService.findById(entity.getId())).thenReturn(output);
        
		mvc.perform(delete("/products/" + entity.getId()+"/")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isNoContent());
	}  


	@Test
	public void UpdateProducts_ProductsDoesNotExist_ReturnStatusNotFound() throws Exception {
   
        doReturn(null).when(productsAppService).findById(999);
        
        UpdateProductsInput products = new UpdateProductsInput();
		products.setCreatedAt(SearchUtils.stringToLocalDateTime("1996-09-28 07:15:22"));
  		products.setDescription("999");
		products.setId(999);
  		products.setName("999");
		products.setPrice(new BigDecimal("999"));
		products.setStock(999);
		products.setUpdatedAt(SearchUtils.stringToLocalDateTime("1996-09-28 07:15:22"));

		ObjectWriter ow = new ObjectMapper().registerModule(new JavaTimeModule()).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS).writer().withDefaultPrettyPrinter();
		String json = ow.writeValueAsString(products);

		 org.assertj.core.api.Assertions.assertThatThrownBy(() -> mvc.perform(
		 	put("/products/999")
		 	.contentType(MediaType.APPLICATION_JSON)
		 	.content(json))
			.andExpect(status().isOk())).hasCause(new EntityNotFoundException("Unable to update. Products with id=999 not found."));
	}    

	@Test
	public void UpdateProducts_ProductsExists_ReturnStatusOk() throws Exception {
		Products entity =  createUpdateEntity();
		entity.setVersiono(0L);
		
		Categories categories = createCategoriesEntity();
		entity.setCategories(categories);
		entity = products_repository.save(entity);
		FindProductsByIdOutput output= new FindProductsByIdOutput();
		output.setCreatedAt(entity.getCreatedAt());
		output.setDescription(entity.getDescription());
		output.setId(entity.getId());
		output.setName(entity.getName());
		output.setPrice(entity.getPrice());
		output.setStock(entity.getStock());
		output.setUpdatedAt(entity.getUpdatedAt());
		output.setVersiono(entity.getVersiono());
		
        Mockito.when(productsAppService.findById(entity.getId())).thenReturn(output);
        
        
		
		UpdateProductsInput productsInput = new UpdateProductsInput();
		productsInput.setId(entity.getId());
		productsInput.setName(entity.getName());
		productsInput.setPrice(entity.getPrice());
		
		productsInput.setCategoryId(Integer.parseInt(categories.getId().toString()));

		ObjectWriter ow = new ObjectMapper().registerModule(new JavaTimeModule()).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS).writer().withDefaultPrettyPrinter();
		String json = ow.writeValueAsString(productsInput);
	
		mvc.perform(put("/products/" + entity.getId()+"/").contentType(MediaType.APPLICATION_JSON).content(json))
		.andExpect(status().isOk());

		Products de = createUpdateEntity();
		de.setId(entity.getId());
		products_repository.delete(de);
		

	}    
	@Test
	public void FindAll_SearchIsNotNullAndPropertyIsValid_ReturnStatusOk() throws Exception {

		mvc.perform(get("/products?search=id[equals]=1&limit=10&offset=1")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
	}
		
	
	@Test
	public void GetCategories_IdIsNotEmptyAndIdDoesNotExist_ReturnNotFound() {
  
	   org.assertj.core.api.Assertions.assertThatThrownBy(() ->  mvc.perform(get("/products/999/categories")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())).hasCause(new EntityNotFoundException("Not found"));
	
	}    
	
	@Test
	public void GetCategories_searchIsNotEmptyAndPropertyIsValid_ReturnList() throws Exception {
	
	   mvc.perform(get("/products/" + products.getId()+ "/categories")
				.contentType(MediaType.APPLICATION_JSON))
	    		  .andExpect(status().isOk());
	}  
	
    
}

