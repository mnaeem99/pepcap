package com.pepcap.ecommerce.application.core.authorization.usersrole;

import com.pepcap.ecommerce.domain.core.authorization.usersrole.UsersroleId;
import org.springframework.data.domain.Pageable;
import com.pepcap.ecommerce.application.core.authorization.usersrole.dto.*;
import com.pepcap.ecommerce.commons.search.SearchCriteria;
import java.net.MalformedURLException;
import java.util.*;

public interface IUsersroleAppService {
	
	//CRUD Operations
	CreateUsersroleOutput create(CreateUsersroleInput usersrole);

    void delete(UsersroleId usersroleId);

    UpdateUsersroleOutput update(UsersroleId usersroleId, UpdateUsersroleInput input);

    FindUsersroleByIdOutput findById(UsersroleId usersroleId);


    List<FindUsersroleByIdOutput> find(SearchCriteria search, Pageable pageable) throws MalformedURLException;
	//Relationship Operations
	//Relationship Operations
    
    GetRoleOutput getRole(UsersroleId usersroleId);
    
    GetUsersOutput getUsers(UsersroleId usersroleId);
    
    //Join Column Parsers
    
	UsersroleId parseUsersroleKey(String keysString);
}

