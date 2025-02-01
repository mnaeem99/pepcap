package com.pepcap.inventorymanagement.application.core.inventorytransactions;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.doNothing;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pepcap.inventorymanagement.domain.core.inventorytransactions.*;
import com.pepcap.inventorymanagement.commons.search.*;
import com.pepcap.inventorymanagement.application.core.inventorytransactions.dto.*;
import com.pepcap.inventorymanagement.domain.core.inventorytransactions.QInventoryTransactions;
import com.pepcap.inventorymanagement.domain.core.inventorytransactions.InventoryTransactions;

import com.pepcap.inventorymanagement.domain.core.products.Products;
import com.pepcap.inventorymanagement.domain.core.products.IProductsRepository;
import com.pepcap.inventorymanagement.commons.logging.LoggingHelper;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import java.time.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class InventoryTransactionsAppServiceTest {

	@InjectMocks
	@Spy
	protected InventoryTransactionsAppService _appService;
	@Mock
	protected IInventoryTransactionsRepository _inventoryTransactionsRepository;
	
    @Mock
	protected IProductsRepository _productsRepository;

	@Mock
	protected IInventoryTransactionsMapper _mapper;

	@Mock
	protected Logger loggerMock;

	@Mock
	protected LoggingHelper logHelper;
	
    protected static Integer ID=15;
	 
	@Before
	public void setUp() {

		MockitoAnnotations.initMocks(_appService);
		when(logHelper.getLogger()).thenReturn(loggerMock);
		doNothing().when(loggerMock).error(anyString());
	}
	
	@Test
	public void findInventoryTransactionsById_IdIsNotNullAndIdDoesNotExist_ReturnNull() {
		Optional<InventoryTransactions> nullOptional = Optional.ofNullable(null);
		Mockito.when(_inventoryTransactionsRepository.findById(any(Integer.class))).thenReturn(nullOptional);
		Assertions.assertThat(_appService.findById(ID )).isEqualTo(null);
	}
	
	@Test
	public void findInventoryTransactionsById_IdIsNotNullAndIdExists_ReturnInventoryTransactions() {

		InventoryTransactions inventoryTransactions = mock(InventoryTransactions.class);
		Optional<InventoryTransactions> inventoryTransactionsOptional = Optional.of((InventoryTransactions) inventoryTransactions);
		Mockito.when(_inventoryTransactionsRepository.findById(any(Integer.class))).thenReturn(inventoryTransactionsOptional);
		
	    Assertions.assertThat(_appService.findById(ID )).isEqualTo(_mapper.inventoryTransactionsToFindInventoryTransactionsByIdOutput(inventoryTransactions));
	}
	
	
	@Test 
    public void createInventoryTransactions_InventoryTransactionsIsNotNullAndInventoryTransactionsDoesNotExist_StoreInventoryTransactions() { 
 
        InventoryTransactions inventoryTransactionsEntity = mock(InventoryTransactions.class); 
    	CreateInventoryTransactionsInput inventoryTransactionsInput = new CreateInventoryTransactionsInput();
		
        Products products = mock(Products.class);
		Optional<Products> productsOptional = Optional.of((Products) products);
        inventoryTransactionsInput.setProductId(15);
		
        Mockito.when(_productsRepository.findById(any(Integer.class))).thenReturn(productsOptional);
        
		
        Mockito.when(_mapper.createInventoryTransactionsInputToInventoryTransactions(any(CreateInventoryTransactionsInput.class))).thenReturn(inventoryTransactionsEntity); 
        Mockito.when(_inventoryTransactionsRepository.save(any(InventoryTransactions.class))).thenReturn(inventoryTransactionsEntity);

	   	Assertions.assertThat(_appService.create(inventoryTransactionsInput)).isEqualTo(_mapper.inventoryTransactionsToCreateInventoryTransactionsOutput(inventoryTransactionsEntity));

    } 
    @Test
	public void createInventoryTransactions_InventoryTransactionsIsNotNullAndInventoryTransactionsDoesNotExistAndChildIsNullAndChildIsNotMandatory_StoreInventoryTransactions() {

		InventoryTransactions inventoryTransactions = mock(InventoryTransactions.class);
		CreateInventoryTransactionsInput inventoryTransactionsInput = mock(CreateInventoryTransactionsInput.class);
		
		
		Mockito.when(_mapper.createInventoryTransactionsInputToInventoryTransactions(any(CreateInventoryTransactionsInput.class))).thenReturn(inventoryTransactions);
		Mockito.when(_inventoryTransactionsRepository.save(any(InventoryTransactions.class))).thenReturn(inventoryTransactions);
	    Assertions.assertThat(_appService.create(inventoryTransactionsInput)).isEqualTo(_mapper.inventoryTransactionsToCreateInventoryTransactionsOutput(inventoryTransactions)); 
	}
	
    @Test
	public void updateInventoryTransactions_InventoryTransactionsIsNotNullAndInventoryTransactionsDoesNotExistAndChildIsNullAndChildIsNotMandatory_ReturnUpdatedInventoryTransactions() {

		InventoryTransactions inventoryTransactions = mock(InventoryTransactions.class);
		UpdateInventoryTransactionsInput inventoryTransactionsInput = mock(UpdateInventoryTransactionsInput.class);
		
		Optional<InventoryTransactions> inventoryTransactionsOptional = Optional.of((InventoryTransactions) inventoryTransactions);
		Mockito.when(_inventoryTransactionsRepository.findById(any(Integer.class))).thenReturn(inventoryTransactionsOptional);
		
		Mockito.when(_mapper.updateInventoryTransactionsInputToInventoryTransactions(any(UpdateInventoryTransactionsInput.class))).thenReturn(inventoryTransactions);
		Mockito.when(_inventoryTransactionsRepository.save(any(InventoryTransactions.class))).thenReturn(inventoryTransactions);
		Assertions.assertThat(_appService.update(ID,inventoryTransactionsInput)).isEqualTo(_mapper.inventoryTransactionsToUpdateInventoryTransactionsOutput(inventoryTransactions));
	}
	
		
	@Test
	public void updateInventoryTransactions_InventoryTransactionsIdIsNotNullAndIdExists_ReturnUpdatedInventoryTransactions() {

		InventoryTransactions inventoryTransactionsEntity = mock(InventoryTransactions.class);
		UpdateInventoryTransactionsInput inventoryTransactions= mock(UpdateInventoryTransactionsInput.class);
		
		Optional<InventoryTransactions> inventoryTransactionsOptional = Optional.of((InventoryTransactions) inventoryTransactionsEntity);
		Mockito.when(_inventoryTransactionsRepository.findById(any(Integer.class))).thenReturn(inventoryTransactionsOptional);
	 		
		Mockito.when(_mapper.updateInventoryTransactionsInputToInventoryTransactions(any(UpdateInventoryTransactionsInput.class))).thenReturn(inventoryTransactionsEntity);
		Mockito.when(_inventoryTransactionsRepository.save(any(InventoryTransactions.class))).thenReturn(inventoryTransactionsEntity);
		Assertions.assertThat(_appService.update(ID,inventoryTransactions)).isEqualTo(_mapper.inventoryTransactionsToUpdateInventoryTransactionsOutput(inventoryTransactionsEntity));
	}
    
	@Test
	public void deleteInventoryTransactions_InventoryTransactionsIsNotNullAndInventoryTransactionsExists_InventoryTransactionsRemoved() {

		InventoryTransactions inventoryTransactions = mock(InventoryTransactions.class);
		Optional<InventoryTransactions> inventoryTransactionsOptional = Optional.of((InventoryTransactions) inventoryTransactions);
		Mockito.when(_inventoryTransactionsRepository.findById(any(Integer.class))).thenReturn(inventoryTransactionsOptional);
 		
		_appService.delete(ID); 
		verify(_inventoryTransactionsRepository).delete(inventoryTransactions);
	}
	
	@Test
	public void find_ListIsEmpty_ReturnList() throws Exception {

		List<InventoryTransactions> list = new ArrayList<>();
		Page<InventoryTransactions> foundPage = new PageImpl(list);
		Pageable pageable = mock(Pageable.class);
		List<FindInventoryTransactionsByIdOutput> output = new ArrayList<>();
		SearchCriteria search= new SearchCriteria();

		Mockito.when(_appService.search(any(SearchCriteria.class))).thenReturn(new BooleanBuilder());
		Mockito.when(_inventoryTransactionsRepository.findAll(any(Predicate.class),any(Pageable.class))).thenReturn(foundPage);
		Assertions.assertThat(_appService.find(search, pageable)).isEqualTo(output);
	}
	
	@Test
	public void find_ListIsNotEmpty_ReturnList() throws Exception {

		List<InventoryTransactions> list = new ArrayList<>();
		InventoryTransactions inventoryTransactions = mock(InventoryTransactions.class);
		list.add(inventoryTransactions);
    	Page<InventoryTransactions> foundPage = new PageImpl(list);
		Pageable pageable = mock(Pageable.class);
		List<FindInventoryTransactionsByIdOutput> output = new ArrayList<>();
        SearchCriteria search= new SearchCriteria();

		output.add(_mapper.inventoryTransactionsToFindInventoryTransactionsByIdOutput(inventoryTransactions));
		
		Mockito.when(_appService.search(any(SearchCriteria.class))).thenReturn(new BooleanBuilder());
    	Mockito.when(_inventoryTransactionsRepository.findAll(any(Predicate.class),any(Pageable.class))).thenReturn(foundPage);
		Assertions.assertThat(_appService.find(search, pageable)).isEqualTo(output);
	}
	
	@Test
	public void searchKeyValuePair_PropertyExists_ReturnBooleanBuilder() {
		QInventoryTransactions inventoryTransactions = QInventoryTransactions.inventoryTransactionsEntity;
	    SearchFields searchFields = new SearchFields();
		searchFields.setOperator("equals");
		searchFields.setSearchValue("xyz");
	    Map<String,SearchFields> map = new HashMap<>();
        map.put("transactionType",searchFields);
		Map<String,String> searchMap = new HashMap<>();
        searchMap.put("xyz",String.valueOf(ID));
		BooleanBuilder builder = new BooleanBuilder();
        builder.and(inventoryTransactions.transactionType.eq("xyz"));
		Assertions.assertThat(_appService.searchKeyValuePair(inventoryTransactions,map,searchMap)).isEqualTo(builder);
	}
	
	@Test (expected = Exception.class)
	public void checkProperties_PropertyDoesNotExist_ThrowException() throws Exception {
		List<String> list = new ArrayList<>();
		list.add("xyz");
		_appService.checkProperties(list);
	}
	
	@Test
	public void checkProperties_PropertyExists_ReturnNothing() throws Exception {
		List<String> list = new ArrayList<>();
        list.add("transactionType");
		_appService.checkProperties(list);
	}
	
	@Test
	public void search_SearchIsNotNullAndSearchContainsCaseThree_ReturnBooleanBuilder() throws Exception {
	
		Map<String,SearchFields> map = new HashMap<>();
		QInventoryTransactions inventoryTransactions = QInventoryTransactions.inventoryTransactionsEntity;
		List<SearchFields> fieldsList= new ArrayList<>();
		SearchFields fields=new SearchFields();
		SearchCriteria search= new SearchCriteria();
		search.setType(3);
		search.setValue("xyz");
		search.setOperator("equals");
        fields.setFieldName("transactionType");
        fields.setOperator("equals");
		fields.setSearchValue("xyz");
        fieldsList.add(fields);
        search.setFields(fieldsList);
		BooleanBuilder builder = new BooleanBuilder();
        builder.or(inventoryTransactions.transactionType.eq("xyz"));
        Mockito.doNothing().when(_appService).checkProperties(any(List.class));
		Mockito.doReturn(builder).when(_appService).searchKeyValuePair(any(QInventoryTransactions.class), any(HashMap.class), any(HashMap.class));
        
		Assertions.assertThat(_appService.search(search)).isEqualTo(builder);
	}
	
	@Test
	public void search_StringIsNull_ReturnNull() throws Exception {

		Assertions.assertThat(_appService.search(null)).isEqualTo(null);
	}
   
    //Products
	@Test
	public void GetProducts_IfInventoryTransactionsIdAndProductsIdIsNotNullAndInventoryTransactionsExists_ReturnProducts() {
		InventoryTransactions inventoryTransactions = mock(InventoryTransactions.class);
		Optional<InventoryTransactions> inventoryTransactionsOptional = Optional.of((InventoryTransactions) inventoryTransactions);
		Products productsEntity = mock(Products.class);

		Mockito.when(_inventoryTransactionsRepository.findById(any(Integer.class))).thenReturn(inventoryTransactionsOptional);

		Mockito.when(inventoryTransactions.getProducts()).thenReturn(productsEntity);
		Assertions.assertThat(_appService.getProducts(ID)).isEqualTo(_mapper.productsToGetProductsOutput(productsEntity, inventoryTransactions));
	}

	@Test 
	public void GetProducts_IfInventoryTransactionsIdAndProductsIdIsNotNullAndInventoryTransactionsDoesNotExist_ReturnNull() {
		Optional<InventoryTransactions> nullOptional = Optional.ofNullable(null);;
		Mockito.when(_inventoryTransactionsRepository.findById(any(Integer.class))).thenReturn(nullOptional);
		Assertions.assertThat(_appService.getProducts(ID)).isEqualTo(null);
	}

}
