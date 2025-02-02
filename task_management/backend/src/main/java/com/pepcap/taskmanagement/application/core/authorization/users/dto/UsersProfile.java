package com.pepcap.taskmanagement.application.core.authorization.users.dto;

import java.time.*;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UsersProfile {

	@NotNull(message = "username Should not be null")
	@Length(max = 50, message = "username must be less than 50 characters")
	private String username;
	
	@NotNull(message = "email Should not be null")
	@Length(max = 100, message = "email must be less than 100 characters")
	@Pattern(regexp = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message = "Email should be valid")
	private String email;
	
  	private LocalDateTime createdAt;
  	
    @NotNull(message = "firstName Should not be null")
  	private String firstName;
  	
    @NotNull(message = "lastName Should not be null")
  	private String lastName;
  	
  	private String phoneNumber;
  	
    @NotNull(message = "role Should not be null")
  	private String role;
  	
  	private LocalDateTime updatedAt;
  	
	
	private String theme;
    private String language;
	
}

