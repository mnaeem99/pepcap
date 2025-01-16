package com.pepcap.adminpanel.application.extended.categories;

import org.springframework.stereotype.Service;
import com.pepcap.adminpanel.application.core.categories.CategoriesAppService;

import com.pepcap.adminpanel.domain.extended.categories.ICategoriesRepositoryExtended;
import com.pepcap.adminpanel.commons.logging.LoggingHelper;

@Service("categoriesAppServiceExtended")
public class CategoriesAppServiceExtended extends CategoriesAppService implements ICategoriesAppServiceExtended {

	public CategoriesAppServiceExtended(ICategoriesRepositoryExtended categoriesRepositoryExtended,
				ICategoriesMapperExtended mapper,LoggingHelper logHelper) {

		super(categoriesRepositoryExtended,
		mapper,logHelper);

	}

 	//Add your custom code here
 
}

