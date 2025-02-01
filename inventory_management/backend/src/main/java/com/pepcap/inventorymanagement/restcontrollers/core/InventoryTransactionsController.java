package com.pepcap.inventorymanagement.restcontrollers.core;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.pepcap.inventorymanagement.commons.search.SearchCriteria;
import com.pepcap.inventorymanagement.commons.search.SearchUtils;
import com.pepcap.inventorymanagement.commons.search.OffsetBasedPageRequest;
import com.pepcap.inventorymanagement.application.core.inventorytransactions.IInventoryTransactionsAppService;
import com.pepcap.inventorymanagement.application.core.inventorytransactions.dto.*;
import com.pepcap.inventorymanagement.application.core.products.IProductsAppService;
import java.util.*;
import java.time.*;
import java.net.MalformedURLException;
import com.pepcap.inventorymanagement.commons.logging.LoggingHelper;

@RestController
@RequestMapping("/inventoryTransactions")
@RequiredArgsConstructor
public class InventoryTransactionsController {

	@Qualifier("inventoryTransactionsAppService")
	@NonNull protected final IInventoryTransactionsAppService _inventoryTransactionsAppService;
    @Qualifier("productsAppService")
	@NonNull  protected final IProductsAppService  _productsAppService;

	@NonNull protected final LoggingHelper logHelper;

	@NonNull protected final Environment env;

	@RequestMapping(method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
	public ResponseEntity<CreateInventoryTransactionsOutput> create( @RequestBody @Valid CreateInventoryTransactionsInput inventoryTransactions) {
		CreateInventoryTransactionsOutput output=_inventoryTransactionsAppService.create(inventoryTransactions);
		return new ResponseEntity<>(output, HttpStatus.OK);
	}

	// ------------ Delete inventoryTransactions ------------
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, consumes = {"application/json"})
	public void delete(@PathVariable String id) {

    	FindInventoryTransactionsByIdOutput output = _inventoryTransactionsAppService.findById(Integer.valueOf(id));
    	if(output == null) {
    		throw new EntityNotFoundException(String.format("There does not exist a inventoryTransactions with a id=%s", id));
    	}	

    	_inventoryTransactionsAppService.delete(Integer.valueOf(id));
    }


	// ------------ Update inventoryTransactions ------------
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = {"application/json"}, produces = {"application/json"})
	public ResponseEntity<UpdateInventoryTransactionsOutput> update(@PathVariable String id,  @RequestBody @Valid UpdateInventoryTransactionsInput inventoryTransactions) {

	    FindInventoryTransactionsByIdOutput currentInventoryTransactions = _inventoryTransactionsAppService.findById(Integer.valueOf(id));
		if(currentInventoryTransactions == null) {
			throw new EntityNotFoundException(String.format("Unable to update. InventoryTransactions with id=%s not found.", id));
		}

		inventoryTransactions.setVersiono(currentInventoryTransactions.getVersiono());
	    UpdateInventoryTransactionsOutput output = _inventoryTransactionsAppService.update(Integer.valueOf(id),inventoryTransactions);
		return new ResponseEntity<>(output, HttpStatus.OK);
	}
    

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, consumes = {"application/json"}, produces = {"application/json"})
	public ResponseEntity<FindInventoryTransactionsByIdOutput> findById(@PathVariable String id) {

    	FindInventoryTransactionsByIdOutput output = _inventoryTransactionsAppService.findById(Integer.valueOf(id));
        if(output == null) {
    		throw new EntityNotFoundException("Not found");
    	}
    	
		return new ResponseEntity<>(output, HttpStatus.OK);
	}
	@RequestMapping(method = RequestMethod.GET, consumes = {"application/json"}, produces = {"application/json"})
	public ResponseEntity<List<FindInventoryTransactionsByIdOutput>> find(@RequestParam(value="search", required=false) String search, @RequestParam(value = "offset", required=false) String offset, @RequestParam(value = "limit", required=false) String limit, Sort sort) throws EntityNotFoundException, MalformedURLException {

		if (offset == null) { offset = env.getProperty("pepcap.offset.default"); }
		if (limit == null) { limit = env.getProperty("pepcap.limit.default"); }

		Pageable Pageable = new OffsetBasedPageRequest(Integer.parseInt(offset), Integer.parseInt(limit), sort);
		SearchCriteria searchCriteria = SearchUtils.generateSearchCriteriaObject(search);

		return new ResponseEntity<>(_inventoryTransactionsAppService.find(searchCriteria,Pageable), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{id}/products", method = RequestMethod.GET, consumes = {"application/json"}, produces = {"application/json"})
	public ResponseEntity<GetProductsOutput> getProducts(@PathVariable String id) {
    	GetProductsOutput output= _inventoryTransactionsAppService.getProducts(Integer.valueOf(id));
		if(output ==null) {
			throw new EntityNotFoundException("Not found");
	    }		

		return new ResponseEntity<>(output, HttpStatus.OK);
	}
}


