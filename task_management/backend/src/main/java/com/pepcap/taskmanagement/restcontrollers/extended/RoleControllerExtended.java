package com.pepcap.taskmanagement.restcontrollers.extended;

import org.springframework.web.bind.annotation.*;
import com.pepcap.taskmanagement.restcontrollers.core.RoleController;
import com.pepcap.taskmanagement.application.extended.authorization.role.IRoleAppServiceExtended;

import com.pepcap.taskmanagement.application.extended.authorization.rolepermission.IRolepermissionAppServiceExtended;
import com.pepcap.taskmanagement.application.extended.authorization.usersrole.IUsersroleAppServiceExtended;
import org.springframework.core.env.Environment;
import com.pepcap.taskmanagement.commons.logging.LoggingHelper;

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

