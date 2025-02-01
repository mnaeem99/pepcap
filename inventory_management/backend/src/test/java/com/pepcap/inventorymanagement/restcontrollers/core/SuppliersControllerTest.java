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
import com.pepcap.inventorymanagement.application.core.suppliers.SuppliersAppService;
import com.pepcap.inventorymanagement.application.core.suppliers.dto.*;
import com.pepcap.inventorymanagement.domain.core.suppliers.ISuppliersRepository;
import com.pepcap.inventorymanagement.domain.core.suppliers.Suppliers;

import com.pepcap.inventorymanagement.DatabaseContainerConfig;
import com.pepcap.inventorymanagement.domain.core.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(properties = "spring.profiles.active=test")
public class SuppliersControllerTest extends DatabaseContainerConfig{
	
	@Autowired
	protected SortHandlerMethodArgumentResolver sortArgumentResolver;

	@Autowired
	@Qualifier("suppliersRepository") 
	protected ISuppliersRepository suppliers_repository;
	

	@SpyBean
	@Qualifier("suppliersAppService")
	protected SuppliersAppService suppliersAppService;
	
	@SpyBean
	protected LoggingHelper logHelper;

	@SpyBean
	protected Environment env;

	@Mock
	protected Logger loggerMock;

	protected Suppliers suppliers;

	protected MockMvc mvc;
	
	@Autowired
	EntityManagerFactory emf;
	
    static EntityManagerFactory emfs;
    
    static int relationCount = 10;
    static int yearCount = 1971;
    static int dayCount = 10;
	private BigDecimal bigdec = new BigDecimal(1.2);
    
	@PostConstruct
	public void init() {
	emfs = emf;
	}

	@AfterClass
	public static void cleanup() {
		EntityManager em = emfs.createEntityManager();
		em.getTransaction().begin();
		em.createNativeQuery("truncate table inventory_management.suppliers CASCADE").executeUpdate();
		em.getTransaction().commit();
	}
	

	public Suppliers createEntity() {
	
		Suppliers suppliersEntity = new Suppliers();
		suppliersEntity.setContactInfo("1");
    	suppliersEntity.setCreatedAt(SearchUtils.stringToLocalDateTime("1996-09-01 09:15:22"));
		suppliersEntity.setId(1);
		suppliersEntity.setName("1");
		suppliersEntity.setVersiono(0L);
		
		return suppliersEntity;
	}
    public CreateSuppliersInput createSuppliersInput() {
	
	    CreateSuppliersInput suppliersInput = new CreateSuppliersInput();
  		suppliersInput.setContactInfo("5");
    	suppliersInput.setCreatedAt(SearchUtils.stringToLocalDateTime("1996-08-10 05:25:22"));
  		suppliersInput.setName("5");
		
		return suppliersInput;
	}

	public Suppliers createNewEntity() {
		Suppliers suppliers = new Suppliers();
		suppliers.setContactInfo("3");
    	suppliers.setCreatedAt(SearchUtils.stringToLocalDateTime("1996-08-11 05:35:22"));
		suppliers.setId(3);
		suppliers.setName("3");
		
		return suppliers;
	}
	
	public Suppliers createUpdateEntity() {
		Suppliers suppliers = new Suppliers();
		suppliers.setContactInfo("4");
    	suppliers.setCreatedAt(SearchUtils.stringToLocalDateTime("1996-09-09 05:45:22"));
		suppliers.setId(4);
		suppliers.setName("4");
		
		return suppliers;
	}
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
    
		final SuppliersController suppliersController = new SuppliersController(suppliersAppService,
		logHelper,env);
		when(logHelper.getLogger()).thenReturn(loggerMock);
		doNothing().when(loggerMock).error(anyString());

