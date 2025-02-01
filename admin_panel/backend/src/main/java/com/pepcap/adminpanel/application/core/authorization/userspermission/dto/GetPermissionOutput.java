package com.pepcap.adminpanel.application.core.authorization.userspermission.dto;

import java.time.*;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class GetPermissionOutput {

 	private Long id;
 	private String name;
 	private String displayName;
  	private Long userspermissionPermissionId;
  	private Integer userspermissionUsersId;

}

