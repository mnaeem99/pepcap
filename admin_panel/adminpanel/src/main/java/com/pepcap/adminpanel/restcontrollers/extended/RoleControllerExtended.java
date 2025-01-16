package com.pepcap.adminpanel.restcontrollers.extended;

import org.springframework.web.bind.annotation.*;
import com.pepcap.adminpanel.restcontrollers.core.RoleController;
import com.pepcap.adminpanel.application.extended.authorization.role.IRoleAppServiceExtended;

import com.pepcap.adminpanel.application.extended.authorization.rolepermission.IRolepermissionAppServiceExtended;
import com.pepcap.adminpanel.application.extended.authorization.usersrole.IUsersroleAppServiceExtended;
import org.springframework.core.env.Environment;
import com.pepcap.adminpanel.commons.logging.LoggingHelper;

@RestController
@RequestMapping("/role/extended")
public class RoleControllerExtended extends RoleController {

		public RoleControllerExtended(IRoleAppServiceExtended roleAppServiceExtended, IRolepermissionAppServiceExtended rolepermissionAppServiceExtended, IUsersroleAppServiceExtended usersroleAppServiceExtended,
	     LoggingHelper helper, Environment env) {
		super(
		roleAppServiceExtended,
		
    	rolepermissionAppServiceExtended,
		
    	usersroleAppServiceExtended,
		helper, env);
	}

	//Add your custom code here

}

