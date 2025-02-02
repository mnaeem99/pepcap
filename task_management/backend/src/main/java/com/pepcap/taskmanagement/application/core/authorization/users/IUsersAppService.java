package com.pepcap.taskmanagement.application.core.authorization.users;

import org.springframework.data.domain.Pageable;
import com.pepcap.taskmanagement.domain.core.authorization.users.Users;
import com.pepcap.taskmanagement.domain.core.authorization.userspreference.Userspreference;
import com.pepcap.taskmanagement.application.core.authorization.users.dto.*;
import com.pepcap.taskmanagement.commons.search.SearchCriteria;
import java.net.MalformedURLException;
import java.util.*;

public interface IUsersAppService {
	
	//CRUD Operations
	CreateUsersOutput create(CreateUsersInput users);

    void delete(Integer id);

    UpdateUsersOutput update(Integer id, UpdateUsersInput input);

    FindUsersByIdOutput findById(Integer id);


    List<FindUsersByIdOutput> find(SearchCriteria search, Pageable pageable) throws MalformedURLException;
    
   	Userspreference createDefaultUsersPreference(Users users);
   	
   	void updateTheme(Users users, String theme);
   	
   	void updateLanguage(Users users, String language);
    
    void updateUsersData(FindUsersWithAllFieldsByIdOutput users);
	
	UsersProfile updateUsersProfile(FindUsersWithAllFieldsByIdOutput users, UsersProfile usersProfile);
	
	FindUsersWithAllFieldsByIdOutput findWithAllFieldsById(Integer usersId);
	
	UsersProfile getProfile(FindUsersByIdOutput user);
	 
	Users getUsers();
	
	FindUsersByUsernameOutput findByUsername(String username);
	
	FindUsersByUsernameOutput findByEmail(String emailAddress);
    
    //Join Column Parsers

	Map<String,String> parseTasksJoinColumn(String keysString);

	Map<String,String> parseTimesheetsJoinColumn(String keysString);

	Map<String,String> parseUserspermissionsJoinColumn(String keysString);

	Map<String,String> parseUsersrolesJoinColumn(String keysString);
}

