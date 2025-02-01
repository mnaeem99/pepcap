package com.pepcap.adminpanel.restcontrollers.extended;

import org.springframework.web.bind.annotation.*;
import com.pepcap.adminpanel.restcontrollers.core.CategoriesController;
import com.pepcap.adminpanel.application.extended.categories.ICategoriesAppServiceExtended;

import com.pepcap.adminpanel.application.extended.products.IProductsAppServiceExtended;
import org.springframework.core.env.Environment;
import com.pepcap.adminpanel.commons.logging.LoggingHelper;

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

