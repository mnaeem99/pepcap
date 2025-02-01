package com.pepcap.adminpanel.domain.core.authorization.tokenverification;

import org.springframework.stereotype.Component;
import java.time.*;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TokenVerificationManager implements ITokenVerificationManager {

	@NonNull private final ITokenverificationRepository _tokenRepository;
	
	public Tokenverification findByTokenAndType(String token, String tokenType) {
		return  _tokenRepository.findByTokenAndTokenType(token, tokenType);
	}
	
	public Tokenverification save(Tokenverification entity) {
		return  _tokenRepository.save(entity);
	}
	
	public Tokenverification findByUsersIdAndType(Integer id, String tokenType) {

		return  _tokenRepository.findByUsersIdAndTokenType(id, tokenType);
	}
	
	public void delete(Tokenverification entity) {
		 _tokenRepository.delete(entity);
	}
	
}

