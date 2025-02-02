package com.pepcap.taskmanagement.application.extended.authorization.rolepermission;

import org.springframework.stereotype.Service;
import com.pepcap.taskmanagement.application.core.authorization.rolepermission.RolepermissionAppService;

import com.pepcap.taskmanagement.domain.extended.authorization.rolepermission.IRolepermissionRepositoryExtended;
import com.pepcap.taskmanagement.domain.extended.authorization.permission.IPermissionRepositoryExtended;
import com.pepcap.taskmanagement.domain.extended.authorization.role.IRoleRepositoryExtended;
import com.pepcap.taskmanagement.domain.extended.authorization.usersrole.IUsersroleRepositoryExtended;
import com.pepcap.taskmanagement.security.JWTAppService;
import com.pepcap.taskmanagement.commons.logging.LoggingHelper;

@Service("rolepermissionAppServiceExtended")
public class RolepermissionAppServiceExtended extends RolepermissionAppService implements IRolepermissionAppServiceExtended {

	public RolepermissionAppServiceExtended(JWTAppService jwtAppService,IUsersroleRepositoryExtended usersroleRepositoryExtended,IRolepermissionRepositoryExtended rolepermissionRepositoryExtended,
				IPermissionRepositoryExtended permissionRepositoryExtended,IRoleRepositoryExtended roleRepositoryExtended,IRolepermissionMapperExtended mapper,LoggingHelper logHelper) {

		super(jwtAppService, usersroleRepositoryExtended,rolepermissionRepositoryExtended,
		permissionRepositoryExtended,roleRepositoryExtended,mapper,logHelper);

	}

 	//Add your custom code here
 
}

