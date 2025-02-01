package com.pepcap.inventorymanagement.restcontrollers.extended;

import org.springframework.web.bind.annotation.*;
import com.pepcap.inventorymanagement.restcontrollers.core.InventoryTransactionsController;
import com.pepcap.inventorymanagement.application.extended.inventorytransactions.IInventoryTransactionsAppServiceExtended;

import com.pepcap.inventorymanagement.application.extended.products.IProductsAppServiceExtended;
import org.springframework.core.env.Environment;
import com.pepcap.inventorymanagement.commons.logging.LoggingHelper;

@RestController
@RequestMapping("/inventoryTransactions/extended")
public class InventoryTransactionsControllerExtended extends InventoryTransactionsController {

		public InventoryTransactionsControllerExtended(IInventoryTransactionsAppServiceExtended inventoryTransactionsAppServiceExtended, IProductsAppServiceExtended productsAppServiceExtended,
	     LoggingHelper helper, Environment env) {
		super(
		inventoryTransactionsAppServiceExtended,
		
    	productsAppServiceExtended,
		helper, env);
	}

	//Add your custom code here

}

