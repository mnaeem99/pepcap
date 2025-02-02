package com.pepcap.taskmanagement.application.core.authorization.users.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ForgotPasswordInput {
	private String email;
	private String clientUrl;
}
