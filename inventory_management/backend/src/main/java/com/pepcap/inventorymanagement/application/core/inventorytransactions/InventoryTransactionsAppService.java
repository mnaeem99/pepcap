package com.pepcap.inventorymanagement.application.core.inventorytransactions;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import com.pepcap.inventorymanagement.application.core.inventorytransactions.dto.*;
import com.pepcap.inventorymanagement.domain.core.inventorytransactions.IInventoryTransactionsRepository;
import com.pepcap.inventorymanagement.domain.core.inventorytransactions.QInventoryTransactions;
import com.pepcap.inventorymanagement.domain.core.inventorytransactions.InventoryTransactions;
import com.pepcap.inventorymanagement.domain.core.products.IProductsRepository;
import com.pepcap.inventorymanagement.domain.core.products.Products;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page; 
import org.springframework.data.domain.Pageable; 
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.lang3.StringUtils;
import com.pepcap.inventorymanagement.commons.search.*;
import com.pepcap.inventorymanagement.commons.logging.LoggingHelper;
import com.querydsl.core.BooleanBuilder;
import java.net.MalformedURLException;
import java.time.*;
import java.util.*;
import javax.persistence.EntityNotFoundException;

@Service("inventoryTransactionsAppService")
@RequiredArgsConstructor
public class InventoryTransactionsAppService implements IInventoryTransactionsAppService {
    
	@Qualifier("inventoryTransactionsRepository")
	@NonNull protected final IInventoryTransactionsRepository _inventoryTransactionsRepository;

	
    @Qualifier("productsRepository")
	@NonNull protected final IProductsRepository _productsRepository;

	@Qualifier("IInventoryTransactionsMapperImpl")
	@NonNull protected final IInventoryTransactionsMapper mapper;

	@NonNull protected final LoggingHelper logHelper;

