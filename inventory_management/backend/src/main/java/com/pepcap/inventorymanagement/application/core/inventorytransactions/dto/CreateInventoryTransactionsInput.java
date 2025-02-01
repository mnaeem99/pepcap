package com.pepcap.inventorymanagement.application.core.inventorytransactions.dto;

import java.time.*;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CreateInventoryTransactionsInput {

  	private LocalDateTime createdAt;
  
  	@NotNull(message = "quantity Should not be null")
  	private Integer quantity;
  
  	@NotNull(message = "transactionType Should not be null")
  	@Length(max = 50, message = "transactionType must be less than 50 characters")
  	private String transactionType;
  
  	private Integer productId;

}

