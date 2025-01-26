package com.pepcap.ecommerce.application.core.authorization.userspermission;

import com.pepcap.ecommerce.domain.core.authorization.userspermission.UserspermissionId;
import org.springframework.data.domain.Pageable;
import com.pepcap.ecommerce.application.core.authorization.userspermission.dto.*;
import com.pepcap.ecommerce.commons.search.SearchCriteria;
import java.net.MalformedURLException;
import java.util.*;

public interface IUserspermissionAppService {
	
	//CRUD Operations
	CreateUserspermissionOutput create(CreateUserspermissionInput userspermission);

    void delete(UserspermissionId userspermissionId);

    UpdateUserspermissionOutput update(UserspermissionId userspermissionId, UpdateUserspermissionInput input);

    FindUserspermissionByIdOutput findById(UserspermissionId userspermissionId);


    List<FindUserspermissionByIdOutput> find(SearchCriteria search, Pageable pageable) throws MalformedURLException;
	//Relationship Operations
	//Relationship Operations
    
    GetPermissionOutput getPermission(UserspermissionId userspermissionId);
    
    GetUsersOutput getUsers(UserspermissionId userspermissionId);
    
    //Join Column Parsers
    
	UserspermissionId parseUserspermissionKey(String keysString);
}

