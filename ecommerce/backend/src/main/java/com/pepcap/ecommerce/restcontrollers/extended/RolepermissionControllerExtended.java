package com.pepcap.ecommerce.restcontrollers.extended;

import org.springframework.web.bind.annotation.*;
import com.pepcap.ecommerce.restcontrollers.core.RolepermissionController;
import com.pepcap.ecommerce.application.extended.authorization.rolepermission.IRolepermissionAppServiceExtended;

import com.pepcap.ecommerce.application.extended.authorization.permission.IPermissionAppServiceExtended;
import com.pepcap.ecommerce.application.extended.authorization.role.IRoleAppServiceExtended;
import org.springframework.core.env.Environment;
import com.pepcap.ecommerce.commons.logging.LoggingHelper;

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

