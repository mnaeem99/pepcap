package com.pepcap.taskmanagement.application.core.timesheets.dto;

import java.time.*;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class GetTasksOutput {

 	private LocalDateTime createdAt;
 	private String description;
 	private LocalDate dueDate;
 	private Integer id;
 	private String name;
 	private String status;
  	private Integer timesheetsId;

}

