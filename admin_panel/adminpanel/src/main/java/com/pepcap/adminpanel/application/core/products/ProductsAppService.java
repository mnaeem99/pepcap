package com.pepcap.adminpanel.application.core.products;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import com.pepcap.adminpanel.application.core.products.dto.*;
import com.pepcap.adminpanel.domain.core.products.IProductsRepository;
import com.pepcap.adminpanel.domain.core.products.QProducts;
import com.pepcap.adminpanel.domain.core.products.Products;
import com.pepcap.adminpanel.domain.core.categories.ICategoriesRepository;
import com.pepcap.adminpanel.domain.core.categories.Categories;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page; 
import org.springframework.data.domain.Pageable; 
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import com.pepcap.adminpanel.commons.search.*;
import com.pepcap.adminpanel.commons.logging.LoggingHelper;
import com.querydsl.core.BooleanBuilder;
import java.net.MalformedURLException;
import java.math.BigDecimal;
import java.time.*;
import java.util.*;
import javax.persistence.EntityNotFoundException;

@Service("productsAppService")
@RequiredArgsConstructor
public class ProductsAppService implements IProductsAppService {
    
	@Qualifier("productsRepository")
	@NonNull protected final IProductsRepository _productsRepository;

	
    @Qualifier("categoriesRepository")
	@NonNull protected final ICategoriesRepository _categoriesRepository;

	@Qualifier("IProductsMapperImpl")
	@NonNull protected final IProductsMapper mapper;

	@NonNull protected final LoggingHelper logHelper;

