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
import com.pepcap.inventorymanagement.application.core.products.IProductsAppService;
import com.pepcap.inventorymanagement.application.core.products.dto.*;
import com.pepcap.inventorymanagement.application.core.categories.ICategoriesAppService;
import com.pepcap.inventorymanagement.application.core.inventorytransactions.IInventoryTransactionsAppService;
import com.pepcap.inventorymanagement.application.core.inventorytransactions.dto.FindInventoryTransactionsByIdOutput;
import java.util.*;
import java.time.*;
import java.net.MalformedURLException;
import com.pepcap.inventorymanagement.commons.logging.LoggingHelper;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductsController {

	@Qualifier("productsAppService")
	@NonNull protected final IProductsAppService _productsAppService;
    @Qualifier("categoriesAppService")
	@NonNull  protected final ICategoriesAppService  _categoriesAppService;

    @Qualifier("inventoryTransactionsAppService")
	@NonNull  protected final IInventoryTransactionsAppService  _inventoryTransactionsAppService;

	@NonNull protected final LoggingHelper logHelper;

	@NonNull protected final Environment env;

	@RequestMapping(method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
	public ResponseEntity<CreateProductsOutput> create( @RequestBody @Valid CreateProductsInput products) {
		CreateProductsOutput output=_productsAppService.create(products);
		return new ResponseEntity<>(output, HttpStatus.OK);
	}

	// ------------ Delete products ------------
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, consumes = {"application/json"})
	public void delete(@PathVariable String id) {

    	FindProductsByIdOutput output = _productsAppService.findById(Integer.valueOf(id));
    	if(output == null) {
    		throw new EntityNotFoundException(String.format("There does not exist a products with a id=%s", id));
    	}	

    	_productsAppService.delete(Integer.valueOf(id));
    }


	// ------------ Update products ------------
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = {"application/json"}, produces = {"application/json"})
	public ResponseEntity<UpdateProductsOutput> update(@PathVariable String id,  @RequestBody @Valid UpdateProductsInput products) {

	    FindProductsByIdOutput currentProducts = _productsAppService.findById(Integer.valueOf(id));
		if(currentProducts == null) {
			throw new EntityNotFoundException(String.format("Unable to update. Products with id=%s not found.", id));
		}

		products.setVersiono(currentProducts.getVersiono());
	    UpdateProductsOutput output = _productsAppService.update(Integer.valueOf(id),products);
		return new ResponseEntity<>(output, HttpStatus.OK);
	}
    

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, consumes = {"application/json"}, produces = {"application/json"})
	public ResponseEntity<FindProductsByIdOutput> findById(@PathVariable String id) {

    	FindProductsByIdOutput output = _productsAppService.findById(Integer.valueOf(id));
        if(output == null) {
    		throw new EntityNotFoundException("Not found");
    	}
    	
		return new ResponseEntity<>(output, HttpStatus.OK);
	}
	@RequestMapping(method = RequestMethod.GET, consumes = {"application/json"}, produces = {"application/json"})
	public ResponseEntity<List<FindProductsByIdOutput>> find(@RequestParam(value="search", required=false) String search, @RequestParam(value = "offset", required=false) String offset, @RequestParam(value = "limit", required=false) String limit, Sort sort) throws EntityNotFoundException, MalformedURLException {

		if (offset == null) { offset = env.getProperty("pepcap.offset.default"); }
		if (limit == null) { limit = env.getProperty("pepcap.limit.default"); }

		Pageable Pageable = new OffsetBasedPageRequest(Integer.parseInt(offset), Integer.parseInt(limit), sort);
		SearchCriteria searchCriteria = SearchUtils.generateSearchCriteriaObject(search);

		return new ResponseEntity<>(_productsAppService.find(searchCriteria,Pageable), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{id}/categories", method = RequestMethod.GET, consumes = {"application/json"}, produces = {"application/json"})
	public ResponseEntity<GetCategoriesOutput> getCategories(@PathVariable String id) {
    	GetCategoriesOutput output= _productsAppService.getCategories(Integer.valueOf(id));
		if(output ==null) {
			throw new EntityNotFoundException("Not found");
	    }		

		return new ResponseEntity<>(output, HttpStatus.OK);
	}
	@RequestMapping(value = "/{id}/inventoryTransactions", method = RequestMethod.GET, consumes = {"application/json"}, produces = {"application/json"})
	public ResponseEntity<List<FindInventoryTransactionsByIdOutput>> getInventoryTransactions(@PathVariable String id, @RequestParam(value="search", required=false) String search, @RequestParam(value = "offset", required=false) String offset, @RequestParam(value = "limit", required=false) String limit, Sort sort) throws EntityNotFoundException, MalformedURLException {
   		if (offset == null) { offset = env.getProperty("pepcap.offset.default"); }
		if (limit == null) { limit = env.getProperty("pepcap.limit.default"); }

		Pageable pageable = new OffsetBasedPageRequest(Integer.parseInt(offset), Integer.parseInt(limit), sort);

		SearchCriteria searchCriteria = SearchUtils.generateSearchCriteriaObject(search);
		Map<String,String> joinColDetails=_productsAppService.parseInventoryTransactionsJoinColumn(id);
		if(joinColDetails == null) {
			throw new EntityNotFoundException("Invalid join column");
		}

		searchCriteria.setJoinColumns(joinColDetails);

    	List<FindInventoryTransactionsByIdOutput> output = _inventoryTransactionsAppService.find(searchCriteria,pageable);
    	
    	if(output == null) {
			throw new EntityNotFoundException("Not found");
		}
		
		return new ResponseEntity<>(output, HttpStatus.OK);
	}
}


