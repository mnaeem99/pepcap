package com.pepcap.taskmanagement.application.core.authorization.users.dto;

import java.time.*;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CreateUsersInput {

  	private LocalDateTime createdAt;
  
	@Pattern(regexp = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message = "Email Address should be valid")
  	@Length(max = 100, message = "email must be less than 100 characters")
  	private String email;
  
  	@Length(max = 50, message = "firstName must be less than 50 characters")
  	private String firstName;
  
  	private Boolean isActive = false;
  
  	private Boolean isEmailConfirmed;
  
  	@Length(max = 50, message = "lastName must be less than 50 characters")
  	private String lastName;
  
  	@Length(max = 255, message = "password must be less than 255 characters")
  	private String password;
  
  	@Length(max = 50, message = "phoneNumber must be less than 50 characters")
  	private String phoneNumber;
  
  	@Length(max = 50, message = "role must be less than 50 characters")
  	private String role;
  
  	private LocalDateTime updatedAt;
  
  	@Length(max = 50, message = "username must be less than 50 characters")
  	private String username;
  

}

