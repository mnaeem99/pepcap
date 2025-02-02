package com.pepcap.taskmanagement.application.core.timesheets.dto;

import java.time.*;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter @Setter
public class UpdateTimesheetsInput {

  	private LocalDateTime createdAt;
  	
  	@NotNull(message = "date Should not be null")
  	private LocalDate date;
  	
  	@NotNull(message = "hoursWorked Should not be null")
  	private BigDecimal hoursWorked;
  	
  	@NotNull(message = "id Should not be null")
  	private Integer id;
  	
  	private Integer taskId;
  	private Integer userId;
  	private Long versiono;
  
}

