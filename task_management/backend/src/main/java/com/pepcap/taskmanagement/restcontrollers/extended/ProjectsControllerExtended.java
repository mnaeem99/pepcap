package com.pepcap.taskmanagement.restcontrollers.extended;

import org.springframework.web.bind.annotation.*;
import com.pepcap.taskmanagement.restcontrollers.core.ProjectsController;
import com.pepcap.taskmanagement.application.extended.projects.IProjectsAppServiceExtended;

import com.pepcap.taskmanagement.application.extended.tasks.ITasksAppServiceExtended;
import org.springframework.core.env.Environment;
import com.pepcap.taskmanagement.commons.logging.LoggingHelper;

@RestController
@RequestMapping("/projects/extended")
public class ProjectsControllerExtended extends ProjectsController {

		public ProjectsControllerExtended(IProjectsAppServiceExtended projectsAppServiceExtended, ITasksAppServiceExtended tasksAppServiceExtended,
	     LoggingHelper helper, Environment env) {
		super(
		projectsAppServiceExtended,
		
    	tasksAppServiceExtended,
		helper, env);
	}

	//Add your custom code here

}

