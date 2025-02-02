package com.pepcap.taskmanagement.application.core.timesheets.dto;

import java.time.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CreateTimesheetsInput {

  	private LocalDateTime createdAt;
  
  	@NotNull(message = "date Should not be null")
  	private LocalDate date;
  
  	@NotNull(message = "hoursWorked Should not be null")
  	private BigDecimal hoursWorked;
  
  	private Integer taskId;
  	private Integer userId;

}

