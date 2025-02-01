package com.pepcap.ecommerce.restcontrollers.extended;

import org.springframework.web.bind.annotation.*;
import com.pepcap.ecommerce.restcontrollers.core.OrdersController;
import com.pepcap.ecommerce.application.extended.orders.IOrdersAppServiceExtended;

import com.pepcap.ecommerce.application.extended.orderitems.IOrderItemsAppServiceExtended;
import com.pepcap.ecommerce.application.extended.authorization.users.IUsersAppServiceExtended;
import org.springframework.core.env.Environment;
import com.pepcap.ecommerce.commons.logging.LoggingHelper;

@RestController
@RequestMapping("/orders/extended")
public class OrdersControllerExtended extends OrdersController {

		public OrdersControllerExtended(IOrdersAppServiceExtended ordersAppServiceExtended, IOrderItemsAppServiceExtended orderItemsAppServiceExtended, IUsersAppServiceExtended usersAppServiceExtended,
	     LoggingHelper helper, Environment env) {
		super(
		ordersAppServiceExtended,
		
    	orderItemsAppServiceExtended,
		
    	usersAppServiceExtended,
		helper, env);
	}

	//Add your custom code here

}

