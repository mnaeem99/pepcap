package com.pepcap.taskmanagement.restcontrollers.extended;

import org.springframework.web.bind.annotation.*;
import com.pepcap.taskmanagement.restcontrollers.core.RolepermissionController;
import com.pepcap.taskmanagement.application.extended.authorization.rolepermission.IRolepermissionAppServiceExtended;

import com.pepcap.taskmanagement.application.extended.authorization.permission.IPermissionAppServiceExtended;
import com.pepcap.taskmanagement.application.extended.authorization.role.IRoleAppServiceExtended;
import org.springframework.core.env.Environment;
import com.pepcap.taskmanagement.commons.logging.LoggingHelper;

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

