package com.pepcap.ecommerce.application.core.authorization.users.dto;

import java.time.*;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UpdateUsersInput {

  	private LocalDateTime createdAt;
  	
  	@Pattern(regexp = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message = "Email Address should be valid")
  	@NotNull(message = "email Should not be null")
 	@Length(max = 100, message = "email must be less than 100 characters")
  	private String email;
  	
  	@NotNull(message = "firstName Should not be null")
 	@Length(max = 50, message = "firstName must be less than 50 characters")
  	private String firstName;
  	
  	@NotNull(message = "id Should not be null")
  	private Integer id;
  	
  	@NotNull(message = "isActive Should not be null")
  	private Boolean isActive = false;
  	
  	private Boolean isEmailConfirmed;
  	
  	@NotNull(message = "lastName Should not be null")
 	@Length(max = 50, message = "lastName must be less than 50 characters")
  	private String lastName;
  	
 	@Length(max = 255, message = "password must be less than 255 characters")
  	private String password;
  	
 	@Length(max = 50, message = "phoneNumber must be less than 50 characters")
  	private String phoneNumber;
  	
  	@NotNull(message = "role Should not be null")
 	@Length(max = 50, message = "role must be less than 50 characters")
  	private String role;
  	
  	private LocalDateTime updatedAt;
  	
  	@NotNull(message = "username Should not be null")
 	@Length(max = 50, message = "username must be less than 50 characters")
  	private String username;
  	
  	private Long versiono;
  
}

