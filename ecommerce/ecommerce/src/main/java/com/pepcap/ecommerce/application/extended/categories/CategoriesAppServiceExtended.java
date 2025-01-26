package com.pepcap.ecommerce.application.extended.categories;

import org.springframework.stereotype.Service;
import com.pepcap.ecommerce.application.core.categories.CategoriesAppService;

import com.pepcap.ecommerce.domain.extended.categories.ICategoriesRepositoryExtended;
import com.pepcap.ecommerce.commons.logging.LoggingHelper;

@Service("categoriesAppServiceExtended")
public class CategoriesAppServiceExtended extends CategoriesAppService implements ICategoriesAppServiceExtended {

	public CategoriesAppServiceExtended(ICategoriesRepositoryExtended categoriesRepositoryExtended,
				ICategoriesMapperExtended mapper,LoggingHelper logHelper) {

		super(categoriesRepositoryExtended,
		mapper,logHelper);

	}

 	//Add your custom code here
 
}

