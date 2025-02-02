package com.pepcap.taskmanagement.restcontrollers.extended;

import org.springframework.web.bind.annotation.*;
import com.pepcap.taskmanagement.restcontrollers.core.UsersroleController;
import com.pepcap.taskmanagement.application.extended.authorization.usersrole.IUsersroleAppServiceExtended;

import com.pepcap.taskmanagement.application.extended.authorization.role.IRoleAppServiceExtended;
import com.pepcap.taskmanagement.application.extended.authorization.users.IUsersAppServiceExtended;
import com.pepcap.taskmanagement.security.JWTAppService;
import org.springframework.core.env.Environment;
import com.pepcap.taskmanagement.commons.logging.LoggingHelper;

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

