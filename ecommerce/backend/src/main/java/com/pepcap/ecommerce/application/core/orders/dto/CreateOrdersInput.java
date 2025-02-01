package com.pepcap.ecommerce.application.core.orders.dto;

import java.time.*;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CreateOrdersInput {

  	private LocalDateTime createdAt;
  
  	@Length(max = 50, message = "status must be less than 50 characters")
  	private String status;
  
  	@NotNull(message = "totalAmount Should not be null")
  	private BigDecimal totalAmount;
  
  	private Integer userId;

}

