package com.pepcap.adminpanel.application.core.categories.dto;

import java.time.*;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UpdateCategoriesInput {

  	private LocalDateTime createdAt;
  	
  	private String description;
  	
  	@NotNull(message = "id Should not be null")
  	private Integer id;
  	
  	@NotNull(message = "name Should not be null")
 	@Length(max = 100, message = "name must be less than 100 characters")
  	private String name;
  	
  	private LocalDateTime updatedAt;
  	
  	private Long versiono;
  
}

