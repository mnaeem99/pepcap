package com.pepcap.adminpanel.application.extended.authorization.rolepermission;

import org.springframework.stereotype.Service;
import com.pepcap.adminpanel.application.core.authorization.rolepermission.RolepermissionAppService;

import com.pepcap.adminpanel.domain.extended.authorization.rolepermission.IRolepermissionRepositoryExtended;
import com.pepcap.adminpanel.domain.extended.authorization.permission.IPermissionRepositoryExtended;
import com.pepcap.adminpanel.domain.extended.authorization.role.IRoleRepositoryExtended;
import com.pepcap.adminpanel.domain.extended.authorization.usersrole.IUsersroleRepositoryExtended;
import com.pepcap.adminpanel.security.JWTAppService;
import com.pepcap.adminpanel.commons.logging.LoggingHelper;

@Service("rolepermissionAppServiceExtended")
public class RolepermissionAppServiceExtended extends RolepermissionAppService implements IRolepermissionAppServiceExtended {

	public RolepermissionAppServiceExtended(JWTAppService jwtAppService,IUsersroleRepositoryExtended usersroleRepositoryExtended,IRolepermissionRepositoryExtended rolepermissionRepositoryExtended,
				IPermissionRepositoryExtended permissionRepositoryExtended,IRoleRepositoryExtended roleRepositoryExtended,IRolepermissionMapperExtended mapper,LoggingHelper logHelper) {

		super(jwtAppService, usersroleRepositoryExtended,rolepermissionRepositoryExtended,
		permissionRepositoryExtended,roleRepositoryExtended,mapper,logHelper);

	}

 	//Add your custom code here
 
}

