package com.pepcap.adminpanel.application.extended.authorization.usersrole;

import org.springframework.stereotype.Service;
import com.pepcap.adminpanel.application.core.authorization.usersrole.UsersroleAppService;

import com.pepcap.adminpanel.domain.extended.authorization.usersrole.IUsersroleRepositoryExtended;
import com.pepcap.adminpanel.domain.extended.authorization.role.IRoleRepositoryExtended;
import com.pepcap.adminpanel.domain.extended.authorization.users.IUsersRepositoryExtended;
import com.pepcap.adminpanel.commons.logging.LoggingHelper;

@Service("usersroleAppServiceExtended")
public class UsersroleAppServiceExtended extends UsersroleAppService implements IUsersroleAppServiceExtended {

	public UsersroleAppServiceExtended(IUsersroleRepositoryExtended usersroleRepositoryExtended,
				IRoleRepositoryExtended roleRepositoryExtended,IUsersRepositoryExtended usersRepositoryExtended,IUsersroleMapperExtended mapper,LoggingHelper logHelper) {

		super(usersroleRepositoryExtended,
		roleRepositoryExtended,usersRepositoryExtended,mapper,logHelper);

	}

 	//Add your custom code here
 
}

