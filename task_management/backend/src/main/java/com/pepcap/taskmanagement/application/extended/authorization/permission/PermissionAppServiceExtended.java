package com.pepcap.taskmanagement.application.extended.authorization.permission;

import org.springframework.stereotype.Service;
import com.pepcap.taskmanagement.application.core.authorization.permission.PermissionAppService;

import com.pepcap.taskmanagement.domain.extended.authorization.permission.IPermissionRepositoryExtended;
import com.pepcap.taskmanagement.commons.logging.LoggingHelper;

@Service("permissionAppServiceExtended")
public class PermissionAppServiceExtended extends PermissionAppService implements IPermissionAppServiceExtended {

	public PermissionAppServiceExtended(IPermissionRepositoryExtended permissionRepositoryExtended,
				IPermissionMapperExtended mapper,LoggingHelper logHelper) {

		super(permissionRepositoryExtended,
		mapper,logHelper);

	}

 	//Add your custom code here
 
}

