package com.pepcap.inventorymanagement.application.extended.inventorytransactions;

import org.springframework.stereotype.Service;
import com.pepcap.inventorymanagement.application.core.inventorytransactions.InventoryTransactionsAppService;

import com.pepcap.inventorymanagement.domain.extended.inventorytransactions.IInventoryTransactionsRepositoryExtended;
import com.pepcap.inventorymanagement.domain.extended.products.IProductsRepositoryExtended;
import com.pepcap.inventorymanagement.commons.logging.LoggingHelper;

@Service("inventoryTransactionsAppServiceExtended")
public class InventoryTransactionsAppServiceExtended extends InventoryTransactionsAppService implements IInventoryTransactionsAppServiceExtended {

	public InventoryTransactionsAppServiceExtended(IInventoryTransactionsRepositoryExtended inventoryTransactionsRepositoryExtended,
				IProductsRepositoryExtended productsRepositoryExtended,IInventoryTransactionsMapperExtended mapper,LoggingHelper logHelper) {

		super(inventoryTransactionsRepositoryExtended,
		productsRepositoryExtended,mapper,logHelper);

	}

 	//Add your custom code here
 
}

