package com.pepcap.adminpanel.application.core.products;

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

import com.pepcap.adminpanel.domain.core.products.*;
import com.pepcap.adminpanel.commons.search.*;
import com.pepcap.adminpanel.application.core.products.dto.*;
import com.pepcap.adminpanel.domain.core.products.QProducts;
import com.pepcap.adminpanel.domain.core.products.Products;

import com.pepcap.adminpanel.domain.core.categories.Categories;
import com.pepcap.adminpanel.domain.core.categories.ICategoriesRepository;
import com.pepcap.adminpanel.commons.logging.LoggingHelper;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import java.time.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class ProductsAppServiceTest {

	@InjectMocks
	@Spy
	protected ProductsAppService _appService;
	@Mock
	protected IProductsRepository _productsRepository;
	
    @Mock
	protected ICategoriesRepository _categoriesRepository;

	@Mock
	protected IProductsMapper _mapper;

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
	public void findProductsById_IdIsNotNullAndIdDoesNotExist_ReturnNull() {
		Optional<Products> nullOptional = Optional.ofNullable(null);
		Mockito.when(_productsRepository.findById(any(Integer.class))).thenReturn(nullOptional);
		Assertions.assertThat(_appService.findById(ID )).isEqualTo(null);
	}
	
	@Test
	public void findProductsById_IdIsNotNullAndIdExists_ReturnProducts() {

		Products products = mock(Products.class);
		Optional<Products> productsOptional = Optional.of((Products) products);
		Mockito.when(_productsRepository.findById(any(Integer.class))).thenReturn(productsOptional);
		
	    Assertions.assertThat(_appService.findById(ID )).isEqualTo(_mapper.productsToFindProductsByIdOutput(products));
	}
	
	
	@Test 
    public void createProducts_ProductsIsNotNullAndProductsDoesNotExist_StoreProducts() { 
 
        Products productsEntity = mock(Products.class); 
    	CreateProductsInput productsInput = new CreateProductsInput();
		
        Categories categories = mock(Categories.class);
		Optional<Categories> categoriesOptional = Optional.of((Categories) categories);
        productsInput.setCategoryId(15);
		
        Mockito.when(_categoriesRepository.findById(any(Integer.class))).thenReturn(categoriesOptional);
        
		
        Mockito.when(_mapper.createProductsInputToProducts(any(CreateProductsInput.class))).thenReturn(productsEntity); 
        Mockito.when(_productsRepository.save(any(Products.class))).thenReturn(productsEntity);

	   	Assertions.assertThat(_appService.create(productsInput)).isEqualTo(_mapper.productsToCreateProductsOutput(productsEntity));

    } 
    @Test
	public void createProducts_ProductsIsNotNullAndProductsDoesNotExistAndChildIsNullAndChildIsNotMandatory_StoreProducts() {

		Products products = mock(Products.class);
		CreateProductsInput productsInput = mock(CreateProductsInput.class);
		
		
		Mockito.when(_mapper.createProductsInputToProducts(any(CreateProductsInput.class))).thenReturn(products);
		Mockito.when(_productsRepository.save(any(Products.class))).thenReturn(products);
	    Assertions.assertThat(_appService.create(productsInput)).isEqualTo(_mapper.productsToCreateProductsOutput(products)); 
	}
	
    @Test
	public void updateProducts_ProductsIsNotNullAndProductsDoesNotExistAndChildIsNullAndChildIsNotMandatory_ReturnUpdatedProducts() {

		Products products = mock(Products.class);
		UpdateProductsInput productsInput = mock(UpdateProductsInput.class);
		
		Optional<Products> productsOptional = Optional.of((Products) products);
		Mockito.when(_productsRepository.findById(any(Integer.class))).thenReturn(productsOptional);
		
		Mockito.when(_mapper.updateProductsInputToProducts(any(UpdateProductsInput.class))).thenReturn(products);
		Mockito.when(_productsRepository.save(any(Products.class))).thenReturn(products);
		Assertions.assertThat(_appService.update(ID,productsInput)).isEqualTo(_mapper.productsToUpdateProductsOutput(products));
	}
	
		
	@Test
	public void updateProducts_ProductsIdIsNotNullAndIdExists_ReturnUpdatedProducts() {

		Products productsEntity = mock(Products.class);
		UpdateProductsInput products= mock(UpdateProductsInput.class);
		
		Optional<Products> productsOptional = Optional.of((Products) productsEntity);
		Mockito.when(_productsRepository.findById(any(Integer.class))).thenReturn(productsOptional);
	 		
		Mockito.when(_mapper.updateProductsInputToProducts(any(UpdateProductsInput.class))).thenReturn(productsEntity);
		Mockito.when(_productsRepository.save(any(Products.class))).thenReturn(productsEntity);
		Assertions.assertThat(_appService.update(ID,products)).isEqualTo(_mapper.productsToUpdateProductsOutput(productsEntity));
	}
    
	@Test
	public void deleteProducts_ProductsIsNotNullAndProductsExists_ProductsRemoved() {

		Products products = mock(Products.class);
		Optional<Products> productsOptional = Optional.of((Products) products);
		Mockito.when(_productsRepository.findById(any(Integer.class))).thenReturn(productsOptional);
 		
		_appService.delete(ID); 
		verify(_productsRepository).delete(products);
	}
	
	@Test
	public void find_ListIsEmpty_ReturnList() throws Exception {

		List<Products> list = new ArrayList<>();
		Page<Products> foundPage = new PageImpl(list);
		Pageable pageable = mock(Pageable.class);
		List<FindProductsByIdOutput> output = new ArrayList<>();
		SearchCriteria search= new SearchCriteria();

		Mockito.when(_appService.search(any(SearchCriteria.class))).thenReturn(new BooleanBuilder());
		Mockito.when(_productsRepository.findAll(any(Predicate.class),any(Pageable.class))).thenReturn(foundPage);
		Assertions.assertThat(_appService.find(search, pageable)).isEqualTo(output);
	}
	
	@Test
	public void find_ListIsNotEmpty_ReturnList() throws Exception {

		List<Products> list = new ArrayList<>();
		Products products = mock(Products.class);
		list.add(products);
    	Page<Products> foundPage = new PageImpl(list);
		Pageable pageable = mock(Pageable.class);
		List<FindProductsByIdOutput> output = new ArrayList<>();
        SearchCriteria search= new SearchCriteria();

		output.add(_mapper.productsToFindProductsByIdOutput(products));
		
		Mockito.when(_appService.search(any(SearchCriteria.class))).thenReturn(new BooleanBuilder());
    	Mockito.when(_productsRepository.findAll(any(Predicate.class),any(Pageable.class))).thenReturn(foundPage);
		Assertions.assertThat(_appService.find(search, pageable)).isEqualTo(output);
	}
	
	@Test
	public void searchKeyValuePair_PropertyExists_ReturnBooleanBuilder() {
		QProducts products = QProducts.productsEntity;
	    SearchFields searchFields = new SearchFields();
		searchFields.setOperator("equals");
		searchFields.setSearchValue("xyz");
	    Map<String,SearchFields> map = new HashMap<>();
        map.put("description",searchFields);
		Map<String,String> searchMap = new HashMap<>();
        searchMap.put("xyz",String.valueOf(ID));
		BooleanBuilder builder = new BooleanBuilder();
        builder.and(products.description.eq("xyz"));
		Assertions.assertThat(_appService.searchKeyValuePair(products,map,searchMap)).isEqualTo(builder);
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
        list.add("description");
        list.add("name");
		_appService.checkProperties(list);
	}
	
	@Test
	public void search_SearchIsNotNullAndSearchContainsCaseThree_ReturnBooleanBuilder() throws Exception {
	
		Map<String,SearchFields> map = new HashMap<>();
		QProducts products = QProducts.productsEntity;
		List<SearchFields> fieldsList= new ArrayList<>();
		SearchFields fields=new SearchFields();
		SearchCriteria search= new SearchCriteria();
		search.setType(3);
		search.setValue("xyz");
		search.setOperator("equals");
        fields.setFieldName("description");
        fields.setOperator("equals");
		fields.setSearchValue("xyz");
        fieldsList.add(fields);
        search.setFields(fieldsList);
		BooleanBuilder builder = new BooleanBuilder();
        builder.or(products.description.eq("xyz"));
        Mockito.doNothing().when(_appService).checkProperties(any(List.class));
		Mockito.doReturn(builder).when(_appService).searchKeyValuePair(any(QProducts.class), any(HashMap.class), any(HashMap.class));
        
		Assertions.assertThat(_appService.search(search)).isEqualTo(builder);
	}
	
	@Test
	public void search_StringIsNull_ReturnNull() throws Exception {

		Assertions.assertThat(_appService.search(null)).isEqualTo(null);
	}
   
    //Categories
	@Test
	public void GetCategories_IfProductsIdAndCategoriesIdIsNotNullAndProductsExists_ReturnCategories() {
		Products products = mock(Products.class);
		Optional<Products> productsOptional = Optional.of((Products) products);
		Categories categoriesEntity = mock(Categories.class);

		Mockito.when(_productsRepository.findById(any(Integer.class))).thenReturn(productsOptional);

		Mockito.when(products.getCategories()).thenReturn(categoriesEntity);
		Assertions.assertThat(_appService.getCategories(ID)).isEqualTo(_mapper.categoriesToGetCategoriesOutput(categoriesEntity, products));
	}

	@Test 
	public void GetCategories_IfProductsIdAndCategoriesIdIsNotNullAndProductsDoesNotExist_ReturnNull() {
		Optional<Products> nullOptional = Optional.ofNullable(null);;
		Mockito.when(_productsRepository.findById(any(Integer.class))).thenReturn(nullOptional);
		Assertions.assertThat(_appService.getCategories(ID)).isEqualTo(null);
	}

}
