package com.pepcap.adminpanel.restcontrollers.extended;

import org.springframework.web.bind.annotation.*;
import com.pepcap.adminpanel.restcontrollers.core.PermissionController;
import com.pepcap.adminpanel.application.extended.authorization.permission.IPermissionAppServiceExtended;

import com.pepcap.adminpanel.application.extended.authorization.rolepermission.IRolepermissionAppServiceExtended;
import com.pepcap.adminpanel.application.extended.authorization.userspermission.IUserspermissionAppServiceExtended;
import org.springframework.core.env.Environment;
import com.pepcap.adminpanel.commons.logging.LoggingHelper;

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

