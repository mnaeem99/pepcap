package com.pepcap.taskmanagement.application.core.authorization.users.dto;

import java.time.*;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class GetUserspreferenceOutput {

 	private String lastName;
 	private String role;
 	private Boolean isActive;
 	private LocalDateTime createdAt;
 	private String firstName;
 	private String password;
 	private String phoneNumber;
 	private Integer id;
 	private String email;
 	private Boolean isEmailConfirmed;
 	private LocalDateTime updatedAt;
 	private String username;
  	private Integer usersId;

}