    @Transactional(propagation = Propagation.REQUIRED)
	public CreateInventoryTransactionsOutput create(CreateInventoryTransactionsInput input) {

		InventoryTransactions inventoryTransactions = mapper.createInventoryTransactionsInputToInventoryTransactions(input);
		Products foundProducts = null;
	  	if(input.getProductId()!=null) {
			foundProducts = _productsRepository.findById(input.getProductId()).orElse(null);
			
			if(foundProducts!=null) {
				foundProducts.addInventoryTransactions(inventoryTransactions);
			}
		}

		InventoryTransactions createdInventoryTransactions = _inventoryTransactionsRepository.save(inventoryTransactions);
		return mapper.inventoryTransactionsToCreateInventoryTransactionsOutput(createdInventoryTransactions);
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public UpdateInventoryTransactionsOutput update(Integer inventoryTransactionsId, UpdateInventoryTransactionsInput input) {

		InventoryTransactions existing = _inventoryTransactionsRepository.findById(inventoryTransactionsId).orElseThrow(() -> new EntityNotFoundException("InventoryTransactions not found"));

		InventoryTransactions inventoryTransactions = mapper.updateInventoryTransactionsInputToInventoryTransactions(input);
		Products foundProducts = null;
        
	  	if(input.getProductId()!=null) { 
			foundProducts = _productsRepository.findById(input.getProductId()).orElse(null);
		
			if(foundProducts!=null) {
				foundProducts.addInventoryTransactions(inventoryTransactions);
			}
		}
		
		InventoryTransactions updatedInventoryTransactions = _inventoryTransactionsRepository.save(inventoryTransactions);
		return mapper.inventoryTransactionsToUpdateInventoryTransactionsOutput(updatedInventoryTransactions);
	}
	
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(Integer inventoryTransactionsId) {

		InventoryTransactions existing = _inventoryTransactionsRepository.findById(inventoryTransactionsId).orElseThrow(() -> new EntityNotFoundException("InventoryTransactions not found"));
	 	
        if(existing.getProducts() !=null)
        {
        existing.getProducts().removeInventoryTransactions(existing);
        }
        if(existing !=null) {
			_inventoryTransactionsRepository.delete(existing);
		}
	}
	
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public FindInventoryTransactionsByIdOutput findById(Integer inventoryTransactionsId) {

		InventoryTransactions foundInventoryTransactions = _inventoryTransactionsRepository.findById(inventoryTransactionsId).orElse(null);
		if (foundInventoryTransactions == null)  
			return null; 
 	   
 	    return mapper.inventoryTransactionsToFindInventoryTransactionsByIdOutput(foundInventoryTransactions);
	}

    //Products
	// ReST API Call - GET /inventoryTransactions/1/products
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
	public GetProductsOutput getProducts(Integer inventoryTransactionsId) {

		InventoryTransactions foundInventoryTransactions = _inventoryTransactionsRepository.findById(inventoryTransactionsId).orElse(null);
		if (foundInventoryTransactions == null) {
			logHelper.getLogger().error("There does not exist a inventoryTransactions wth a id='{}'", inventoryTransactionsId);
			return null;
		}
		Products re = foundInventoryTransactions.getProducts();
		return mapper.productsToGetProductsOutput(re, foundInventoryTransactions);
	}
	
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<FindInventoryTransactionsByIdOutput> find(SearchCriteria search, Pageable pageable) throws MalformedURLException  {

		Page<InventoryTransactions> foundInventoryTransactions = _inventoryTransactionsRepository.findAll(search(search), pageable);
		List<InventoryTransactions> inventoryTransactionsList = foundInventoryTransactions.getContent();
		Iterator<InventoryTransactions> inventoryTransactionsIterator = inventoryTransactionsList.iterator(); 
		List<FindInventoryTransactionsByIdOutput> output = new ArrayList<>();

		while (inventoryTransactionsIterator.hasNext()) {
		InventoryTransactions inventoryTransactions = inventoryTransactionsIterator.next();
 	    output.add(mapper.inventoryTransactionsToFindInventoryTransactionsByIdOutput(inventoryTransactions));
		}
		return output;
	}
	
	protected BooleanBuilder search(SearchCriteria search) throws MalformedURLException {

		QInventoryTransactions inventoryTransactions= QInventoryTransactions.inventoryTransactionsEntity;
		if(search != null) {
			Map<String,SearchFields> map = new HashMap<>();
			for(SearchFields fieldDetails: search.getFields())
			{
				map.put(fieldDetails.getFieldName(),fieldDetails);
			}
			List<String> keysList = new ArrayList<String>(map.keySet());
			checkProperties(keysList);
			return searchKeyValuePair(inventoryTransactions, map,search.getJoinColumns());
		}
		return null;
	}
	
	protected void checkProperties(List<String> list) throws MalformedURLException  {
		for (int i = 0; i < list.size(); i++) {
			if(!(
		        list.get(i).replace("%20","").trim().equals("products") ||
				list.get(i).replace("%20","").trim().equals("productId") ||
				list.get(i).replace("%20","").trim().equals("createdAt") ||
				list.get(i).replace("%20","").trim().equals("id") ||
				list.get(i).replace("%20","").trim().equals("quantity") ||
				list.get(i).replace("%20","").trim().equals("transactionType")
			)) 
			{
			 throw new MalformedURLException("Wrong URL Format: Property " + list.get(i) + " not found!" );
			}
		}
	}
	
	protected BooleanBuilder searchKeyValuePair(QInventoryTransactions inventoryTransactions, Map<String,SearchFields> map,Map<String,String> joinColumns) {
		BooleanBuilder builder = new BooleanBuilder();
        
		Iterator<Map.Entry<String, SearchFields>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, SearchFields> details = iterator.next();

			if(details.getKey().replace("%20","").trim().equals("createdAt")) {
				if(details.getValue().getOperator().equals("equals") && SearchUtils.stringToLocalDateTime(details.getValue().getSearchValue()) !=null) {
					builder.and(inventoryTransactions.createdAt.eq(SearchUtils.stringToLocalDateTime(details.getValue().getSearchValue())));
				} else if(details.getValue().getOperator().equals("notEqual") && SearchUtils.stringToLocalDateTime(details.getValue().getSearchValue()) !=null) {
					builder.and(inventoryTransactions.createdAt.ne(SearchUtils.stringToLocalDateTime(details.getValue().getSearchValue())));
				} else if(details.getValue().getOperator().equals("range")) {
				   LocalDateTime startLocalDateTime= SearchUtils.stringToLocalDateTime(details.getValue().getStartingValue());
				   LocalDateTime endLocalDateTime= SearchUtils.stringToLocalDateTime(details.getValue().getEndingValue());
				   if(startLocalDateTime!=null && endLocalDateTime!=null) {
					   builder.and(inventoryTransactions.createdAt.between(startLocalDateTime,endLocalDateTime));
				   } else if(endLocalDateTime!=null) {
					   builder.and(inventoryTransactions.createdAt.loe(endLocalDateTime));
                   } else if(startLocalDateTime!=null) {
                	   builder.and(inventoryTransactions.createdAt.goe(startLocalDateTime));  
                   }
                }     
			}
			if(details.getKey().replace("%20","").trim().equals("id")) {
				if(details.getValue().getOperator().equals("contains") && StringUtils.isNumeric(details.getValue().getSearchValue())) {
					builder.and(inventoryTransactions.id.like(details.getValue().getSearchValue() + "%"));
				} else if(details.getValue().getOperator().equals("equals") && StringUtils.isNumeric(details.getValue().getSearchValue())) {
					builder.and(inventoryTransactions.id.eq(Integer.valueOf(details.getValue().getSearchValue())));
				} else if(details.getValue().getOperator().equals("notEqual") && StringUtils.isNumeric(details.getValue().getSearchValue())) {
					builder.and(inventoryTransactions.id.ne(Integer.valueOf(details.getValue().getSearchValue())));
				} else if(details.getValue().getOperator().equals("range")) {
				   if(StringUtils.isNumeric(details.getValue().getStartingValue()) && StringUtils.isNumeric(details.getValue().getEndingValue())) {
                	   builder.and(inventoryTransactions.id.between(Integer.valueOf(details.getValue().getStartingValue()), Integer.valueOf(details.getValue().getEndingValue())));
                   } else if(StringUtils.isNumeric(details.getValue().getStartingValue())) {
                	   builder.and(inventoryTransactions.id.goe(Integer.valueOf(details.getValue().getStartingValue())));
                   } else if(StringUtils.isNumeric(details.getValue().getEndingValue())) {
                	   builder.and(inventoryTransactions.id.loe(Integer.valueOf(details.getValue().getEndingValue())));
				   }
				}
			}
			if(details.getKey().replace("%20","").trim().equals("quantity")) {
				if(details.getValue().getOperator().equals("contains") && StringUtils.isNumeric(details.getValue().getSearchValue())) {
					builder.and(inventoryTransactions.quantity.like(details.getValue().getSearchValue() + "%"));
				} else if(details.getValue().getOperator().equals("equals") && StringUtils.isNumeric(details.getValue().getSearchValue())) {
					builder.and(inventoryTransactions.quantity.eq(Integer.valueOf(details.getValue().getSearchValue())));
				} else if(details.getValue().getOperator().equals("notEqual") && StringUtils.isNumeric(details.getValue().getSearchValue())) {
					builder.and(inventoryTransactions.quantity.ne(Integer.valueOf(details.getValue().getSearchValue())));
				} else if(details.getValue().getOperator().equals("range")) {
				   if(StringUtils.isNumeric(details.getValue().getStartingValue()) && StringUtils.isNumeric(details.getValue().getEndingValue())) {
                	   builder.and(inventoryTransactions.quantity.between(Integer.valueOf(details.getValue().getStartingValue()), Integer.valueOf(details.getValue().getEndingValue())));
                   } else if(StringUtils.isNumeric(details.getValue().getStartingValue())) {
                	   builder.and(inventoryTransactions.quantity.goe(Integer.valueOf(details.getValue().getStartingValue())));
                   } else if(StringUtils.isNumeric(details.getValue().getEndingValue())) {
                	   builder.and(inventoryTransactions.quantity.loe(Integer.valueOf(details.getValue().getEndingValue())));
				   }
				}
			}
            if(details.getKey().replace("%20","").trim().equals("transactionType")) {
				if(details.getValue().getOperator().equals("contains")) {
					builder.and(inventoryTransactions.transactionType.likeIgnoreCase("%"+ details.getValue().getSearchValue() + "%"));
				} else if(details.getValue().getOperator().equals("equals")) {
					builder.and(inventoryTransactions.transactionType.eq(details.getValue().getSearchValue()));
				} else if(details.getValue().getOperator().equals("notEqual")) {
					builder.and(inventoryTransactions.transactionType.ne(details.getValue().getSearchValue()));
				}
			}
	    
		     if(details.getKey().replace("%20","").trim().equals("products")) {
				if(details.getValue().getOperator().equals("contains") && StringUtils.isNumeric(details.getValue().getSearchValue())) {
					builder.and(inventoryTransactions.products.id.like(details.getValue().getSearchValue() + "%"));
				} else if(details.getValue().getOperator().equals("equals") && StringUtils.isNumeric(details.getValue().getSearchValue())) {
					builder.and(inventoryTransactions.products.id.eq(Integer.valueOf(details.getValue().getSearchValue())));
				} else if(details.getValue().getOperator().equals("notEqual") && StringUtils.isNumeric(details.getValue().getSearchValue())) {
					builder.and(inventoryTransactions.products.id.ne(Integer.valueOf(details.getValue().getSearchValue())));
				} else if(details.getValue().getOperator().equals("range")) {
				   if(StringUtils.isNumeric(details.getValue().getStartingValue()) && StringUtils.isNumeric(details.getValue().getEndingValue())) {
                	   builder.and(inventoryTransactions.products.id.between(Integer.valueOf(details.getValue().getStartingValue()), Integer.valueOf(details.getValue().getEndingValue())));
                   } else if(StringUtils.isNumeric(details.getValue().getStartingValue())) {
                	   builder.and(inventoryTransactions.products.id.goe(Integer.valueOf(details.getValue().getStartingValue())));
                   } else if(StringUtils.isNumeric(details.getValue().getEndingValue())) {
                	   builder.and(inventoryTransactions.products.id.loe(Integer.valueOf(details.getValue().getEndingValue())));
				   }
				}
			}
		}
		
		for (Map.Entry<String, String> joinCol : joinColumns.entrySet()) {
		if(joinCol != null && joinCol.getKey().equals("productId")) {
		    builder.and(inventoryTransactions.products.id.eq(Integer.parseInt(joinCol.getValue())));
		}
        
		if(joinCol != null && joinCol.getKey().equals("products")) {
		    builder.and(inventoryTransactions.products.id.eq(Integer.parseInt(joinCol.getValue())));
        }
        }
		return builder;
	}
	
    
}



