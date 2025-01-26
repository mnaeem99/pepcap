package com.pepcap.ecommerce.application.extended.authorization.userspermission;

import org.springframework.stereotype.Service;
import com.pepcap.ecommerce.application.core.authorization.userspermission.UserspermissionAppService;

import com.pepcap.ecommerce.domain.extended.authorization.userspermission.IUserspermissionRepositoryExtended;
import com.pepcap.ecommerce.domain.extended.authorization.permission.IPermissionRepositoryExtended;
import com.pepcap.ecommerce.domain.extended.authorization.users.IUsersRepositoryExtended;
import com.pepcap.ecommerce.commons.logging.LoggingHelper;

@Service("userspermissionAppServiceExtended")
public class UserspermissionAppServiceExtended extends UserspermissionAppService implements IUserspermissionAppServiceExtended {

	public UserspermissionAppServiceExtended(IUserspermissionRepositoryExtended userspermissionRepositoryExtended,
				IPermissionRepositoryExtended permissionRepositoryExtended,IUsersRepositoryExtended usersRepositoryExtended,IUserspermissionMapperExtended mapper,LoggingHelper logHelper) {

		super(userspermissionRepositoryExtended,
		permissionRepositoryExtended,usersRepositoryExtended,mapper,logHelper);

	}

 	//Add your custom code here
 
}

