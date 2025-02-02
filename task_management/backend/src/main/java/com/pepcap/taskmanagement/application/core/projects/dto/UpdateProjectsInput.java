package com.pepcap.taskmanagement.application.core.projects.dto;

import java.time.*;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UpdateProjectsInput {

  	private LocalDateTime createdAt;
  	
  	private String description;
  	
  	private LocalDate endDate;
  	
  	@NotNull(message = "id Should not be null")
  	private Integer id;
  	
  	@NotNull(message = "name Should not be null")
 	@Length(max = 100, message = "name must be less than 100 characters")
  	private String name;
  	
  	private LocalDate startDate;
  	
 	@Length(max = 50, message = "status must be less than 50 characters")
  	private String status;
  	
  	private Long versiono;
  
}

