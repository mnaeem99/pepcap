package com.pepcap.ecommerce.restcontrollers.core;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.pepcap.ecommerce.commons.search.SearchCriteria;
import com.pepcap.ecommerce.commons.search.SearchUtils;
import com.pepcap.ecommerce.commons.search.OffsetBasedPageRequest;
import com.pepcap.ecommerce.application.core.orders.IOrdersAppService;
import com.pepcap.ecommerce.application.core.orders.dto.*;
import com.pepcap.ecommerce.application.core.orderitems.IOrderItemsAppService;
import com.pepcap.ecommerce.application.core.orderitems.dto.FindOrderItemsByIdOutput;
import com.pepcap.ecommerce.application.core.authorization.users.IUsersAppService;
import java.util.*;
import java.time.*;
import java.net.MalformedURLException;
import com.pepcap.ecommerce.commons.logging.LoggingHelper;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrdersController {

	@Qualifier("ordersAppService")
	@NonNull protected final IOrdersAppService _ordersAppService;
    @Qualifier("orderItemsAppService")
	@NonNull  protected final IOrderItemsAppService  _orderItemsAppService;

    @Qualifier("usersAppService")
	@NonNull  protected final IUsersAppService  _usersAppService;

	@NonNull protected final LoggingHelper logHelper;

	@NonNull protected final Environment env;

    @PreAuthorize("hasAnyAuthority('ORDERSENTITY_CREATE')")
	@RequestMapping(method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
	public ResponseEntity<CreateOrdersOutput> create( @RequestBody @Valid CreateOrdersInput orders) {
		CreateOrdersOutput output=_ordersAppService.create(orders);
		return new ResponseEntity<>(output, HttpStatus.OK);
	}

	// ------------ Delete orders ------------
	@PreAuthorize("hasAnyAuthority('ORDERSENTITY_DELETE')")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, consumes = {"application/json"})
	public void delete(@PathVariable String id) {

    	FindOrdersByIdOutput output = _ordersAppService.findById(Integer.valueOf(id));
    	if(output == null) {
    		throw new EntityNotFoundException(String.format("There does not exist a orders with a id=%s", id));
    	}	

    	_ordersAppService.delete(Integer.valueOf(id));
    }


	// ------------ Update orders ------------
    @PreAuthorize("hasAnyAuthority('ORDERSENTITY_UPDATE')")
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = {"application/json"}, produces = {"application/json"})
	public ResponseEntity<UpdateOrdersOutput> update(@PathVariable String id,  @RequestBody @Valid UpdateOrdersInput orders) {

	    FindOrdersByIdOutput currentOrders = _ordersAppService.findById(Integer.valueOf(id));
		if(currentOrders == null) {
			throw new EntityNotFoundException(String.format("Unable to update. Orders with id=%s not found.", id));
		}

		orders.setVersiono(currentOrders.getVersiono());
	    UpdateOrdersOutput output = _ordersAppService.update(Integer.valueOf(id),orders);
		return new ResponseEntity<>(output, HttpStatus.OK);
	}
    

    @PreAuthorize("hasAnyAuthority('ORDERSENTITY_READ')")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, consumes = {"application/json"}, produces = {"application/json"})
	public ResponseEntity<FindOrdersByIdOutput> findById(@PathVariable String id) {

    	FindOrdersByIdOutput output = _ordersAppService.findById(Integer.valueOf(id));
        if(output == null) {
    		throw new EntityNotFoundException("Not found");
    	}
    	
		return new ResponseEntity<>(output, HttpStatus.OK);
	}
    @PreAuthorize("hasAnyAuthority('ORDERSENTITY_READ')")
	@RequestMapping(method = RequestMethod.GET, consumes = {"application/json"}, produces = {"application/json"})
	public ResponseEntity<List<FindOrdersByIdOutput>> find(@RequestParam(value="search", required=false) String search, @RequestParam(value = "offset", required=false) String offset, @RequestParam(value = "limit", required=false) String limit, Sort sort) throws EntityNotFoundException, MalformedURLException {

		if (offset == null) { offset = env.getProperty("pepcap.offset.default"); }
		if (limit == null) { limit = env.getProperty("pepcap.limit.default"); }

		Pageable Pageable = new OffsetBasedPageRequest(Integer.parseInt(offset), Integer.parseInt(limit), sort);
		SearchCriteria searchCriteria = SearchUtils.generateSearchCriteriaObject(search);

		return new ResponseEntity<>(_ordersAppService.find(searchCriteria,Pageable), HttpStatus.OK);
	}
	
    @PreAuthorize("hasAnyAuthority('ORDERSENTITY_READ')")
	@RequestMapping(value = "/{id}/orderItems", method = RequestMethod.GET, consumes = {"application/json"}, produces = {"application/json"})
	public ResponseEntity<List<FindOrderItemsByIdOutput>> getOrderItems(@PathVariable String id, @RequestParam(value="search", required=false) String search, @RequestParam(value = "offset", required=false) String offset, @RequestParam(value = "limit", required=false) String limit, Sort sort) throws EntityNotFoundException, MalformedURLException {
   		if (offset == null) { offset = env.getProperty("pepcap.offset.default"); }
		if (limit == null) { limit = env.getProperty("pepcap.limit.default"); }

		Pageable pageable = new OffsetBasedPageRequest(Integer.parseInt(offset), Integer.parseInt(limit), sort);

		SearchCriteria searchCriteria = SearchUtils.generateSearchCriteriaObject(search);
		Map<String,String> joinColDetails=_ordersAppService.parseOrderItemsJoinColumn(id);
		if(joinColDetails == null) {
			throw new EntityNotFoundException("Invalid join column");
		}

		searchCriteria.setJoinColumns(joinColDetails);

    	List<FindOrderItemsByIdOutput> output = _orderItemsAppService.find(searchCriteria,pageable);
    	
    	if(output == null) {
			throw new EntityNotFoundException("Not found");
		}
		
		return new ResponseEntity<>(output, HttpStatus.OK);
	}
    @PreAuthorize("hasAnyAuthority('ORDERSENTITY_READ')")
	@RequestMapping(value = "/{id}/users", method = RequestMethod.GET, consumes = {"application/json"}, produces = {"application/json"})
	public ResponseEntity<GetUsersOutput> getUsers(@PathVariable String id) {
    	GetUsersOutput output= _ordersAppService.getUsers(Integer.valueOf(id));
		if(output ==null) {
			throw new EntityNotFoundException("Not found");
	    }		

		return new ResponseEntity<>(output, HttpStatus.OK);
	}
}


