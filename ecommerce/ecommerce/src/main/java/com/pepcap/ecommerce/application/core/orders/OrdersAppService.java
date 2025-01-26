package com.pepcap.ecommerce.application.core.orders;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import com.pepcap.ecommerce.application.core.orders.dto.*;
import com.pepcap.ecommerce.domain.core.orders.IOrdersRepository;
import com.pepcap.ecommerce.domain.core.orders.QOrders;
import com.pepcap.ecommerce.domain.core.orders.Orders;
import com.pepcap.ecommerce.domain.core.authorization.users.IUsersRepository;
import com.pepcap.ecommerce.domain.core.authorization.users.Users;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page; 
import org.springframework.data.domain.Pageable; 
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import com.pepcap.ecommerce.commons.search.*;
import com.pepcap.ecommerce.commons.logging.LoggingHelper;
import com.querydsl.core.BooleanBuilder;
import java.net.MalformedURLException;
import java.math.BigDecimal;
import java.time.*;
import java.util.*;
import javax.persistence.EntityNotFoundException;

@Service("ordersAppService")
@RequiredArgsConstructor
public class OrdersAppService implements IOrdersAppService {
    
	@Qualifier("ordersRepository")
	@NonNull protected final IOrdersRepository _ordersRepository;

	
    @Qualifier("usersRepository")
	@NonNull protected final IUsersRepository _usersRepository;

	@Qualifier("IOrdersMapperImpl")
	@NonNull protected final IOrdersMapper mapper;

	@NonNull protected final LoggingHelper logHelper;