    @Transactional(propagation = Propagation.REQUIRED)
	public CreateProductsOutput create(CreateProductsInput input) {

		Products products = mapper.createProductsInputToProducts(input);
		Categories foundCategories = null;
	  	if(input.getCategoryId()!=null) {
			foundCategories = _categoriesRepository.findById(input.getCategoryId()).orElse(null);
			
			if(foundCategories!=null) {
				foundCategories.addProducts(products);
			}
		}

		Products createdProducts = _productsRepository.save(products);
		return mapper.productsToCreateProductsOutput(createdProducts);
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public UpdateProductsOutput update(Integer productsId, UpdateProductsInput input) {

		Products existing = _productsRepository.findById(productsId).orElseThrow(() -> new EntityNotFoundException("Products not found"));

		Products products = mapper.updateProductsInputToProducts(input);
		Categories foundCategories = null;
        
	  	if(input.getCategoryId()!=null) { 
			foundCategories = _categoriesRepository.findById(input.getCategoryId()).orElse(null);
		
			if(foundCategories!=null) {
				foundCategories.addProducts(products);
			}
		}
		
		Products updatedProducts = _productsRepository.save(products);
		return mapper.productsToUpdateProductsOutput(updatedProducts);
	}
	
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(Integer productsId) {

		Products existing = _productsRepository.findById(productsId).orElseThrow(() -> new EntityNotFoundException("Products not found"));
	 	
        if(existing.getCategories() !=null)
        {
        existing.getCategories().removeProducts(existing);
        }
        if(existing !=null) {
			_productsRepository.delete(existing);
		}
	}
	
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public FindProductsByIdOutput findById(Integer productsId) {

		Products foundProducts = _productsRepository.findById(productsId).orElse(null);
		if (foundProducts == null)  
			return null; 
 	   
 	    return mapper.productsToFindProductsByIdOutput(foundProducts);
	}

    //Categories
	// ReST API Call - GET /products/1/categories
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
	public GetCategoriesOutput getCategories(Integer productsId) {

		Products foundProducts = _productsRepository.findById(productsId).orElse(null);
		if (foundProducts == null) {
			logHelper.getLogger().error("There does not exist a products wth a id='{}'", productsId);
			return null;
		}
		Categories re = foundProducts.getCategories();
		return mapper.categoriesToGetCategoriesOutput(re, foundProducts);
	}
	
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<FindProductsByIdOutput> find(SearchCriteria search, Pageable pageable) throws MalformedURLException  {

		Page<Products> foundProducts = _productsRepository.findAll(search(search), pageable);
		List<Products> productsList = foundProducts.getContent();
		Iterator<Products> productsIterator = productsList.iterator(); 
		List<FindProductsByIdOutput> output = new ArrayList<>();

		while (productsIterator.hasNext()) {
		Products products = productsIterator.next();
 	    output.add(mapper.productsToFindProductsByIdOutput(products));
		}
		return output;
	}
	
	protected BooleanBuilder search(SearchCriteria search) throws MalformedURLException {

		QProducts products= QProducts.productsEntity;
		if(search != null) {
			Map<String,SearchFields> map = new HashMap<>();
			for(SearchFields fieldDetails: search.getFields())
			{
				map.put(fieldDetails.getFieldName(),fieldDetails);
			}
			List<String> keysList = new ArrayList<String>(map.keySet());
			checkProperties(keysList);
			return searchKeyValuePair(products, map,search.getJoinColumns());
		}
		return null;
	}
	
	protected void checkProperties(List<String> list) throws MalformedURLException  {
		for (int i = 0; i < list.size(); i++) {
			if(!(
		        list.get(i).replace("%20","").trim().equals("categories") ||
				list.get(i).replace("%20","").trim().equals("categoryId") ||
				list.get(i).replace("%20","").trim().equals("createdAt") ||
				list.get(i).replace("%20","").trim().equals("description") ||
				list.get(i).replace("%20","").trim().equals("id") ||
				list.get(i).replace("%20","").trim().equals("name") ||
				list.get(i).replace("%20","").trim().equals("price") ||
				list.get(i).replace("%20","").trim().equals("stock") ||
				list.get(i).replace("%20","").trim().equals("updatedAt")
			)) 
			{
			 throw new MalformedURLException("Wrong URL Format: Property " + list.get(i) + " not found!" );
			}
		}
	}
	
	protected BooleanBuilder searchKeyValuePair(QProducts products, Map<String,SearchFields> map,Map<String,String> joinColumns) {
		BooleanBuilder builder = new BooleanBuilder();
        
		Iterator<Map.Entry<String, SearchFields>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, SearchFields> details = iterator.next();

			if(details.getKey().replace("%20","").trim().equals("createdAt")) {
				if(details.getValue().getOperator().equals("equals") && SearchUtils.stringToLocalDateTime(details.getValue().getSearchValue()) !=null) {
					builder.and(products.createdAt.eq(SearchUtils.stringToLocalDateTime(details.getValue().getSearchValue())));
				} else if(details.getValue().getOperator().equals("notEqual") && SearchUtils.stringToLocalDateTime(details.getValue().getSearchValue()) !=null) {
					builder.and(products.createdAt.ne(SearchUtils.stringToLocalDateTime(details.getValue().getSearchValue())));
				} else if(details.getValue().getOperator().equals("range")) {
				   LocalDateTime startLocalDateTime= SearchUtils.stringToLocalDateTime(details.getValue().getStartingValue());
				   LocalDateTime endLocalDateTime= SearchUtils.stringToLocalDateTime(details.getValue().getEndingValue());
				   if(startLocalDateTime!=null && endLocalDateTime!=null) {
					   builder.and(products.createdAt.between(startLocalDateTime,endLocalDateTime));
				   } else if(endLocalDateTime!=null) {
					   builder.and(products.createdAt.loe(endLocalDateTime));
                   } else if(startLocalDateTime!=null) {
                	   builder.and(products.createdAt.goe(startLocalDateTime));  
                   }
                }     
			}
            if(details.getKey().replace("%20","").trim().equals("description")) {
				if(details.getValue().getOperator().equals("contains")) {
					builder.and(products.description.likeIgnoreCase("%"+ details.getValue().getSearchValue() + "%"));
				} else if(details.getValue().getOperator().equals("equals")) {
					builder.and(products.description.eq(details.getValue().getSearchValue()));
				} else if(details.getValue().getOperator().equals("notEqual")) {
					builder.and(products.description.ne(details.getValue().getSearchValue()));
				}
			}
			if(details.getKey().replace("%20","").trim().equals("id")) {
				if(details.getValue().getOperator().equals("contains") && StringUtils.isNumeric(details.getValue().getSearchValue())) {
					builder.and(products.id.like(details.getValue().getSearchValue() + "%"));
				} else if(details.getValue().getOperator().equals("equals") && StringUtils.isNumeric(details.getValue().getSearchValue())) {
					builder.and(products.id.eq(Integer.valueOf(details.getValue().getSearchValue())));
				} else if(details.getValue().getOperator().equals("notEqual") && StringUtils.isNumeric(details.getValue().getSearchValue())) {
					builder.and(products.id.ne(Integer.valueOf(details.getValue().getSearchValue())));
				} else if(details.getValue().getOperator().equals("range")) {
				   if(StringUtils.isNumeric(details.getValue().getStartingValue()) && StringUtils.isNumeric(details.getValue().getEndingValue())) {
                	   builder.and(products.id.between(Integer.valueOf(details.getValue().getStartingValue()), Integer.valueOf(details.getValue().getEndingValue())));
                   } else if(StringUtils.isNumeric(details.getValue().getStartingValue())) {
                	   builder.and(products.id.goe(Integer.valueOf(details.getValue().getStartingValue())));
                   } else if(StringUtils.isNumeric(details.getValue().getEndingValue())) {
                	   builder.and(products.id.loe(Integer.valueOf(details.getValue().getEndingValue())));
				   }
				}
			}
            if(details.getKey().replace("%20","").trim().equals("name")) {
				if(details.getValue().getOperator().equals("contains")) {
					builder.and(products.name.likeIgnoreCase("%"+ details.getValue().getSearchValue() + "%"));
				} else if(details.getValue().getOperator().equals("equals")) {
					builder.and(products.name.eq(details.getValue().getSearchValue()));
				} else if(details.getValue().getOperator().equals("notEqual")) {
					builder.and(products.name.ne(details.getValue().getSearchValue()));
				}
			}
			if(details.getKey().replace("%20","").trim().equals("price")) {
				if(details.getValue().getOperator().equals("contains") && NumberUtils.isCreatable(details.getValue().getSearchValue())) {
					builder.and(products.price.like(details.getValue().getSearchValue() + "%"));
				} else if(details.getValue().getOperator().equals("equals") && NumberUtils.isCreatable(details.getValue().getSearchValue())) {
					builder.and(products.price.eq(new BigDecimal(details.getValue().getSearchValue())));
				} else if(details.getValue().getOperator().equals("notEqual") && NumberUtils.isCreatable(details.getValue().getSearchValue())) {
					builder.and(products.price.ne(new BigDecimal(details.getValue().getSearchValue())));
				} else if(details.getValue().getOperator().equals("range")) {
				   if(NumberUtils.isCreatable(details.getValue().getStartingValue()) && NumberUtils.isCreatable(details.getValue().getEndingValue())) {
                	   builder.and(products.price.between(new BigDecimal(details.getValue().getStartingValue()), new BigDecimal(details.getValue().getEndingValue())));
                   } else if(NumberUtils.isCreatable(details.getValue().getStartingValue())) {
                	   builder.and(products.price.goe(new BigDecimal(details.getValue().getStartingValue())));
                   } else if(NumberUtils.isCreatable(details.getValue().getEndingValue())) {
                	   builder.and(products.price.loe(new BigDecimal(details.getValue().getEndingValue())));
				   }
				}
			}		
			if(details.getKey().replace("%20","").trim().equals("stock")) {
				if(details.getValue().getOperator().equals("contains") && StringUtils.isNumeric(details.getValue().getSearchValue())) {
					builder.and(products.stock.like(details.getValue().getSearchValue() + "%"));
				} else if(details.getValue().getOperator().equals("equals") && StringUtils.isNumeric(details.getValue().getSearchValue())) {
					builder.and(products.stock.eq(Integer.valueOf(details.getValue().getSearchValue())));
				} else if(details.getValue().getOperator().equals("notEqual") && StringUtils.isNumeric(details.getValue().getSearchValue())) {
					builder.and(products.stock.ne(Integer.valueOf(details.getValue().getSearchValue())));
				} else if(details.getValue().getOperator().equals("range")) {
				   if(StringUtils.isNumeric(details.getValue().getStartingValue()) && StringUtils.isNumeric(details.getValue().getEndingValue())) {
                	   builder.and(products.stock.between(Integer.valueOf(details.getValue().getStartingValue()), Integer.valueOf(details.getValue().getEndingValue())));
                   } else if(StringUtils.isNumeric(details.getValue().getStartingValue())) {
                	   builder.and(products.stock.goe(Integer.valueOf(details.getValue().getStartingValue())));
                   } else if(StringUtils.isNumeric(details.getValue().getEndingValue())) {
                	   builder.and(products.stock.loe(Integer.valueOf(details.getValue().getEndingValue())));
				   }
				}
			}
			if(details.getKey().replace("%20","").trim().equals("updatedAt")) {
				if(details.getValue().getOperator().equals("equals") && SearchUtils.stringToLocalDateTime(details.getValue().getSearchValue()) !=null) {
					builder.and(products.updatedAt.eq(SearchUtils.stringToLocalDateTime(details.getValue().getSearchValue())));
				} else if(details.getValue().getOperator().equals("notEqual") && SearchUtils.stringToLocalDateTime(details.getValue().getSearchValue()) !=null) {
					builder.and(products.updatedAt.ne(SearchUtils.stringToLocalDateTime(details.getValue().getSearchValue())));
				} else if(details.getValue().getOperator().equals("range")) {
				   LocalDateTime startLocalDateTime= SearchUtils.stringToLocalDateTime(details.getValue().getStartingValue());
				   LocalDateTime endLocalDateTime= SearchUtils.stringToLocalDateTime(details.getValue().getEndingValue());
				   if(startLocalDateTime!=null && endLocalDateTime!=null) {
					   builder.and(products.updatedAt.between(startLocalDateTime,endLocalDateTime));
				   } else if(endLocalDateTime!=null) {
					   builder.and(products.updatedAt.loe(endLocalDateTime));
                   } else if(startLocalDateTime!=null) {
                	   builder.and(products.updatedAt.goe(startLocalDateTime));  
                   }
                }     
			}
	    
		    if(details.getKey().replace("%20","").trim().equals("categories")) {
				if(details.getValue().getOperator().equals("contains")) {
					builder.and(products.categories.name.likeIgnoreCase("%"+ details.getValue().getSearchValue() + "%"));
				} else if(details.getValue().getOperator().equals("equals")) {
					builder.and(products.categories.name.eq(details.getValue().getSearchValue()));
				} else if(details.getValue().getOperator().equals("notEqual")) {
					builder.and(products.categories.name.ne(details.getValue().getSearchValue()));
				}
			}
		}
		
		for (Map.Entry<String, String> joinCol : joinColumns.entrySet()) {
		if(joinCol != null && joinCol.getKey().equals("categoryId")) {
		    builder.and(products.categories.id.eq(Integer.parseInt(joinCol.getValue())));
		}
        
		if(joinCol != null && joinCol.getKey().equals("categories")) {
		    builder.and(products.categories.name.eq(joinCol.getValue()));
        }
        }
		return builder;
	}
	
    
}



