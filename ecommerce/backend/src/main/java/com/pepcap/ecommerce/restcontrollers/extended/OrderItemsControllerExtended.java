package com.pepcap.ecommerce.restcontrollers.extended;

import org.springframework.web.bind.annotation.*;
import com.pepcap.ecommerce.restcontrollers.core.OrderItemsController;
import com.pepcap.ecommerce.application.extended.orderitems.IOrderItemsAppServiceExtended;

import com.pepcap.ecommerce.application.extended.orders.IOrdersAppServiceExtended;
import com.pepcap.ecommerce.application.extended.products.IProductsAppServiceExtended;
import org.springframework.core.env.Environment;
import com.pepcap.ecommerce.commons.logging.LoggingHelper;

@RestController
@RequestMapping("/orderItems/extended")
public class OrderItemsControllerExtended extends OrderItemsController {

		public OrderItemsControllerExtended(IOrderItemsAppServiceExtended orderItemsAppServiceExtended, IOrdersAppServiceExtended ordersAppServiceExtended, IProductsAppServiceExtended productsAppServiceExtended,
	     LoggingHelper helper, Environment env) {
		super(
		orderItemsAppServiceExtended,
		
    	ordersAppServiceExtended,
		
    	productsAppServiceExtended,
		helper, env);
	}

	//Add your custom code here

}

