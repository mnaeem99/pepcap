package com.pepcap.ecommerce.application.core.orders;

import org.springframework.data.domain.Pageable;
import com.pepcap.ecommerce.application.core.orders.dto.*;
import com.pepcap.ecommerce.commons.search.SearchCriteria;
import java.net.MalformedURLException;
import java.util.*;

public interface IOrdersAppService {
	
	//CRUD Operations
	CreateOrdersOutput create(CreateOrdersInput orders);

    void delete(Integer id);

    UpdateOrdersOutput update(Integer id, UpdateOrdersInput input);

    FindOrdersByIdOutput findById(Integer id);


    List<FindOrdersByIdOutput> find(SearchCriteria search, Pageable pageable) throws MalformedURLException;
	//Relationship Operations
    
    GetUsersOutput getUsers(Integer ordersid);
    
    //Join Column Parsers

	Map<String,String> parseOrderItemsJoinColumn(String keysString);
}

