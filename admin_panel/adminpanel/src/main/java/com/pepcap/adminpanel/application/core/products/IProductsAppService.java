package com.pepcap.adminpanel.application.core.products;

import org.springframework.data.domain.Pageable;
import com.pepcap.adminpanel.application.core.products.dto.*;
import com.pepcap.adminpanel.commons.search.SearchCriteria;
import java.net.MalformedURLException;
import java.util.*;

public interface IProductsAppService {
	
	//CRUD Operations
	CreateProductsOutput create(CreateProductsInput products);

    void delete(Integer id);

    UpdateProductsOutput update(Integer id, UpdateProductsInput input);

    FindProductsByIdOutput findById(Integer id);


    List<FindProductsByIdOutput> find(SearchCriteria search, Pageable pageable) throws MalformedURLException;
	//Relationship Operations
    
    GetCategoriesOutput getCategories(Integer productsid);
    
    //Join Column Parsers
}

