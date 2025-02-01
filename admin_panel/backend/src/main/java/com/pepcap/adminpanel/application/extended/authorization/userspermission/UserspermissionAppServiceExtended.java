package com.pepcap.adminpanel.application.extended.authorization.userspermission;

import org.springframework.stereotype.Service;
import com.pepcap.adminpanel.application.core.authorization.userspermission.UserspermissionAppService;

import com.pepcap.adminpanel.domain.extended.authorization.userspermission.IUserspermissionRepositoryExtended;
import com.pepcap.adminpanel.domain.extended.authorization.permission.IPermissionRepositoryExtended;
import com.pepcap.adminpanel.domain.extended.authorization.users.IUsersRepositoryExtended;
import com.pepcap.adminpanel.commons.logging.LoggingHelper;

@Service("userspermissionAppServiceExtended")
public class UserspermissionAppServiceExtended extends UserspermissionAppService implements IUserspermissionAppServiceExtended {

	public UserspermissionAppServiceExtended(IUserspermissionRepositoryExtended userspermissionRepositoryExtended,
				IPermissionRepositoryExtended permissionRepositoryExtended,IUsersRepositoryExtended usersRepositoryExtended,IUserspermissionMapperExtended mapper,LoggingHelper logHelper) {

		super(userspermissionRepositoryExtended,
		permissionRepositoryExtended,usersRepositoryExtended,mapper,logHelper);

	}

 	//Add your custom code here
 
}

