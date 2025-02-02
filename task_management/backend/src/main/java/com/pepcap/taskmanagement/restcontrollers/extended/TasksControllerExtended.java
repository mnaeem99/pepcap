package com.pepcap.taskmanagement.restcontrollers.extended;

import org.springframework.web.bind.annotation.*;
import com.pepcap.taskmanagement.restcontrollers.core.TasksController;
import com.pepcap.taskmanagement.application.extended.tasks.ITasksAppServiceExtended;

import com.pepcap.taskmanagement.application.extended.projects.IProjectsAppServiceExtended;
import com.pepcap.taskmanagement.application.extended.timesheets.ITimesheetsAppServiceExtended;
import com.pepcap.taskmanagement.application.extended.authorization.users.IUsersAppServiceExtended;
import org.springframework.core.env.Environment;
import com.pepcap.taskmanagement.commons.logging.LoggingHelper;

@RestController
@RequestMapping("/tasks/extended")
public class TasksControllerExtended extends TasksController {

		public TasksControllerExtended(ITasksAppServiceExtended tasksAppServiceExtended, IProjectsAppServiceExtended projectsAppServiceExtended, ITimesheetsAppServiceExtended timesheetsAppServiceExtended, IUsersAppServiceExtended usersAppServiceExtended,
	     LoggingHelper helper, Environment env) {
		super(
		tasksAppServiceExtended,
		
    	projectsAppServiceExtended,
		
    	timesheetsAppServiceExtended,
		
    	usersAppServiceExtended,
		helper, env);
	}

	//Add your custom code here

}

