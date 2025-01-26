package com.pepcap.ecommerce.application.core.authorization.usersrole.dto;

import java.time.*;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class GetRoleOutput {

 	private Long id;
 	private String name;
 	private String displayName;
  	private Long usersroleRoleId;
  	private Integer usersroleUsersId;

}

