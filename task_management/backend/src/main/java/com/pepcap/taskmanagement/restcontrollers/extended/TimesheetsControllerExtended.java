package com.pepcap.taskmanagement.restcontrollers.extended;

import org.springframework.web.bind.annotation.*;
import com.pepcap.taskmanagement.restcontrollers.core.TimesheetsController;
import com.pepcap.taskmanagement.application.extended.timesheets.ITimesheetsAppServiceExtended;

import com.pepcap.taskmanagement.application.extended.tasks.ITasksAppServiceExtended;
import com.pepcap.taskmanagement.application.extended.authorization.users.IUsersAppServiceExtended;
import org.springframework.core.env.Environment;
import com.pepcap.taskmanagement.commons.logging.LoggingHelper;

@RestController
@RequestMapping("/timesheets/extended")
public class TimesheetsControllerExtended extends TimesheetsController {

		public TimesheetsControllerExtended(ITimesheetsAppServiceExtended timesheetsAppServiceExtended, ITasksAppServiceExtended tasksAppServiceExtended, IUsersAppServiceExtended usersAppServiceExtended,
	     LoggingHelper helper, Environment env) {
		super(
		timesheetsAppServiceExtended,
		
    	tasksAppServiceExtended,
		
    	usersAppServiceExtended,
		helper, env);
	}

	//Add your custom code here

}

