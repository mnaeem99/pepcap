package com.pepcap.taskmanagement.application.core.authorization.users.dto;

import java.time.*;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UpdateUsersOutput {

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

}
