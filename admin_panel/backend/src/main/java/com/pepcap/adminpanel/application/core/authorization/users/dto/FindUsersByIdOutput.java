package com.pepcap.adminpanel.application.core.authorization.users.dto;

import java.time.*;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class FindUsersByIdOutput {

  	private LocalDateTime createdAt;
  	private String email;
  	private String firstName;
  	private Integer id;
  	private Boolean isActive;
  	private Boolean isEmailConfirmed;
  	private String lastName;
  	private String phoneNumber;
  	private String role;
  	private LocalDateTime updatedAt;
  	private String username;
	private Long versiono;
	private String theme;
    private String language;
 
}

