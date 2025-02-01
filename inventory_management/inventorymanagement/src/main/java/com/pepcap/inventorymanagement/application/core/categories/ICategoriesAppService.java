package com.pepcap.inventorymanagement.application.core.categories;

import org.springframework.data.domain.Pageable;
import com.pepcap.inventorymanagement.application.core.categories.dto.*;
import com.pepcap.inventorymanagement.commons.search.SearchCriteria;
import java.net.MalformedURLException;
import java.util.*;

public interface ICategoriesAppService {
	
	//CRUD Operations
	CreateCategoriesOutput create(CreateCategoriesInput categories);

    void delete(Integer id);

    UpdateCategoriesOutput update(Integer id, UpdateCategoriesInput input);

    FindCategoriesByIdOutput findById(Integer id);


    List<FindCategoriesByIdOutput> find(SearchCriteria search, Pageable pageable) throws MalformedURLException;
    
    //Join Column Parsers

	Map<String,String> parseProductsJoinColumn(String keysString);
}

