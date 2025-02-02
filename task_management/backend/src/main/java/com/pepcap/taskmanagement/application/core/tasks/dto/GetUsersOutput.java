package com.pepcap.taskmanagement.application.core.tasks.dto;

import java.time.*;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class GetUsersOutput {

 	private LocalDateTime createdAt;
 	private String email;
 	private String firstName;
 	private Integer id;
 	private Boolean isActive;
 	private Boolean isEmailConfirmed;
 	private String lastName;
 	private String password;
 	private String phoneNumber;
 	private String role;
 	private LocalDateTime updatedAt;
 	private String username;
  	private Integer tasksId;

}

