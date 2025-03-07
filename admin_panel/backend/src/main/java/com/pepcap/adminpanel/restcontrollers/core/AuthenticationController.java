package com.pepcap.adminpanel.restcontrollers.core;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import javax.servlet.http.Cookie;
import com.pepcap.adminpanel.security.JWTAppService;
import com.pepcap.adminpanel.security.SecurityConstants;
import com.pepcap.adminpanel.commons.domain.EmptyJsonResponse;
import org.springframework.http.ResponseEntity; 
import org.springframework.http.HttpStatus; 
import org.springframework.web.bind.annotation.RequestMapping; 
import org.springframework.web.bind.annotation.RequestMethod; 
import org.springframework.web.bind.annotation.RestController; 
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
@RestController 
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController { 

	@NonNull protected final JWTAppService _jwtAppService;

	@NonNull protected final HttpServletRequest request;
	
	@NonNull protected final HttpServletResponse response;

	@RequestMapping(value = "/logout", method = RequestMethod.POST) 
	public ResponseEntity logout() { 

		String token = request.getHeader(SecurityConstants.HEADER_STRING);
		_jwtAppService.deleteToken(token);

		handleLogOutResponse(response);
		return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.OK); 
	} 

	private void handleLogOutResponse(HttpServletResponse response) {
		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			if(cookie.getName().equals("Authentication")) {
			cookie.setMaxAge(0);
			cookie.setValue(null);
			cookie.setPath("/");
			response.addCookie(cookie);
			}
		}
	}
    
    @RequestMapping(method = RequestMethod.OPTIONS) 
    public ResponseEntity getCsrfToken(HttpServletRequest request) {
        return new ResponseEntity<>("", HttpStatus.OK);
    }
    
} 

