package com.pepcap.adminpanel.restcontrollers.extended;

import org.springframework.web.bind.annotation.*;
import com.pepcap.adminpanel.restcontrollers.core.UserspermissionController;
import com.pepcap.adminpanel.application.extended.authorization.userspermission.IUserspermissionAppServiceExtended;

import com.pepcap.adminpanel.application.extended.authorization.permission.IPermissionAppServiceExtended;
import com.pepcap.adminpanel.application.extended.authorization.users.IUsersAppServiceExtended;
import com.pepcap.adminpanel.security.JWTAppService;
import org.springframework.core.env.Environment;
import com.pepcap.adminpanel.commons.logging.LoggingHelper;

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

