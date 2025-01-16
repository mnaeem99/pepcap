package com.pepcap.adminpanel.restcontrollers.extended;

import org.springframework.web.bind.annotation.*;
import com.pepcap.adminpanel.restcontrollers.core.UsersController;
import com.pepcap.adminpanel.application.extended.authorization.users.IUsersAppServiceExtended;

import com.pepcap.adminpanel.application.extended.authorization.userspermission.IUserspermissionAppServiceExtended;
import com.pepcap.adminpanel.application.extended.authorization.usersrole.IUsersroleAppServiceExtended;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.pepcap.adminpanel.security.JWTAppService;
import org.springframework.core.env.Environment;
import com.pepcap.adminpanel.commons.logging.LoggingHelper;

@RestController
@RequestMapping("/users/extended")
public class UsersControllerExtended extends UsersController {

		public UsersControllerExtended(IUsersAppServiceExtended usersAppServiceExtended, IUserspermissionAppServiceExtended userspermissionAppServiceExtended, IUsersroleAppServiceExtended usersroleAppServiceExtended,
	    PasswordEncoder pEncoder,JWTAppService jwtAppService, LoggingHelper helper, Environment env) {
		super(
		usersAppServiceExtended,
		
    	userspermissionAppServiceExtended,
		
    	usersroleAppServiceExtended,
	    pEncoder,
	    jwtAppService,
		helper, env);
	}

	//Add your custom code here

}

