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
import com.pepcap.inventorymanagement.application.core.categories.ICategoriesAppService;
import com.pepcap.inventorymanagement.application.core.categories.dto.*;
import com.pepcap.inventorymanagement.application.core.products.IProductsAppService;
import com.pepcap.inventorymanagement.application.core.products.dto.FindProductsByIdOutput;
import java.util.*;
import java.time.*;
import java.net.MalformedURLException;
import com.pepcap.inventorymanagement.commons.logging.LoggingHelper;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoriesController {

	@Qualifier("categoriesAppService")
	@NonNull protected final ICategoriesAppService _categoriesAppService;
    @Qualifier("productsAppService")
	@NonNull  protected final IProductsAppService  _productsAppService;

	@NonNull protected final LoggingHelper logHelper;

	@NonNull protected final Environment env;

	@RequestMapping(method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
	public ResponseEntity<CreateCategoriesOutput> create( @RequestBody @Valid CreateCategoriesInput categories) {
		CreateCategoriesOutput output=_categoriesAppService.create(categories);
		return new ResponseEntity<>(output, HttpStatus.OK);
	}

	// ------------ Delete categories ------------
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, consumes = {"application/json"})
	public void delete(@PathVariable String id) {

    	FindCategoriesByIdOutput output = _categoriesAppService.findById(Integer.valueOf(id));
    	if(output == null) {
    		throw new EntityNotFoundException(String.format("There does not exist a categories with a id=%s", id));
    	}	

    	_categoriesAppService.delete(Integer.valueOf(id));
    }


	// ------------ Update categories ------------
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = {"application/json"}, produces = {"application/json"})
	public ResponseEntity<UpdateCategoriesOutput> update(@PathVariable String id,  @RequestBody @Valid UpdateCategoriesInput categories) {

	    FindCategoriesByIdOutput currentCategories = _categoriesAppService.findById(Integer.valueOf(id));
		if(currentCategories == null) {
			throw new EntityNotFoundException(String.format("Unable to update. Categories with id=%s not found.", id));
		}

		categories.setVersiono(currentCategories.getVersiono());
	    UpdateCategoriesOutput output = _categoriesAppService.update(Integer.valueOf(id),categories);
		return new ResponseEntity<>(output, HttpStatus.OK);
	}
    

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, consumes = {"application/json"}, produces = {"application/json"})
	public ResponseEntity<FindCategoriesByIdOutput> findById(@PathVariable String id) {

    	FindCategoriesByIdOutput output = _categoriesAppService.findById(Integer.valueOf(id));
        if(output == null) {
    		throw new EntityNotFoundException("Not found");
    	}
    	
		return new ResponseEntity<>(output, HttpStatus.OK);
	}
	@RequestMapping(method = RequestMethod.GET, consumes = {"application/json"}, produces = {"application/json"})
	public ResponseEntity<List<FindCategoriesByIdOutput>> find(@RequestParam(value="search", required=false) String search, @RequestParam(value = "offset", required=false) String offset, @RequestParam(value = "limit", required=false) String limit, Sort sort) throws EntityNotFoundException, MalformedURLException {

		if (offset == null) { offset = env.getProperty("pepcap.offset.default"); }
		if (limit == null) { limit = env.getProperty("pepcap.limit.default"); }

		Pageable Pageable = new OffsetBasedPageRequest(Integer.parseInt(offset), Integer.parseInt(limit), sort);
		SearchCriteria searchCriteria = SearchUtils.generateSearchCriteriaObject(search);

		return new ResponseEntity<>(_categoriesAppService.find(searchCriteria,Pageable), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{id}/products", method = RequestMethod.GET, consumes = {"application/json"}, produces = {"application/json"})
	public ResponseEntity<List<FindProductsByIdOutput>> getProducts(@PathVariable String id, @RequestParam(value="search", required=false) String search, @RequestParam(value = "offset", required=false) String offset, @RequestParam(value = "limit", required=false) String limit, Sort sort) throws EntityNotFoundException, MalformedURLException {
   		if (offset == null) { offset = env.getProperty("pepcap.offset.default"); }
		if (limit == null) { limit = env.getProperty("pepcap.limit.default"); }

		Pageable pageable = new OffsetBasedPageRequest(Integer.parseInt(offset), Integer.parseInt(limit), sort);

		SearchCriteria searchCriteria = SearchUtils.generateSearchCriteriaObject(search);
		Map<String,String> joinColDetails=_categoriesAppService.parseProductsJoinColumn(id);
		if(joinColDetails == null) {
			throw new EntityNotFoundException("Invalid join column");
		}

		searchCriteria.setJoinColumns(joinColDetails);

    	List<FindProductsByIdOutput> output = _productsAppService.find(searchCriteria,pageable);
    	
    	if(output == null) {
			throw new EntityNotFoundException("Not found");
		}
		
		return new ResponseEntity<>(output, HttpStatus.OK);
	}
}


