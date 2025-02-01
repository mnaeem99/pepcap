package com.pepcap.inventorymanagement.application.core.inventorytransactions;

import org.springframework.data.domain.Pageable;
import com.pepcap.inventorymanagement.application.core.inventorytransactions.dto.*;
import com.pepcap.inventorymanagement.commons.search.SearchCriteria;
import java.net.MalformedURLException;
import java.util.*;

public interface IInventoryTransactionsAppService {
	
	//CRUD Operations
	CreateInventoryTransactionsOutput create(CreateInventoryTransactionsInput inventorytransactions);

    void delete(Integer id);

    UpdateInventoryTransactionsOutput update(Integer id, UpdateInventoryTransactionsInput input);

    FindInventoryTransactionsByIdOutput findById(Integer id);


    List<FindInventoryTransactionsByIdOutput> find(SearchCriteria search, Pageable pageable) throws MalformedURLException;
	//Relationship Operations
    
    GetProductsOutput getProducts(Integer inventoryTransactionsid);
    
    //Join Column Parsers
}

