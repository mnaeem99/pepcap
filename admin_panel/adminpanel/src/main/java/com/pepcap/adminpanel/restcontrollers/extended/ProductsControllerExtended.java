package com.pepcap.adminpanel.restcontrollers.extended;

import org.springframework.web.bind.annotation.*;
import com.pepcap.adminpanel.restcontrollers.core.ProductsController;
import com.pepcap.adminpanel.application.extended.products.IProductsAppServiceExtended;

import com.pepcap.adminpanel.application.extended.categories.ICategoriesAppServiceExtended;
import org.springframework.core.env.Environment;
import com.pepcap.adminpanel.commons.logging.LoggingHelper;

@RestController
@RequestMapping("/products/extended")
public class ProductsControllerExtended extends ProductsController {

		public ProductsControllerExtended(IProductsAppServiceExtended productsAppServiceExtended, ICategoriesAppServiceExtended categoriesAppServiceExtended,
	     LoggingHelper helper, Environment env) {
		super(
		productsAppServiceExtended,
		
    	categoriesAppServiceExtended,
		helper, env);
	}

	//Add your custom code here

}

