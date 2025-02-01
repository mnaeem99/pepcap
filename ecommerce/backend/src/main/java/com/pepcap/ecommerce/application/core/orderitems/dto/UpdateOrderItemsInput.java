package com.pepcap.ecommerce.application.core.orderitems.dto;

import java.time.*;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter @Setter
public class UpdateOrderItemsInput {

  	@NotNull(message = "id Should not be null")
  	private Integer id;
  	
  	@NotNull(message = "price Should not be null")
  	private BigDecimal price;
  	
  	@NotNull(message = "quantity Should not be null")
  	private Integer quantity;
  	
  	private Integer orderId;
  	private Integer productId;
  	private Long versiono;
  
}

