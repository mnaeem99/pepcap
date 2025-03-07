package com.pepcap.taskmanagement.application.core.authorization.usersrole.dto;

import java.time.*;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CreateUsersroleOutput {

    private Long roleId;
    private Integer usersId;
	private String roleDescriptiveField;
	private Integer usersDescriptiveField;

}

