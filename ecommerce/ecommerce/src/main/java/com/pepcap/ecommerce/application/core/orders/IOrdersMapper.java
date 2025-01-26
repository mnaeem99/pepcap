package com.pepcap.ecommerce.application.core.orders;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import com.pepcap.ecommerce.domain.core.authorization.users.Users;
import com.pepcap.ecommerce.application.core.orders.dto.*;
import com.pepcap.ecommerce.domain.core.orders.Orders;
import java.time.*;

@Mapper(componentModel = "spring")
public interface IOrdersMapper {
   Orders createOrdersInputToOrders(CreateOrdersInput ordersDto);
   
   @Mappings({ 
   @Mapping(source = "entity.users.id", target = "userId"),                   
   @Mapping(source = "entity.users.id", target = "usersDescriptiveField"),                    
   }) 
   CreateOrdersOutput ordersToCreateOrdersOutput(Orders entity);
   
    Orders updateOrdersInputToOrders(UpdateOrdersInput ordersDto);
    
    @Mappings({ 
    @Mapping(source = "entity.users.id", target = "userId"),                   
    @Mapping(source = "entity.users.id", target = "usersDescriptiveField"),                    
   	}) 
   	UpdateOrdersOutput ordersToUpdateOrdersOutput(Orders entity);
   	@Mappings({ 
   	@Mapping(source = "entity.users.id", target = "userId"),                   
   	@Mapping(source = "entity.users.id", target = "usersDescriptiveField"),                    
   	}) 
   	FindOrdersByIdOutput ordersToFindOrdersByIdOutput(Orders entity);


   @Mappings({
   @Mapping(source = "users.createdAt", target = "createdAt"),                  
   @Mapping(source = "users.id", target = "id"),                  
   @Mapping(source = "foundOrders.id", target = "ordersId"),
   })
   GetUsersOutput usersToGetUsersOutput(Users users, Orders foundOrders);
   
}

