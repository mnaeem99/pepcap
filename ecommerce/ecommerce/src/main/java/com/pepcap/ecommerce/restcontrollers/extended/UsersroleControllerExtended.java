package com.pepcap.ecommerce.restcontrollers.extended;

import org.springframework.web.bind.annotation.*;
import com.pepcap.ecommerce.restcontrollers.core.UsersroleController;
import com.pepcap.ecommerce.application.extended.authorization.usersrole.IUsersroleAppServiceExtended;

import com.pepcap.ecommerce.application.extended.authorization.role.IRoleAppServiceExtended;
import com.pepcap.ecommerce.application.extended.authorization.users.IUsersAppServiceExtended;
import com.pepcap.ecommerce.security.JWTAppService;
import org.springframework.core.env.Environment;
import com.pepcap.ecommerce.commons.logging.LoggingHelper;

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

