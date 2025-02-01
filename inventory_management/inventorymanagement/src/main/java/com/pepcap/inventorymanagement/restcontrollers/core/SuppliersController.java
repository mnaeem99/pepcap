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
import com.pepcap.inventorymanagement.application.core.suppliers.ISuppliersAppService;
import com.pepcap.inventorymanagement.application.core.suppliers.dto.*;
import java.util.*;
import java.time.*;
import java.net.MalformedURLException;
import com.pepcap.inventorymanagement.commons.logging.LoggingHelper;

@RestController
@RequestMapping("/suppliers")
@RequiredArgsConstructor
public class SuppliersController {

	@Qualifier("suppliersAppService")
	@NonNull protected final ISuppliersAppService _suppliersAppService;
	@NonNull protected final LoggingHelper logHelper;

	@NonNull protected final Environment env;

	@RequestMapping(method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
	public ResponseEntity<CreateSuppliersOutput> create( @RequestBody @Valid CreateSuppliersInput suppliers) {
		CreateSuppliersOutput output=_suppliersAppService.create(suppliers);
		return new ResponseEntity<>(output, HttpStatus.OK);
	}

	// ------------ Delete suppliers ------------
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, consumes = {"application/json"})
	public void delete(@PathVariable String id) {

    	FindSuppliersByIdOutput output = _suppliersAppService.findById(Integer.valueOf(id));
    	if(output == null) {
    		throw new EntityNotFoundException(String.format("There does not exist a suppliers with a id=%s", id));
    	}	

    	_suppliersAppService.delete(Integer.valueOf(id));
    }


	// ------------ Update suppliers ------------
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = {"application/json"}, produces = {"application/json"})
	public ResponseEntity<UpdateSuppliersOutput> update(@PathVariable String id,  @RequestBody @Valid UpdateSuppliersInput suppliers) {

	    FindSuppliersByIdOutput currentSuppliers = _suppliersAppService.findById(Integer.valueOf(id));
		if(currentSuppliers == null) {
			throw new EntityNotFoundException(String.format("Unable to update. Suppliers with id=%s not found.", id));
		}

		suppliers.setVersiono(currentSuppliers.getVersiono());
	    UpdateSuppliersOutput output = _suppliersAppService.update(Integer.valueOf(id),suppliers);
		return new ResponseEntity<>(output, HttpStatus.OK);
	}
    

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, consumes = {"application/json"}, produces = {"application/json"})
	public ResponseEntity<FindSuppliersByIdOutput> findById(@PathVariable String id) {

    	FindSuppliersByIdOutput output = _suppliersAppService.findById(Integer.valueOf(id));
        if(output == null) {
    		throw new EntityNotFoundException("Not found");
    	}
    	
		return new ResponseEntity<>(output, HttpStatus.OK);
	}
	@RequestMapping(method = RequestMethod.GET, consumes = {"application/json"}, produces = {"application/json"})
	public ResponseEntity<List<FindSuppliersByIdOutput>> find(@RequestParam(value="search", required=false) String search, @RequestParam(value = "offset", required=false) String offset, @RequestParam(value = "limit", required=false) String limit, Sort sort) throws EntityNotFoundException, MalformedURLException {

		if (offset == null) { offset = env.getProperty("pepcap.offset.default"); }
		if (limit == null) { limit = env.getProperty("pepcap.limit.default"); }

		Pageable Pageable = new OffsetBasedPageRequest(Integer.parseInt(offset), Integer.parseInt(limit), sort);
		SearchCriteria searchCriteria = SearchUtils.generateSearchCriteriaObject(search);

		return new ResponseEntity<>(_suppliersAppService.find(searchCriteria,Pageable), HttpStatus.OK);
	}
	
}


