package com.pepcap.ecommerce.application.core.products;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import com.pepcap.ecommerce.domain.core.categories.Categories;
import com.pepcap.ecommerce.application.core.products.dto.*;
import com.pepcap.ecommerce.domain.core.products.Products;
import java.time.*;

@Mapper(componentModel = "spring")
public interface IProductsMapper {
   Products createProductsInputToProducts(CreateProductsInput productsDto);
   
   @Mappings({ 
   @Mapping(source = "entity.categories.id", target = "categoryId"),                   
   @Mapping(source = "entity.categories.id", target = "categoriesDescriptiveField"),                    
   }) 
   CreateProductsOutput productsToCreateProductsOutput(Products entity);
   
    Products updateProductsInputToProducts(UpdateProductsInput productsDto);
    
    @Mappings({ 
    @Mapping(source = "entity.categories.id", target = "categoryId"),                   
    @Mapping(source = "entity.categories.id", target = "categoriesDescriptiveField"),                    
   	}) 
   	UpdateProductsOutput productsToUpdateProductsOutput(Products entity);
   	@Mappings({ 
   	@Mapping(source = "entity.categories.id", target = "categoryId"),                   
   	@Mapping(source = "entity.categories.id", target = "categoriesDescriptiveField"),                    
   	}) 
   	FindProductsByIdOutput productsToFindProductsByIdOutput(Products entity);


   @Mappings({
   @Mapping(source = "categories.createdAt", target = "createdAt"),                  
   @Mapping(source = "categories.description", target = "description"),                  
   @Mapping(source = "categories.id", target = "id"),                  
   @Mapping(source = "categories.name", target = "name"),                  
   @Mapping(source = "categories.updatedAt", target = "updatedAt"),                  
   @Mapping(source = "foundProducts.id", target = "productsId"),
   })
   GetCategoriesOutput categoriesToGetCategoriesOutput(Categories categories, Products foundProducts);
   
}

