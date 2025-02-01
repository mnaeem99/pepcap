package com.pepcap.inventorymanagement.application.extended.categories;

import org.springframework.stereotype.Service;
import com.pepcap.inventorymanagement.application.core.categories.CategoriesAppService;

import com.pepcap.inventorymanagement.domain.extended.categories.ICategoriesRepositoryExtended;
import com.pepcap.inventorymanagement.commons.logging.LoggingHelper;

@Service("categoriesAppServiceExtended")
public class CategoriesAppServiceExtended extends CategoriesAppService implements ICategoriesAppServiceExtended {

	public CategoriesAppServiceExtended(ICategoriesRepositoryExtended categoriesRepositoryExtended,
				ICategoriesMapperExtended mapper,LoggingHelper logHelper) {

		super(categoriesRepositoryExtended,
		mapper,logHelper);

	}

 	//Add your custom code here
 
}

