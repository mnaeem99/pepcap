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
import com.pepcap.ecommerce.application.core.categories.CategoriesAppService;
import com.pepcap.ecommerce.application.core.categories.dto.*;
import com.pepcap.ecommerce.domain.core.categories.ICategoriesRepository;
import com.pepcap.ecommerce.domain.core.categories.Categories;

import com.pepcap.ecommerce.domain.core.products.IProductsRepository;
import com.pepcap.ecommerce.domain.core.products.Products;
import com.pepcap.ecommerce.domain.core.categories.ICategoriesRepository;
import com.pepcap.ecommerce.domain.core.categories.Categories;
import com.pepcap.ecommerce.application.core.products.ProductsAppService;    
import com.pepcap.ecommerce.DatabaseContainerConfig;
import com.pepcap.ecommerce.domain.core.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(properties = "spring.profiles.active=test")
public class CategoriesControllerTest extends DatabaseContainerConfig{
	
	@Autowired
	protected SortHandlerMethodArgumentResolver sortArgumentResolver;

	@Autowired
	@Qualifier("categoriesRepository") 
	protected ICategoriesRepository categories_repository;
	
	@Autowired
	@Qualifier("productsRepository") 
	protected IProductsRepository productsRepository;
	
	@Autowired
	@Qualifier("categoriesRepository") 
	protected ICategoriesRepository categoriesRepository;
	

	@SpyBean
	@Qualifier("categoriesAppService")
	protected CategoriesAppService categoriesAppService;
	
    @SpyBean
    @Qualifier("productsAppService")
	protected ProductsAppService  productsAppService;
	
	@SpyBean
	protected LoggingHelper logHelper;

	@SpyBean
	protected Environment env;

	@Mock
	protected Logger loggerMock;

	protected Categories categories;

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
		em.createNativeQuery("truncate table e_commerce.categories CASCADE").executeUpdate();
		em.createNativeQuery("truncate table e_commerce.products CASCADE").executeUpdate();
		em.createNativeQuery("truncate table e_commerce.categories CASCADE").executeUpdate();
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

	public Categories createEntity() {
	
		Categories categoriesEntity = new Categories();
    	categoriesEntity.setCreatedAt(SearchUtils.stringToLocalDateTime("1996-09-01 09:15:22"));
		categoriesEntity.setDescription("1");
		categoriesEntity.setId(1);
		categoriesEntity.setName("1");
    	categoriesEntity.setUpdatedAt(SearchUtils.stringToLocalDateTime("1996-09-01 09:15:22"));
		categoriesEntity.setVersiono(0L);
		
		return categoriesEntity;
	}
    public CreateCategoriesInput createCategoriesInput() {
	
	    CreateCategoriesInput categoriesInput = new CreateCategoriesInput();
    	categoriesInput.setCreatedAt(SearchUtils.stringToLocalDateTime("1996-08-10 05:25:22"));
  		categoriesInput.setDescription("5");
  		categoriesInput.setName("5");
    	categoriesInput.setUpdatedAt(SearchUtils.stringToLocalDateTime("1996-08-10 05:25:22"));
		
		return categoriesInput;
	}

	public Categories createNewEntity() {
		Categories categories = new Categories();
    	categories.setCreatedAt(SearchUtils.stringToLocalDateTime("1996-08-11 05:35:22"));
		categories.setDescription("3");
		categories.setId(3);
		categories.setName("3");
    	categories.setUpdatedAt(SearchUtils.stringToLocalDateTime("1996-08-11 05:35:22"));
		
		return categories;
	}
	
	public Categories createUpdateEntity() {
		Categories categories = new Categories();
    	categories.setCreatedAt(SearchUtils.stringToLocalDateTime("1996-09-09 05:45:22"));
		categories.setDescription("4");
		categories.setId(4);
		categories.setName("4");
    	categories.setUpdatedAt(SearchUtils.stringToLocalDateTime("1996-09-09 05:45:22"));
		
		return categories;
	}
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
    
		final CategoriesController categoriesController = new CategoriesController(categoriesAppService, productsAppService,
		logHelper,env);
		when(logHelper.getLogger()).thenReturn(loggerMock);
		doNothing().when(loggerMock).error(anyString());

