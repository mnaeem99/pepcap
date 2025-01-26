package com.pepcap.ecommerce.application.core.orders.dto;

import java.time.*;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter @Setter
public class UpdateOrdersInput {

  	private LocalDateTime createdAt;
  	
  	@NotNull(message = "id Should not be null")
  	private Integer id;
  	
 	@Length(max = 50, message = "status must be less than 50 characters")
  	private String status;
  	
  	@NotNull(message = "totalAmount Should not be null")
  	private BigDecimal totalAmount;
  	
  	private Integer userId;
  	private Long versiono;
  
}

