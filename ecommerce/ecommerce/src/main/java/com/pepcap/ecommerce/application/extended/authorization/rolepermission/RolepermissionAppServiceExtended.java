package com.pepcap.ecommerce.application.extended.authorization.rolepermission;

import org.springframework.stereotype.Service;
import com.pepcap.ecommerce.application.core.authorization.rolepermission.RolepermissionAppService;

import com.pepcap.ecommerce.domain.extended.authorization.rolepermission.IRolepermissionRepositoryExtended;
import com.pepcap.ecommerce.domain.extended.authorization.permission.IPermissionRepositoryExtended;
import com.pepcap.ecommerce.domain.extended.authorization.role.IRoleRepositoryExtended;
import com.pepcap.ecommerce.domain.extended.authorization.usersrole.IUsersroleRepositoryExtended;
import com.pepcap.ecommerce.security.JWTAppService;
import com.pepcap.ecommerce.commons.logging.LoggingHelper;

@Service("rolepermissionAppServiceExtended")
public class RolepermissionAppServiceExtended extends RolepermissionAppService implements IRolepermissionAppServiceExtended {

	public RolepermissionAppServiceExtended(JWTAppService jwtAppService,IUsersroleRepositoryExtended usersroleRepositoryExtended,IRolepermissionRepositoryExtended rolepermissionRepositoryExtended,
				IPermissionRepositoryExtended permissionRepositoryExtended,IRoleRepositoryExtended roleRepositoryExtended,IRolepermissionMapperExtended mapper,LoggingHelper logHelper) {

		super(jwtAppService, usersroleRepositoryExtended,rolepermissionRepositoryExtended,
		permissionRepositoryExtended,roleRepositoryExtended,mapper,logHelper);

	}

 	//Add your custom code here
 
}

