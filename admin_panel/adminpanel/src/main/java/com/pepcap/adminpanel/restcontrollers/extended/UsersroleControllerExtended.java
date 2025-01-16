package com.pepcap.adminpanel.restcontrollers.extended;

import org.springframework.web.bind.annotation.*;
import com.pepcap.adminpanel.restcontrollers.core.UsersroleController;
import com.pepcap.adminpanel.application.extended.authorization.usersrole.IUsersroleAppServiceExtended;

import com.pepcap.adminpanel.application.extended.authorization.role.IRoleAppServiceExtended;
import com.pepcap.adminpanel.application.extended.authorization.users.IUsersAppServiceExtended;
import com.pepcap.adminpanel.security.JWTAppService;
import org.springframework.core.env.Environment;
import com.pepcap.adminpanel.commons.logging.LoggingHelper;

@RestController
@RequestMapping("/usersrole/extended")
public class UsersroleControllerExtended extends UsersroleController {

		public UsersroleControllerExtended(IUsersroleAppServiceExtended usersroleAppServiceExtended, IRoleAppServiceExtended roleAppServiceExtended, IUsersAppServiceExtended usersAppServiceExtended,
	    JWTAppService jwtAppService, LoggingHelper helper, Environment env) {
		super(
		usersroleAppServiceExtended,
		
    	roleAppServiceExtended,
		
    	usersAppServiceExtended,
	    jwtAppService,
		helper, env);
	}

	//Add your custom code here

}

