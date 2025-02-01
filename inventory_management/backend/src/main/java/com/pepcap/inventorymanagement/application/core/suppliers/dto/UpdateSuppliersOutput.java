package com.pepcap.inventorymanagement.application.core.suppliers.dto;

import java.time.*;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UpdateSuppliersOutput {

  	private String contactInfo;
  	private LocalDateTime createdAt;
  	private Integer id;
  	private String name;

}
