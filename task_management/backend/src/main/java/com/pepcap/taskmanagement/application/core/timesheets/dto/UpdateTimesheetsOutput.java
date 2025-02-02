package com.pepcap.taskmanagement.application.core.timesheets.dto;

import java.time.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter @Setter
public class UpdateTimesheetsOutput {

  	private LocalDateTime createdAt;
  	private LocalDate date;
  	private BigDecimal hoursWorked;
  	private Integer id;
  	private Integer taskId;
	private Integer tasksDescriptiveField;
  	private Integer userId;
	private Integer usersDescriptiveField;

}
