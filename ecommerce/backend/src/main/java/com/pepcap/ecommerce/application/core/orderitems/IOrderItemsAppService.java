package com.pepcap.ecommerce.application.core.orderitems;

import org.springframework.data.domain.Pageable;
import com.pepcap.ecommerce.application.core.orderitems.dto.*;
import com.pepcap.ecommerce.commons.search.SearchCriteria;
import java.net.MalformedURLException;
import java.util.*;

public interface IOrderItemsAppService {
	
	//CRUD Operations
	CreateOrderItemsOutput create(CreateOrderItemsInput orderitems);

    void delete(Integer id);

    UpdateOrderItemsOutput update(Integer id, UpdateOrderItemsInput input);

    FindOrderItemsByIdOutput findById(Integer id);


    List<FindOrderItemsByIdOutput> find(SearchCriteria search, Pageable pageable) throws MalformedURLException;
	//Relationship Operations
	//Relationship Operations
    
    GetOrdersOutput getOrders(Integer orderItemsid);
    
    GetProductsOutput getProducts(Integer orderItemsid);
    
    //Join Column Parsers
}

