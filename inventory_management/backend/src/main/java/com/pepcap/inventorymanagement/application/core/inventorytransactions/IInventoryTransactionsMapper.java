package com.pepcap.inventorymanagement.application.core.inventorytransactions;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import com.pepcap.inventorymanagement.domain.core.products.Products;
import com.pepcap.inventorymanagement.application.core.inventorytransactions.dto.*;
import com.pepcap.inventorymanagement.domain.core.inventorytransactions.InventoryTransactions;
import java.time.*;

@Mapper(componentModel = "spring")
public interface IInventoryTransactionsMapper {
   InventoryTransactions createInventoryTransactionsInputToInventoryTransactions(CreateInventoryTransactionsInput inventoryTransactionsDto);
   
   @Mappings({ 
   @Mapping(source = "entity.products.id", target = "productId"),                   
   @Mapping(source = "entity.products.id", target = "productsDescriptiveField"),                    
   }) 
   CreateInventoryTransactionsOutput inventoryTransactionsToCreateInventoryTransactionsOutput(InventoryTransactions entity);
   
    InventoryTransactions updateInventoryTransactionsInputToInventoryTransactions(UpdateInventoryTransactionsInput inventoryTransactionsDto);
    
    @Mappings({ 
    @Mapping(source = "entity.products.id", target = "productId"),                   
    @Mapping(source = "entity.products.id", target = "productsDescriptiveField"),                    
   	}) 
   	UpdateInventoryTransactionsOutput inventoryTransactionsToUpdateInventoryTransactionsOutput(InventoryTransactions entity);
   	@Mappings({ 
   	@Mapping(source = "entity.products.id", target = "productId"),                   
   	@Mapping(source = "entity.products.id", target = "productsDescriptiveField"),                    
   	}) 
   	FindInventoryTransactionsByIdOutput inventoryTransactionsToFindInventoryTransactionsByIdOutput(InventoryTransactions entity);


   @Mappings({
   @Mapping(source = "products.createdAt", target = "createdAt"),                  
   @Mapping(source = "products.id", target = "id"),                  
   @Mapping(source = "foundInventoryTransactions.id", target = "inventoryTransactionsId"),
   })
   GetProductsOutput productsToGetProductsOutput(Products products, InventoryTransactions foundInventoryTransactions);
   
}

