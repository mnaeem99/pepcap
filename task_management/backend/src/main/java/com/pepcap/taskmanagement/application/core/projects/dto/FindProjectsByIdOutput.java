package com.pepcap.taskmanagement.application.core.projects.dto;

import java.time.*;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class FindProjectsByIdOutput {

  	private LocalDateTime createdAt;
  	private String description;
  	private LocalDate endDate;
  	private Integer id;
  	private String name;
  	private LocalDate startDate;
  	private String status;
	private Long versiono;
 
}

