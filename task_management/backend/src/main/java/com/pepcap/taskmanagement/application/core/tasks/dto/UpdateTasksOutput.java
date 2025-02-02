package com.pepcap.taskmanagement.application.core.tasks.dto;

import java.time.*;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UpdateTasksOutput {

  	private LocalDateTime createdAt;
  	private String description;
  	private LocalDate dueDate;
  	private Integer id;
  	private String name;
  	private String status;
  	private Integer projectId;
	private Integer projectsDescriptiveField;
  	private Integer assigneeId;
	private Integer usersDescriptiveField;

}
