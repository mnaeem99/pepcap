package com.pepcap.taskmanagement.application.extended.authorization.role;

import org.springframework.stereotype.Service;
import com.pepcap.taskmanagement.application.core.authorization.role.RoleAppService;

import com.pepcap.taskmanagement.domain.extended.authorization.role.IRoleRepositoryExtended;
import com.pepcap.taskmanagement.commons.logging.LoggingHelper;

@Service("roleAppServiceExtended")
public class RoleAppServiceExtended extends RoleAppService implements IRoleAppServiceExtended {

	public RoleAppServiceExtended(IRoleRepositoryExtended roleRepositoryExtended,
				IRoleMapperExtended mapper,LoggingHelper logHelper) {

		super(roleRepositoryExtended,
		mapper,logHelper);

	}

 	//Add your custom code here
 
}

