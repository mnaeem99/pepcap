package com.pepcap.inventorymanagement.application.core.products.dto;

import java.time.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter @Setter
public class UpdateProductsOutput {

  	private LocalDateTime createdAt;
  	private String description;
  	private Integer id;
  	private String name;
  	private BigDecimal price;
  	private Integer stock;
  	private LocalDateTime updatedAt;
  	private Integer categoryId;
	private Integer categoriesDescriptiveField;

}
