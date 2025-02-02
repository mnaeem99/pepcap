package com.pepcap.taskmanagement.application.extended.tasks;

import org.springframework.stereotype.Service;
import com.pepcap.taskmanagement.application.core.tasks.TasksAppService;

import com.pepcap.taskmanagement.domain.extended.tasks.ITasksRepositoryExtended;
import com.pepcap.taskmanagement.domain.extended.projects.IProjectsRepositoryExtended;
import com.pepcap.taskmanagement.domain.extended.authorization.users.IUsersRepositoryExtended;
import com.pepcap.taskmanagement.commons.logging.LoggingHelper;

@Service("tasksAppServiceExtended")
public class TasksAppServiceExtended extends TasksAppService implements ITasksAppServiceExtended {

	public TasksAppServiceExtended(ITasksRepositoryExtended tasksRepositoryExtended,
				IProjectsRepositoryExtended projectsRepositoryExtended,IUsersRepositoryExtended usersRepositoryExtended,ITasksMapperExtended mapper,LoggingHelper logHelper) {

		super(tasksRepositoryExtended,
		projectsRepositoryExtended,usersRepositoryExtended,mapper,logHelper);

	}

 	//Add your custom code here
 
}

