package com.pepcap.inventorymanagement.application.core.products.dto;

import java.time.*;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class GetCategoriesOutput {

 	private LocalDateTime createdAt;
 	private String description;
 	private Integer id;
 	private String name;
 	private LocalDateTime updatedAt;
  	private Integer productsId;

}

