package com.pepcap.inventorymanagement.application.core.inventorytransactions.dto;

import java.time.*;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class FindInventoryTransactionsByIdOutput {

  	private LocalDateTime createdAt;
  	private Integer id;
  	private Integer quantity;
  	private String transactionType;
  	private Integer productId;
  	private Integer productsDescriptiveField;
	private Long versiono;
 
}

