package com.pepcap.taskmanagement.application.extended.authorization.users;

import org.springframework.stereotype.Service;
import com.pepcap.taskmanagement.application.core.authorization.users.UsersAppService;

import com.pepcap.taskmanagement.domain.extended.authorization.users.IUsersRepositoryExtended;
import com.pepcap.taskmanagement.domain.core.authorization.userspreference.IUserspreferenceRepository;
import com.pepcap.taskmanagement.commons.logging.LoggingHelper;

@Service("usersAppServiceExtended")
public class UsersAppServiceExtended extends UsersAppService implements IUsersAppServiceExtended {

	public UsersAppServiceExtended(IUsersRepositoryExtended usersRepositoryExtended,
				IUserspreferenceRepository userspreferenceRepository,IUsersMapperExtended mapper,LoggingHelper logHelper) {

		super(usersRepositoryExtended,
		userspreferenceRepository,mapper,logHelper);

	}

 	//Add your custom code here
 
}

