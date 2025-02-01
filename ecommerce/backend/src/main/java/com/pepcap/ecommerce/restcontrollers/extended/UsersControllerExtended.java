package com.pepcap.ecommerce.restcontrollers.extended;

import org.springframework.web.bind.annotation.*;
import com.pepcap.ecommerce.restcontrollers.core.UsersController;
import com.pepcap.ecommerce.application.extended.authorization.users.IUsersAppServiceExtended;

import com.pepcap.ecommerce.application.extended.orders.IOrdersAppServiceExtended;
import com.pepcap.ecommerce.application.extended.authorization.userspermission.IUserspermissionAppServiceExtended;
import com.pepcap.ecommerce.application.extended.authorization.usersrole.IUsersroleAppServiceExtended;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.pepcap.ecommerce.security.JWTAppService;
import org.springframework.core.env.Environment;
import com.pepcap.ecommerce.commons.logging.LoggingHelper;

@RestController
@RequestMapping("/users/extended")
public class UsersControllerExtended extends UsersController {

		public UsersControllerExtended(IUsersAppServiceExtended usersAppServiceExtended, IOrdersAppServiceExtended ordersAppServiceExtended, IUserspermissionAppServiceExtended userspermissionAppServiceExtended, IUsersroleAppServiceExtended usersroleAppServiceExtended,
	    PasswordEncoder pEncoder,JWTAppService jwtAppService, LoggingHelper helper, Environment env) {
		super(
		usersAppServiceExtended,
		
    	ordersAppServiceExtended,
		
    	userspermissionAppServiceExtended,
		
    	usersroleAppServiceExtended,
	    pEncoder,
	    jwtAppService,
		helper, env);
	}

	//Add your custom code here

}

