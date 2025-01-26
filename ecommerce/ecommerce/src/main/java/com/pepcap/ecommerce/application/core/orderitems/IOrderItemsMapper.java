package com.pepcap.ecommerce.application.core.orderitems;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import com.pepcap.ecommerce.domain.core.orders.Orders;
import com.pepcap.ecommerce.domain.core.products.Products;
import com.pepcap.ecommerce.application.core.orderitems.dto.*;
import com.pepcap.ecommerce.domain.core.orderitems.OrderItems;
import java.time.*;

@Mapper(componentModel = "spring")
public interface IOrderItemsMapper {
   OrderItems createOrderItemsInputToOrderItems(CreateOrderItemsInput orderItemsDto);
   
   @Mappings({ 
   @Mapping(source = "entity.orders.id", target = "orderId"),                   
   @Mapping(source = "entity.orders.id", target = "ordersDescriptiveField"),                    
   @Mapping(source = "entity.products.id", target = "productId"),                   
   @Mapping(source = "entity.products.id", target = "productsDescriptiveField"),                    
   }) 
   CreateOrderItemsOutput orderItemsToCreateOrderItemsOutput(OrderItems entity);
   
    OrderItems updateOrderItemsInputToOrderItems(UpdateOrderItemsInput orderItemsDto);
    
    @Mappings({ 
    @Mapping(source = "entity.orders.id", target = "orderId"),                   
    @Mapping(source = "entity.orders.id", target = "ordersDescriptiveField"),                    
    @Mapping(source = "entity.products.id", target = "productId"),                   
    @Mapping(source = "entity.products.id", target = "productsDescriptiveField"),                    
   	}) 
   	UpdateOrderItemsOutput orderItemsToUpdateOrderItemsOutput(OrderItems entity);
   	@Mappings({ 
   	@Mapping(source = "entity.orders.id", target = "orderId"),                   
   	@Mapping(source = "entity.orders.id", target = "ordersDescriptiveField"),                    
   	@Mapping(source = "entity.products.id", target = "productId"),                   
   	@Mapping(source = "entity.products.id", target = "productsDescriptiveField"),                    
   	}) 
   	FindOrderItemsByIdOutput orderItemsToFindOrderItemsByIdOutput(OrderItems entity);


   @Mappings({
   @Mapping(source = "orders.id", target = "id"),                  
   @Mapping(source = "foundOrderItems.id", target = "orderItemsId"),
   })
   GetOrdersOutput ordersToGetOrdersOutput(Orders orders, OrderItems foundOrderItems);
   
   @Mappings({
   @Mapping(source = "products.id", target = "id"),                  
   @Mapping(source = "products.price", target = "price"),                  
   @Mapping(source = "foundOrderItems.id", target = "orderItemsId"),
   })
   GetProductsOutput productsToGetProductsOutput(Products products, OrderItems foundOrderItems);
   
}

