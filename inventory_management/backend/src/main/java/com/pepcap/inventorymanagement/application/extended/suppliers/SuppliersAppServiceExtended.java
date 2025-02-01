package com.pepcap.inventorymanagement.application.extended.suppliers;

import org.springframework.stereotype.Service;
import com.pepcap.inventorymanagement.application.core.suppliers.SuppliersAppService;

import com.pepcap.inventorymanagement.domain.extended.suppliers.ISuppliersRepositoryExtended;
import com.pepcap.inventorymanagement.commons.logging.LoggingHelper;

@Service("suppliersAppServiceExtended")
public class SuppliersAppServiceExtended extends SuppliersAppService implements ISuppliersAppServiceExtended {

	public SuppliersAppServiceExtended(ISuppliersRepositoryExtended suppliersRepositoryExtended,
				ISuppliersMapperExtended mapper,LoggingHelper logHelper) {

		super(suppliersRepositoryExtended,
		mapper,logHelper);

	}

 	//Add your custom code here
 
}

