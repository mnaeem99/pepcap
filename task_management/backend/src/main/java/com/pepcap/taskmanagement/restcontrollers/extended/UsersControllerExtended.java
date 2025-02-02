package com.pepcap.taskmanagement.restcontrollers.extended;

import org.springframework.web.bind.annotation.*;
import com.pepcap.taskmanagement.restcontrollers.core.UsersController;
import com.pepcap.taskmanagement.application.extended.authorization.users.IUsersAppServiceExtended;

import com.pepcap.taskmanagement.application.extended.tasks.ITasksAppServiceExtended;
import com.pepcap.taskmanagement.application.extended.timesheets.ITimesheetsAppServiceExtended;
import com.pepcap.taskmanagement.application.extended.authorization.userspermission.IUserspermissionAppServiceExtended;
import com.pepcap.taskmanagement.application.extended.authorization.usersrole.IUsersroleAppServiceExtended;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.pepcap.taskmanagement.security.JWTAppService;
import org.springframework.core.env.Environment;
import com.pepcap.taskmanagement.commons.logging.LoggingHelper;

@RestController
@RequestMapping("/users/extended")
public class UsersControllerExtended extends UsersController {

		public UsersControllerExtended(IUsersAppServiceExtended usersAppServiceExtended, ITasksAppServiceExtended tasksAppServiceExtended, ITimesheetsAppServiceExtended timesheetsAppServiceExtended, IUserspermissionAppServiceExtended userspermissionAppServiceExtended, IUsersroleAppServiceExtended usersroleAppServiceExtended,
	    PasswordEncoder pEncoder,JWTAppService jwtAppService, LoggingHelper helper, Environment env) {
		super(
		usersAppServiceExtended,
		
    	tasksAppServiceExtended,
		
    	timesheetsAppServiceExtended,
		
    	userspermissionAppServiceExtended,
		
    	usersroleAppServiceExtended,
	    pEncoder,
	    jwtAppService,
		helper, env);
	}

	//Add your custom code here

}

