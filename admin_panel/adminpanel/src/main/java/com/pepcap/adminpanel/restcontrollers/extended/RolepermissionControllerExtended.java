package com.pepcap.adminpanel.restcontrollers.extended;

import org.springframework.web.bind.annotation.*;
import com.pepcap.adminpanel.restcontrollers.core.RolepermissionController;
import com.pepcap.adminpanel.application.extended.authorization.rolepermission.IRolepermissionAppServiceExtended;

import com.pepcap.adminpanel.application.extended.authorization.permission.IPermissionAppServiceExtended;
import com.pepcap.adminpanel.application.extended.authorization.role.IRoleAppServiceExtended;
import org.springframework.core.env.Environment;
import com.pepcap.adminpanel.commons.logging.LoggingHelper;

@RestController
@RequestMapping("/rolepermission/extended")
public class RolepermissionControllerExtended extends RolepermissionController {

		public RolepermissionControllerExtended(IRolepermissionAppServiceExtended rolepermissionAppServiceExtended, IPermissionAppServiceExtended permissionAppServiceExtended, IRoleAppServiceExtended roleAppServiceExtended,
	     LoggingHelper helper, Environment env) {
		super(
		rolepermissionAppServiceExtended,
		
    	permissionAppServiceExtended,
		
    	roleAppServiceExtended,
		helper, env);
	}

	//Add your custom code here

}