		this.mvc = MockMvcBuilders.standaloneSetup(suppliersController)
				.setCustomArgumentResolvers(sortArgumentResolver)
				.setControllerAdvice()
				.build();
	}

	@Before
	public void initTest() {

		suppliers= createEntity();
		List<Suppliers> list= suppliers_repository.findAll();
		if(!list.contains(suppliers)) {
			suppliers=suppliers_repository.save(suppliers);
		}

	}
	
	@Test
	public void FindById_IdIsValid_ReturnStatusOk() throws Exception {
	
		mvc.perform(get("/suppliers/" + suppliers.getId()+"/")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
	}  

	@Test
	public void FindById_IdIsNotValid_ReturnStatusNotFound() {

		 org.assertj.core.api.Assertions.assertThatThrownBy(() -> mvc.perform(get("/suppliers/999")
				.contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk())).hasCause(new EntityNotFoundException("Not found"));

	}
	@Test
	public void CreateSuppliers_SuppliersDoesNotExist_ReturnStatusOk() throws Exception {
		CreateSuppliersInput suppliersInput = createSuppliersInput();	
		
		
		ObjectWriter ow = new ObjectMapper().registerModule(new JavaTimeModule()).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS).writer().withDefaultPrettyPrinter();
	
		String json = ow.writeValueAsString(suppliersInput);

		mvc.perform(post("/suppliers").contentType(MediaType.APPLICATION_JSON).content(json))
		.andExpect(status().isOk());
	}     

	@Test
	public void DeleteSuppliers_IdIsNotValid_ThrowEntityNotFoundException() {

        doReturn(null).when(suppliersAppService).findById(999);
        org.assertj.core.api.Assertions.assertThatThrownBy(() ->  mvc.perform(delete("/suppliers/999")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())).hasCause(new EntityNotFoundException("There does not exist a suppliers with a id=999"));

	}  

	@Test
	public void Delete_IdIsValid_ReturnStatusNoContent() throws Exception {
	
	 	Suppliers entity =  createNewEntity();
	 	entity.setVersiono(0L);
		entity = suppliers_repository.save(entity);
		

		FindSuppliersByIdOutput output= new FindSuppliersByIdOutput();
		output.setId(entity.getId());
		output.setName(entity.getName());
		
         Mockito.doReturn(output).when(suppliersAppService).findById(entity.getId());
       
    //    Mockito.when(suppliersAppService.findById(entity.getId())).thenReturn(output);
        
		mvc.perform(delete("/suppliers/" + entity.getId()+"/")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isNoContent());
	}  


	@Test
	public void UpdateSuppliers_SuppliersDoesNotExist_ReturnStatusNotFound() throws Exception {
   
        doReturn(null).when(suppliersAppService).findById(999);
        
        UpdateSuppliersInput suppliers = new UpdateSuppliersInput();
  		suppliers.setContactInfo("999");
		suppliers.setCreatedAt(SearchUtils.stringToLocalDateTime("1996-09-28 07:15:22"));
		suppliers.setId(999);
  		suppliers.setName("999");

		ObjectWriter ow = new ObjectMapper().registerModule(new JavaTimeModule()).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS).writer().withDefaultPrettyPrinter();
		String json = ow.writeValueAsString(suppliers);

		 org.assertj.core.api.Assertions.assertThatThrownBy(() -> mvc.perform(
		 	put("/suppliers/999")
		 	.contentType(MediaType.APPLICATION_JSON)
		 	.content(json))
			.andExpect(status().isOk())).hasCause(new EntityNotFoundException("Unable to update. Suppliers with id=999 not found."));
	}    

	@Test
	public void UpdateSuppliers_SuppliersExists_ReturnStatusOk() throws Exception {
		Suppliers entity =  createUpdateEntity();
		entity.setVersiono(0L);
		
		entity = suppliers_repository.save(entity);
		FindSuppliersByIdOutput output= new FindSuppliersByIdOutput();
		output.setContactInfo(entity.getContactInfo());
		output.setCreatedAt(entity.getCreatedAt());
		output.setId(entity.getId());
		output.setName(entity.getName());
		output.setVersiono(entity.getVersiono());
		
        Mockito.when(suppliersAppService.findById(entity.getId())).thenReturn(output);
        
        
		
		UpdateSuppliersInput suppliersInput = new UpdateSuppliersInput();
		suppliersInput.setId(entity.getId());
		suppliersInput.setName(entity.getName());
		

		ObjectWriter ow = new ObjectMapper().registerModule(new JavaTimeModule()).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS).writer().withDefaultPrettyPrinter();
		String json = ow.writeValueAsString(suppliersInput);
	
		mvc.perform(put("/suppliers/" + entity.getId()+"/").contentType(MediaType.APPLICATION_JSON).content(json))
		.andExpect(status().isOk());

		Suppliers de = createUpdateEntity();
		de.setId(entity.getId());
		suppliers_repository.delete(de);
		

	}    
	@Test
	public void FindAll_SearchIsNotNullAndPropertyIsValid_ReturnStatusOk() throws Exception {

		mvc.perform(get("/suppliers?search=id[equals]=1&limit=10&offset=1")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
	}
		
    
}

