package com.pepcap.adminpanel.application.core.authorization.rolepermission.dto;

import java.time.*;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class FindRolepermissionByIdOutput {

  	private Long permissionId;
  	private Long roleId;
  	private String permissionDescriptiveField;
  	private String roleDescriptiveField;
	private Long versiono;
 
}

