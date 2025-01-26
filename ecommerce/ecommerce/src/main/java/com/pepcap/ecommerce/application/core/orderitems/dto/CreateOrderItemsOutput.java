package com.pepcap.ecommerce.application.core.orderitems.dto;

import java.time.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter @Setter
public class CreateOrderItemsOutput {

    private Integer id;
    private BigDecimal price;
    private Integer quantity;
	private Integer orderId;
	private Integer ordersDescriptiveField;
	private Integer productId;
	private Integer productsDescriptiveField;

}

