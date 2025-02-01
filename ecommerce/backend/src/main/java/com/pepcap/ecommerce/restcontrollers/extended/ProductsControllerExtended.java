package com.pepcap.ecommerce.restcontrollers.extended;

import org.springframework.web.bind.annotation.*;
import com.pepcap.ecommerce.restcontrollers.core.ProductsController;
import com.pepcap.ecommerce.application.extended.products.IProductsAppServiceExtended;

import com.pepcap.ecommerce.application.extended.categories.ICategoriesAppServiceExtended;
import com.pepcap.ecommerce.application.extended.orderitems.IOrderItemsAppServiceExtended;
import org.springframework.core.env.Environment;
import com.pepcap.ecommerce.commons.logging.LoggingHelper;

@RestController
@RequestMapping("/products/extended")
public class ProductsControllerExtended extends ProductsController {

		public ProductsControllerExtended(IProductsAppServiceExtended productsAppServiceExtended, ICategoriesAppServiceExtended categoriesAppServiceExtended, IOrderItemsAppServiceExtended orderItemsAppServiceExtended,
	     LoggingHelper helper, Environment env) {
		super(
		productsAppServiceExtended,
		
    	categoriesAppServiceExtended,
		
    	orderItemsAppServiceExtended,
		helper, env);
	}

	//Add your custom code here

}

