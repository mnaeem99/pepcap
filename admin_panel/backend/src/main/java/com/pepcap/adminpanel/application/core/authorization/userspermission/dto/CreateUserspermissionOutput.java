package com.pepcap.adminpanel.application.core.authorization.userspermission.dto;

import java.time.*;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CreateUserspermissionOutput {

    private Long permissionId;
    private Boolean revoked;
    private Integer usersId;
	private String permissionDescriptiveField;
	private String usersDescriptiveField;

}

