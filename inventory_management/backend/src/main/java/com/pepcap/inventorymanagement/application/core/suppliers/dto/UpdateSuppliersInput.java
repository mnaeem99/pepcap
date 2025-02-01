package com.pepcap.inventorymanagement.application.core.suppliers.dto;

import java.time.*;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UpdateSuppliersInput {

  	private String contactInfo;
  	
  	private LocalDateTime createdAt;
  	
  	@NotNull(message = "id Should not be null")
  	private Integer id;
  	
  	@NotNull(message = "name Should not be null")
 	@Length(max = 100, message = "name must be less than 100 characters")
  	private String name;
  	
  	private Long versiono;
  
}

