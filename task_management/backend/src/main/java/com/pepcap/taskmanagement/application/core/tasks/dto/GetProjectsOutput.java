package com.pepcap.taskmanagement.application.core.tasks.dto;

import java.time.*;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class GetProjectsOutput {

 	private LocalDateTime createdAt;
 	private String description;
 	private LocalDate endDate;
 	private Integer id;
 	private String name;
 	private LocalDate startDate;
 	private String status;
  	private Integer tasksId;

}

