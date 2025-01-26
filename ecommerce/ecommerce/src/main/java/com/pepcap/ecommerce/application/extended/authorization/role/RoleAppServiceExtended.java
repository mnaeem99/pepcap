package com.pepcap.ecommerce.application.extended.authorization.role;

import org.springframework.stereotype.Service;
import com.pepcap.ecommerce.application.core.authorization.role.RoleAppService;

import com.pepcap.ecommerce.domain.extended.authorization.role.IRoleRepositoryExtended;
import com.pepcap.ecommerce.commons.logging.LoggingHelper;

@Service("roleAppServiceExtended")
public class RoleAppServiceExtended extends RoleAppService implements IRoleAppServiceExtended {

	public RoleAppServiceExtended(IRoleRepositoryExtended roleRepositoryExtended,
				IRoleMapperExtended mapper,LoggingHelper logHelper) {

		super(roleRepositoryExtended,
		mapper,logHelper);

	}

 	//Add your custom code here
 
}

