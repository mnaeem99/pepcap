package com.pepcap.inventorymanagement.application.core.categories.dto;

import java.time.*;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UpdateCategoriesOutput {

  	private LocalDateTime createdAt;
  	private String description;
  	private Integer id;
  	private String name;
  	private LocalDateTime updatedAt;

}
