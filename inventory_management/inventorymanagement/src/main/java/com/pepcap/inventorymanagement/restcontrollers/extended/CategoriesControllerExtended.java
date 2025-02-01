package com.pepcap.inventorymanagement.restcontrollers.extended;

import org.springframework.web.bind.annotation.*;
import com.pepcap.inventorymanagement.restcontrollers.core.CategoriesController;
import com.pepcap.inventorymanagement.application.extended.categories.ICategoriesAppServiceExtended;

import com.pepcap.inventorymanagement.application.extended.products.IProductsAppServiceExtended;
import org.springframework.core.env.Environment;
import com.pepcap.inventorymanagement.commons.logging.LoggingHelper;

@RestController
@RequestMapping("/categories/extended")
public class CategoriesControllerExtended extends CategoriesController {

		public CategoriesControllerExtended(ICategoriesAppServiceExtended categoriesAppServiceExtended, IProductsAppServiceExtended productsAppServiceExtended,
	     LoggingHelper helper, Environment env) {
		super(
		categoriesAppServiceExtended,
		
    	productsAppServiceExtended,
		helper, env);
	}

	//Add your custom code here

}

