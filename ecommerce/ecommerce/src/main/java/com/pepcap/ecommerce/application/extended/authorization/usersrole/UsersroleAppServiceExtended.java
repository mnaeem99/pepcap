package com.pepcap.ecommerce.application.extended.authorization.usersrole;

import org.springframework.stereotype.Service;
import com.pepcap.ecommerce.application.core.authorization.usersrole.UsersroleAppService;

import com.pepcap.ecommerce.domain.extended.authorization.usersrole.IUsersroleRepositoryExtended;
import com.pepcap.ecommerce.domain.extended.authorization.role.IRoleRepositoryExtended;
import com.pepcap.ecommerce.domain.extended.authorization.users.IUsersRepositoryExtended;
import com.pepcap.ecommerce.commons.logging.LoggingHelper;

@Service("usersroleAppServiceExtended")
public class UsersroleAppServiceExtended extends UsersroleAppService implements IUsersroleAppServiceExtended {

	public UsersroleAppServiceExtended(IUsersroleRepositoryExtended usersroleRepositoryExtended,
				IRoleRepositoryExtended roleRepositoryExtended,IUsersRepositoryExtended usersRepositoryExtended,IUsersroleMapperExtended mapper,LoggingHelper logHelper) {

		super(usersroleRepositoryExtended,
		roleRepositoryExtended,usersRepositoryExtended,mapper,logHelper);

	}

 	//Add your custom code here
 
}