    @Transactional(propagation = Propagation.REQUIRED)
	public CreateOrdersOutput create(CreateOrdersInput input) {

		Orders orders = mapper.createOrdersInputToOrders(input);
		Users foundUsers = null;
	  	if(input.getUserId()!=null) {
			foundUsers = _usersRepository.findById(input.getUserId()).orElse(null);
			
			if(foundUsers!=null) {
				foundUsers.addOrders(orders);
			}
		}

		Orders createdOrders = _ordersRepository.save(orders);
		return mapper.ordersToCreateOrdersOutput(createdOrders);
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public UpdateOrdersOutput update(Integer ordersId, UpdateOrdersInput input) {

		Orders existing = _ordersRepository.findById(ordersId).orElseThrow(() -> new EntityNotFoundException("Orders not found"));

		Orders orders = mapper.updateOrdersInputToOrders(input);
		orders.setOrderItemsSet(existing.getOrderItemsSet());
		Users foundUsers = null;
        
	  	if(input.getUserId()!=null) { 
			foundUsers = _usersRepository.findById(input.getUserId()).orElse(null);
		
			if(foundUsers!=null) {
				foundUsers.addOrders(orders);
			}
		}
		
		Orders updatedOrders = _ordersRepository.save(orders);
		return mapper.ordersToUpdateOrdersOutput(updatedOrders);
	}
	
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(Integer ordersId) {

		Orders existing = _ordersRepository.findById(ordersId).orElseThrow(() -> new EntityNotFoundException("Orders not found"));
	 	
        if(existing.getUsers() !=null)
        {
        existing.getUsers().removeOrders(existing);
        }
        if(existing !=null) {
			_ordersRepository.delete(existing);
		}
	}
	
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public FindOrdersByIdOutput findById(Integer ordersId) {

		Orders foundOrders = _ordersRepository.findById(ordersId).orElse(null);
		if (foundOrders == null)  
			return null; 
 	   
 	    return mapper.ordersToFindOrdersByIdOutput(foundOrders);
	}

    //Users
	// ReST API Call - GET /orders/1/users
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
	public GetUsersOutput getUsers(Integer ordersId) {

		Orders foundOrders = _ordersRepository.findById(ordersId).orElse(null);
		if (foundOrders == null) {
			logHelper.getLogger().error("There does not exist a orders wth a id='{}'", ordersId);
			return null;
		}
		Users re = foundOrders.getUsers();
		return mapper.usersToGetUsersOutput(re, foundOrders);
	}
	
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<FindOrdersByIdOutput> find(SearchCriteria search, Pageable pageable) throws MalformedURLException  {

		Page<Orders> foundOrders = _ordersRepository.findAll(search(search), pageable);
		List<Orders> ordersList = foundOrders.getContent();
		Iterator<Orders> ordersIterator = ordersList.iterator(); 
		List<FindOrdersByIdOutput> output = new ArrayList<>();

		while (ordersIterator.hasNext()) {
		Orders orders = ordersIterator.next();
 	    output.add(mapper.ordersToFindOrdersByIdOutput(orders));
		}
		return output;
	}
	
	protected BooleanBuilder search(SearchCriteria search) throws MalformedURLException {

		QOrders orders= QOrders.ordersEntity;
		if(search != null) {
			Map<String,SearchFields> map = new HashMap<>();
			for(SearchFields fieldDetails: search.getFields())
			{
				map.put(fieldDetails.getFieldName(),fieldDetails);
			}
			List<String> keysList = new ArrayList<String>(map.keySet());
			checkProperties(keysList);
			return searchKeyValuePair(orders, map,search.getJoinColumns());
		}
		return null;
	}
	
	protected void checkProperties(List<String> list) throws MalformedURLException  {
		for (int i = 0; i < list.size(); i++) {
			if(!(
		        list.get(i).replace("%20","").trim().equals("users") ||
				list.get(i).replace("%20","").trim().equals("userId") ||
				list.get(i).replace("%20","").trim().equals("createdAt") ||
				list.get(i).replace("%20","").trim().equals("id") ||
				list.get(i).replace("%20","").trim().equals("status") ||
				list.get(i).replace("%20","").trim().equals("totalAmount")
			)) 
			{
			 throw new MalformedURLException("Wrong URL Format: Property " + list.get(i) + " not found!" );
			}
		}
	}
	
	protected BooleanBuilder searchKeyValuePair(QOrders orders, Map<String,SearchFields> map,Map<String,String> joinColumns) {
		BooleanBuilder builder = new BooleanBuilder();
        
		Iterator<Map.Entry<String, SearchFields>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, SearchFields> details = iterator.next();

			if(details.getKey().replace("%20","").trim().equals("createdAt")) {
				if(details.getValue().getOperator().equals("equals") && SearchUtils.stringToLocalDateTime(details.getValue().getSearchValue()) !=null) {
					builder.and(orders.createdAt.eq(SearchUtils.stringToLocalDateTime(details.getValue().getSearchValue())));
				} else if(details.getValue().getOperator().equals("notEqual") && SearchUtils.stringToLocalDateTime(details.getValue().getSearchValue()) !=null) {
					builder.and(orders.createdAt.ne(SearchUtils.stringToLocalDateTime(details.getValue().getSearchValue())));
				} else if(details.getValue().getOperator().equals("range")) {
				   LocalDateTime startLocalDateTime= SearchUtils.stringToLocalDateTime(details.getValue().getStartingValue());
				   LocalDateTime endLocalDateTime= SearchUtils.stringToLocalDateTime(details.getValue().getEndingValue());
				   if(startLocalDateTime!=null && endLocalDateTime!=null) {
					   builder.and(orders.createdAt.between(startLocalDateTime,endLocalDateTime));
				   } else if(endLocalDateTime!=null) {
					   builder.and(orders.createdAt.loe(endLocalDateTime));
                   } else if(startLocalDateTime!=null) {
                	   builder.and(orders.createdAt.goe(startLocalDateTime));  
                   }
                }     
			}
			if(details.getKey().replace("%20","").trim().equals("id")) {
				if(details.getValue().getOperator().equals("contains") && StringUtils.isNumeric(details.getValue().getSearchValue())) {
					builder.and(orders.id.like(details.getValue().getSearchValue() + "%"));
				} else if(details.getValue().getOperator().equals("equals") && StringUtils.isNumeric(details.getValue().getSearchValue())) {
					builder.and(orders.id.eq(Integer.valueOf(details.getValue().getSearchValue())));
				} else if(details.getValue().getOperator().equals("notEqual") && StringUtils.isNumeric(details.getValue().getSearchValue())) {
					builder.and(orders.id.ne(Integer.valueOf(details.getValue().getSearchValue())));
				} else if(details.getValue().getOperator().equals("range")) {
				   if(StringUtils.isNumeric(details.getValue().getStartingValue()) && StringUtils.isNumeric(details.getValue().getEndingValue())) {
                	   builder.and(orders.id.between(Integer.valueOf(details.getValue().getStartingValue()), Integer.valueOf(details.getValue().getEndingValue())));
                   } else if(StringUtils.isNumeric(details.getValue().getStartingValue())) {
                	   builder.and(orders.id.goe(Integer.valueOf(details.getValue().getStartingValue())));
                   } else if(StringUtils.isNumeric(details.getValue().getEndingValue())) {
                	   builder.and(orders.id.loe(Integer.valueOf(details.getValue().getEndingValue())));
				   }
				}
			}
            if(details.getKey().replace("%20","").trim().equals("status")) {
				if(details.getValue().getOperator().equals("contains")) {
					builder.and(orders.status.likeIgnoreCase("%"+ details.getValue().getSearchValue() + "%"));
				} else if(details.getValue().getOperator().equals("equals")) {
					builder.and(orders.status.eq(details.getValue().getSearchValue()));
				} else if(details.getValue().getOperator().equals("notEqual")) {
					builder.and(orders.status.ne(details.getValue().getSearchValue()));
				}
			}
			if(details.getKey().replace("%20","").trim().equals("totalAmount")) {
				if(details.getValue().getOperator().equals("contains") && NumberUtils.isCreatable(details.getValue().getSearchValue())) {
					builder.and(orders.totalAmount.like(details.getValue().getSearchValue() + "%"));
				} else if(details.getValue().getOperator().equals("equals") && NumberUtils.isCreatable(details.getValue().getSearchValue())) {
					builder.and(orders.totalAmount.eq(new BigDecimal(details.getValue().getSearchValue())));
				} else if(details.getValue().getOperator().equals("notEqual") && NumberUtils.isCreatable(details.getValue().getSearchValue())) {
					builder.and(orders.totalAmount.ne(new BigDecimal(details.getValue().getSearchValue())));
				} else if(details.getValue().getOperator().equals("range")) {
				   if(NumberUtils.isCreatable(details.getValue().getStartingValue()) && NumberUtils.isCreatable(details.getValue().getEndingValue())) {
                	   builder.and(orders.totalAmount.between(new BigDecimal(details.getValue().getStartingValue()), new BigDecimal(details.getValue().getEndingValue())));
                   } else if(NumberUtils.isCreatable(details.getValue().getStartingValue())) {
                	   builder.and(orders.totalAmount.goe(new BigDecimal(details.getValue().getStartingValue())));
                   } else if(NumberUtils.isCreatable(details.getValue().getEndingValue())) {
                	   builder.and(orders.totalAmount.loe(new BigDecimal(details.getValue().getEndingValue())));
				   }
				}
			}		
	    
		     if(details.getKey().replace("%20","").trim().equals("users")) {
				if(details.getValue().getOperator().equals("contains") && StringUtils.isNumeric(details.getValue().getSearchValue())) {
					builder.and(orders.users.id.like(details.getValue().getSearchValue() + "%"));
				} else if(details.getValue().getOperator().equals("equals") && StringUtils.isNumeric(details.getValue().getSearchValue())) {
					builder.and(orders.users.id.eq(Integer.valueOf(details.getValue().getSearchValue())));
				} else if(details.getValue().getOperator().equals("notEqual") && StringUtils.isNumeric(details.getValue().getSearchValue())) {
					builder.and(orders.users.id.ne(Integer.valueOf(details.getValue().getSearchValue())));
				} else if(details.getValue().getOperator().equals("range")) {
				   if(StringUtils.isNumeric(details.getValue().getStartingValue()) && StringUtils.isNumeric(details.getValue().getEndingValue())) {
                	   builder.and(orders.users.id.between(Integer.valueOf(details.getValue().getStartingValue()), Integer.valueOf(details.getValue().getEndingValue())));
                   } else if(StringUtils.isNumeric(details.getValue().getStartingValue())) {
                	   builder.and(orders.users.id.goe(Integer.valueOf(details.getValue().getStartingValue())));
                   } else if(StringUtils.isNumeric(details.getValue().getEndingValue())) {
                	   builder.and(orders.users.id.loe(Integer.valueOf(details.getValue().getEndingValue())));
				   }
				}
			}
		}
		
		for (Map.Entry<String, String> joinCol : joinColumns.entrySet()) {
		if(joinCol != null && joinCol.getKey().equals("userId")) {
		    builder.and(orders.users.id.eq(Integer.parseInt(joinCol.getValue())));
		}
        
		if(joinCol != null && joinCol.getKey().equals("users")) {
		    builder.and(orders.users.id.eq(Integer.parseInt(joinCol.getValue())));
        }
        }
		return builder;
	}
	
	public Map<String,String> parseOrderItemsJoinColumn(String keysString) {
		
		Map<String,String> joinColumnMap = new HashMap<String,String>();
		joinColumnMap.put("orderId", keysString);
		  
		return joinColumnMap;
	}
    
    
}



