package com.pepcap.taskmanagement.application.extended.timesheets;

import org.springframework.stereotype.Service;
import com.pepcap.taskmanagement.application.core.timesheets.TimesheetsAppService;

import com.pepcap.taskmanagement.domain.extended.timesheets.ITimesheetsRepositoryExtended;
import com.pepcap.taskmanagement.domain.extended.tasks.ITasksRepositoryExtended;
import com.pepcap.taskmanagement.domain.extended.authorization.users.IUsersRepositoryExtended;
import com.pepcap.taskmanagement.commons.logging.LoggingHelper;

@Service("timesheetsAppServiceExtended")
public class TimesheetsAppServiceExtended extends TimesheetsAppService implements ITimesheetsAppServiceExtended {

	public TimesheetsAppServiceExtended(ITimesheetsRepositoryExtended timesheetsRepositoryExtended,
				ITasksRepositoryExtended tasksRepositoryExtended,IUsersRepositoryExtended usersRepositoryExtended,ITimesheetsMapperExtended mapper,LoggingHelper logHelper) {

		super(timesheetsRepositoryExtended,
		tasksRepositoryExtended,usersRepositoryExtended,mapper,logHelper);

	}

 	//Add your custom code here
 
}

