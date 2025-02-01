package com.pepcap.inventorymanagement.application.core.inventorytransactions.dto;

import java.time.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter @Setter
public class GetProductsOutput {

 	private LocalDateTime createdAt;
 	private String description;
 	private Integer id;
 	private String name;
 	private BigDecimal price;
 	private Integer stock;
 	private LocalDateTime updatedAt;
  	private Integer inventoryTransactionsId;

}

