package com.pepcap.ecommerce.application.core.products.dto;

import java.time.*;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter @Setter
public class UpdateProductsInput {

  	private LocalDateTime createdAt;
  	
  	private String description;
  	
  	@NotNull(message = "id Should not be null")
  	private Integer id;
  	
  	@NotNull(message = "name Should not be null")
 	@Length(max = 100, message = "name must be less than 100 characters")
  	private String name;
  	
  	@NotNull(message = "price Should not be null")
  	private BigDecimal price;
  	
  	private Integer stock;
  	
  	private LocalDateTime updatedAt;
  	
  	private Integer categoryId;
  	private Long versiono;
  
}

