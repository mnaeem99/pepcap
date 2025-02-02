package com.pepcap.taskmanagement.application.core.authorization.tokenverification;

import com.pepcap.taskmanagement.domain.core.authorization.tokenverification.Tokenverification;

public interface ITokenVerificationAppService {
	
	Tokenverification findByTokenAndType(String token, String type);

	Tokenverification generateToken(String type,Integer usersId);

	Tokenverification findByUsersIdAndType(Integer usersId, String type);

	void deleteToken(Tokenverification entity);

}

