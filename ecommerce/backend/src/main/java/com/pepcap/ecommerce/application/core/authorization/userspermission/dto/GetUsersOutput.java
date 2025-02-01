package com.pepcap.ecommerce.application.core.authorization.userspermission.dto;

import java.time.*;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class GetUsersOutput {

 	private String lastName;
 	private String role;
 	private Boolean isActive;
 	private String firstName;
 	private String password;
 	private Integer id;
 	private String email;
 	private String username;
  	private Long userspermissionPermissionId;
  	private Integer userspermissionUsersId;

}

