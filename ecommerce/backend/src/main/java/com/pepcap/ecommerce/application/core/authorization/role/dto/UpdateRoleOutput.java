package com.pepcap.ecommerce.application.core.authorization.role.dto;

import java.time.*;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UpdateRoleOutput {

  	private String displayName;
  	private Long id;
  	private String name;

}
