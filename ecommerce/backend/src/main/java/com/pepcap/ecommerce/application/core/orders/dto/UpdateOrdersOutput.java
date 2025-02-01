package com.pepcap.ecommerce.application.core.orders.dto;

import java.time.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter @Setter
public class UpdateOrdersOutput {

  	private LocalDateTime createdAt;
  	private Integer id;
  	private String status;
  	private BigDecimal totalAmount;
  	private Integer userId;
	private Integer usersDescriptiveField;

}
