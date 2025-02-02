package com.pepcap.taskmanagement.restcontrollers.extended;

import org.springframework.web.bind.annotation.*;
import com.pepcap.taskmanagement.restcontrollers.core.UserspermissionController;
import com.pepcap.taskmanagement.application.extended.authorization.userspermission.IUserspermissionAppServiceExtended;

import com.pepcap.taskmanagement.application.extended.authorization.permission.IPermissionAppServiceExtended;
import com.pepcap.taskmanagement.application.extended.authorization.users.IUsersAppServiceExtended;
import com.pepcap.taskmanagement.security.JWTAppService;
import org.springframework.core.env.Environment;
import com.pepcap.taskmanagement.commons.logging.LoggingHelper;

@RestController
@RequestMapping("/userspermission/extended")
public class UserspermissionControllerExtended extends UserspermissionController {

		public UserspermissionControllerExtended(IUserspermissionAppServiceExtended userspermissionAppServiceExtended, IPermissionAppServiceExtended permissionAppServiceExtended, IUsersAppServiceExtended usersAppServiceExtended,
	    JWTAppService jwtAppService, LoggingHelper helper, Environment env) {
		super(
		userspermissionAppServiceExtended,
		
    	permissionAppServiceExtended,
		
    	usersAppServiceExtended,
	    jwtAppService,
		helper, env);
	}

	//Add your custom code here

}

