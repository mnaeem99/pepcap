package com.pepcap.adminpanel.application.extended.authorization.role;

import org.springframework.stereotype.Service;
import com.pepcap.adminpanel.application.core.authorization.role.RoleAppService;

import com.pepcap.adminpanel.domain.extended.authorization.role.IRoleRepositoryExtended;
import com.pepcap.adminpanel.commons.logging.LoggingHelper;

@Service("roleAppServiceExtended")
public class RoleAppServiceExtended extends RoleAppService implements IRoleAppServiceExtended {

	public RoleAppServiceExtended(IRoleRepositoryExtended roleRepositoryExtended,
				IRoleMapperExtended mapper,LoggingHelper logHelper) {

		super(roleRepositoryExtended,
		mapper,logHelper);

	}

 	//Add your custom code here
 
}

