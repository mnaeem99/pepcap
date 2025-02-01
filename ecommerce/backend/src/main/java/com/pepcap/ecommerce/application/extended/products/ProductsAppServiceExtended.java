package com.pepcap.ecommerce.application.extended.products;

import org.springframework.stereotype.Service;
import com.pepcap.ecommerce.application.core.products.ProductsAppService;

import com.pepcap.ecommerce.domain.extended.products.IProductsRepositoryExtended;
import com.pepcap.ecommerce.domain.extended.categories.ICategoriesRepositoryExtended;
import com.pepcap.ecommerce.commons.logging.LoggingHelper;

@Service("productsAppServiceExtended")
public class ProductsAppServiceExtended extends ProductsAppService implements IProductsAppServiceExtended {

	public ProductsAppServiceExtended(IProductsRepositoryExtended productsRepositoryExtended,
				ICategoriesRepositoryExtended categoriesRepositoryExtended,IProductsMapperExtended mapper,LoggingHelper logHelper) {

		super(productsRepositoryExtended,
		categoriesRepositoryExtended,mapper,logHelper);

	}

 	//Add your custom code here
 
}

