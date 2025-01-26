package com.pepcap.ecommerce.application.extended.orderitems;

import org.springframework.stereotype.Service;
import com.pepcap.ecommerce.application.core.orderitems.OrderItemsAppService;

import com.pepcap.ecommerce.domain.extended.orderitems.IOrderItemsRepositoryExtended;
import com.pepcap.ecommerce.domain.extended.orders.IOrdersRepositoryExtended;
import com.pepcap.ecommerce.domain.extended.products.IProductsRepositoryExtended;
import com.pepcap.ecommerce.commons.logging.LoggingHelper;

@Service("orderItemsAppServiceExtended")
public class OrderItemsAppServiceExtended extends OrderItemsAppService implements IOrderItemsAppServiceExtended {

	public OrderItemsAppServiceExtended(IOrderItemsRepositoryExtended orderItemsRepositoryExtended,
				IOrdersRepositoryExtended ordersRepositoryExtended,IProductsRepositoryExtended productsRepositoryExtended,IOrderItemsMapperExtended mapper,LoggingHelper logHelper) {

		super(orderItemsRepositoryExtended,
		ordersRepositoryExtended,productsRepositoryExtended,mapper,logHelper);

	}

 	//Add your custom code here
 
}

