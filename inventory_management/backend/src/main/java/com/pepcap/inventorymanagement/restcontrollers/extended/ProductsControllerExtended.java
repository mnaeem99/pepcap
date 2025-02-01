package com.pepcap.inventorymanagement.restcontrollers.extended;

import org.springframework.web.bind.annotation.*;
import com.pepcap.inventorymanagement.restcontrollers.core.ProductsController;
import com.pepcap.inventorymanagement.application.extended.products.IProductsAppServiceExtended;

import com.pepcap.inventorymanagement.application.extended.categories.ICategoriesAppServiceExtended;
import com.pepcap.inventorymanagement.application.extended.inventorytransactions.IInventoryTransactionsAppServiceExtended;
import org.springframework.core.env.Environment;
import com.pepcap.inventorymanagement.commons.logging.LoggingHelper;

@RestController
@RequestMapping("/products/extended")
public class ProductsControllerExtended extends ProductsController {

		public ProductsControllerExtended(IProductsAppServiceExtended productsAppServiceExtended, ICategoriesAppServiceExtended categoriesAppServiceExtended, IInventoryTransactionsAppServiceExtended inventoryTransactionsAppServiceExtended,
	     LoggingHelper helper, Environment env) {
		super(
		productsAppServiceExtended,
		
    	categoriesAppServiceExtended,
		
    	inventoryTransactionsAppServiceExtended,
		helper, env);
	}

	//Add your custom code here

}

