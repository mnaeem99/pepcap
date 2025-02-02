package com.pepcap.taskmanagement.restcontrollers.extended;

import org.springframework.web.bind.annotation.*;
import com.pepcap.taskmanagement.restcontrollers.core.PermissionController;
import com.pepcap.taskmanagement.application.extended.authorization.permission.IPermissionAppServiceExtended;

import com.pepcap.taskmanagement.application.extended.authorization.rolepermission.IRolepermissionAppServiceExtended;
import com.pepcap.taskmanagement.application.extended.authorization.userspermission.IUserspermissionAppServiceExtended;
import org.springframework.core.env.Environment;
import com.pepcap.taskmanagement.commons.logging.LoggingHelper;

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

