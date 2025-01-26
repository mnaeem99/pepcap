package com.pepcap.ecommerce.application.core.authorization.userspermission.dto;

import java.time.*;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class FindUserspermissionByIdOutput {

  	private Long permissionId;
  	private Boolean revoked;
  	private Integer usersId;
  	private String permissionDescriptiveField;
  	private Integer usersDescriptiveField;
	private Long versiono;
 
}

