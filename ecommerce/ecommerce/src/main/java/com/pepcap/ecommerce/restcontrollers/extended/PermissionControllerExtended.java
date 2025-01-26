package com.pepcap.ecommerce.restcontrollers.extended;

import org.springframework.web.bind.annotation.*;
import com.pepcap.ecommerce.restcontrollers.core.PermissionController;
import com.pepcap.ecommerce.application.extended.authorization.permission.IPermissionAppServiceExtended;

import com.pepcap.ecommerce.application.extended.authorization.rolepermission.IRolepermissionAppServiceExtended;
import com.pepcap.ecommerce.application.extended.authorization.userspermission.IUserspermissionAppServiceExtended;
import org.springframework.core.env.Environment;
import com.pepcap.ecommerce.commons.logging.LoggingHelper;

@RestController
@RequestMapping("/permission/extended")
public class PermissionControllerExtended extends PermissionController {

		public PermissionControllerExtended(IPermissionAppServiceExtended permissionAppServiceExtended, IRolepermissionAppServiceExtended rolepermissionAppServiceExtended, IUserspermissionAppServiceExtended userspermissionAppServiceExtended,
	     LoggingHelper helper, Environment env) {
		super(
		permissionAppServiceExtended,
		
    	rolepermissionAppServiceExtended,
		
    	userspermissionAppServiceExtended,
		helper, env);
	}

	//Add your custom code here

}

