package com.pepcap.ecommerce.application.core.authorization.usersrole.dto;

import java.time.*;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class FindUsersroleByIdOutput {

  	private Long roleId;
  	private Integer usersId;
  	private String roleDescriptiveField;
  	private Integer usersDescriptiveField;
	private Long versiono;
 
}

