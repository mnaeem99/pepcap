package com.pepcap.inventorymanagement.application.extended.products;

import org.springframework.stereotype.Service;
import com.pepcap.inventorymanagement.application.core.products.ProductsAppService;

import com.pepcap.inventorymanagement.domain.extended.products.IProductsRepositoryExtended;
import com.pepcap.inventorymanagement.domain.extended.categories.ICategoriesRepositoryExtended;
import com.pepcap.inventorymanagement.commons.logging.LoggingHelper;

@Service("productsAppServiceExtended")
public class ProductsAppServiceExtended extends ProductsAppService implements IProductsAppServiceExtended {

	public ProductsAppServiceExtended(IProductsRepositoryExtended productsRepositoryExtended,
				ICategoriesRepositoryExtended categoriesRepositoryExtended,IProductsMapperExtended mapper,LoggingHelper logHelper) {

		super(productsRepositoryExtended,
		categoriesRepositoryExtended,mapper,logHelper);

	}

 	//Add your custom code here
 
}