		this.mvc = MockMvcBuilders.standaloneSetup(categoriesController)
				.setCustomArgumentResolvers(sortArgumentResolver)
				.setControllerAdvice()
				.build();
	}

	@Before
	public void initTest() {

		categories= createEntity();
		List<Categories> list= categories_repository.findAll();
		if(!list.contains(categories)) {
			categories=categories_repository.save(categories);
		}

	}
	
	@Test
	public void FindById_IdIsValid_ReturnStatusOk() throws Exception {
	
		mvc.perform(get("/categories/" + categories.getId()+"/")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
	}  

	@Test
	public void FindById_IdIsNotValid_ReturnStatusNotFound() {

		 org.assertj.core.api.Assertions.assertThatThrownBy(() -> mvc.perform(get("/categories/999")
				.contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk())).hasCause(new EntityNotFoundException("Not found"));

	}
	@Test
	public void CreateCategories_CategoriesDoesNotExist_ReturnStatusOk() throws Exception {
		CreateCategoriesInput categoriesInput = createCategoriesInput();	
		
		
		ObjectWriter ow = new ObjectMapper().registerModule(new JavaTimeModule()).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS).writer().withDefaultPrettyPrinter();
	
		String json = ow.writeValueAsString(categoriesInput);

		mvc.perform(post("/categories").contentType(MediaType.APPLICATION_JSON).content(json))
		.andExpect(status().isOk());
	}     
	

	@Test
	public void DeleteCategories_IdIsNotValid_ThrowEntityNotFoundException() {

        doReturn(null).when(categoriesAppService).findById(999);
        org.assertj.core.api.Assertions.assertThatThrownBy(() ->  mvc.perform(delete("/categories/999")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())).hasCause(new EntityNotFoundException("There does not exist a categories with a id=999"));

	}  

	@Test
	public void Delete_IdIsValid_ReturnStatusNoContent() throws Exception {
	
	 	Categories entity =  createNewEntity();
	 	entity.setVersiono(0L);
		entity = categories_repository.save(entity);
		

		FindCategoriesByIdOutput output= new FindCategoriesByIdOutput();
		output.setId(entity.getId());
		output.setName(entity.getName());
		
         Mockito.doReturn(output).when(categoriesAppService).findById(entity.getId());
       
    //    Mockito.when(categoriesAppService.findById(entity.getId())).thenReturn(output);
        
		mvc.perform(delete("/categories/" + entity.getId()+"/")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isNoContent());
	}  


	@Test
	public void UpdateCategories_CategoriesDoesNotExist_ReturnStatusNotFound() throws Exception {
   
        doReturn(null).when(categoriesAppService).findById(999);
        
        UpdateCategoriesInput categories = new UpdateCategoriesInput();
		categories.setCreatedAt(SearchUtils.stringToLocalDateTime("1996-09-28 07:15:22"));
  		categories.setDescription("999");
		categories.setId(999);
  		categories.setName("999");
		categories.setUpdatedAt(SearchUtils.stringToLocalDateTime("1996-09-28 07:15:22"));

		ObjectWriter ow = new ObjectMapper().registerModule(new JavaTimeModule()).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS).writer().withDefaultPrettyPrinter();
		String json = ow.writeValueAsString(categories);

		 org.assertj.core.api.Assertions.assertThatThrownBy(() -> mvc.perform(
		 	put("/categories/999")
		 	.contentType(MediaType.APPLICATION_JSON)
		 	.content(json))
			.andExpect(status().isOk())).hasCause(new EntityNotFoundException("Unable to update. Categories with id=999 not found."));
	}    

	@Test
	public void UpdateCategories_CategoriesExists_ReturnStatusOk() throws Exception {
		Categories entity =  createUpdateEntity();
		entity.setVersiono(0L);
		
		entity = categories_repository.save(entity);
		FindCategoriesByIdOutput output= new FindCategoriesByIdOutput();
		output.setCreatedAt(entity.getCreatedAt());
		output.setDescription(entity.getDescription());
		output.setId(entity.getId());
		output.setName(entity.getName());
		output.setUpdatedAt(entity.getUpdatedAt());
		output.setVersiono(entity.getVersiono());
		
        Mockito.when(categoriesAppService.findById(entity.getId())).thenReturn(output);
        
        
		
		UpdateCategoriesInput categoriesInput = new UpdateCategoriesInput();
		categoriesInput.setId(entity.getId());
		categoriesInput.setName(entity.getName());
		

		ObjectWriter ow = new ObjectMapper().registerModule(new JavaTimeModule()).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS).writer().withDefaultPrettyPrinter();
		String json = ow.writeValueAsString(categoriesInput);
	
		mvc.perform(put("/categories/" + entity.getId()+"/").contentType(MediaType.APPLICATION_JSON).content(json))
		.andExpect(status().isOk());

		Categories de = createUpdateEntity();
		de.setId(entity.getId());
		categories_repository.delete(de);
		

	}    
	@Test
	public void FindAll_SearchIsNotNullAndPropertyIsValid_ReturnStatusOk() throws Exception {

		mvc.perform(get("/categories?search=id[equals]=1&limit=10&offset=1")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
	}
		
	
	
	@Test
	public void GetProducts_searchIsNotEmptyAndPropertyIsValid_ReturnList() throws Exception {

		Map<String,String> joinCol = new HashMap<String,String>();
		joinCol.put("id", "1");
		
        Mockito.when(categoriesAppService.parseProductsJoinColumn("categoryId")).thenReturn(joinCol);
		mvc.perform(get("/categories/1/products?search=categoryId[equals]=1&limit=10&offset=1")
				.contentType(MediaType.APPLICATION_JSON))
	    		  .andExpect(status().isOk());
	}  
	
	@Test
	public void GetProducts_searchIsNotEmpty() {
	
		Mockito.when(categoriesAppService.parseProductsJoinColumn(anyString())).thenReturn(null);
	 		  		    		  
	    org.assertj.core.api.Assertions.assertThatThrownBy(() -> mvc.perform(get("/categories/1/products?search=categoryId[equals]=1&limit=10&offset=1")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())).hasCause(new EntityNotFoundException("Invalid join column"));
	}    
    
}

