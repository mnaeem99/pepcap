package com.pepcap.adminpanel.application.extended.products;

import org.springframework.stereotype.Service;
import com.pepcap.adminpanel.application.core.products.ProductsAppService;

import com.pepcap.adminpanel.domain.extended.products.IProductsRepositoryExtended;
import com.pepcap.adminpanel.domain.extended.categories.ICategoriesRepositoryExtended;
import com.pepcap.adminpanel.commons.logging.LoggingHelper;

@Service("productsAppServiceExtended")
public class ProductsAppServiceExtended extends ProductsAppService implements IProductsAppServiceExtended {

	public ProductsAppServiceExtended(IProductsRepositoryExtended productsRepositoryExtended,
				ICategoriesRepositoryExtended categoriesRepositoryExtended,IProductsMapperExtended mapper,LoggingHelper logHelper) {

		super(productsRepositoryExtended,
		categoriesRepositoryExtended,mapper,logHelper);

	}

 	//Add your custom code here
 
}

