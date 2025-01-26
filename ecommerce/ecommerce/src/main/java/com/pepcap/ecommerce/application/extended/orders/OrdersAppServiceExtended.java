package com.pepcap.ecommerce.application.extended.orders;

import org.springframework.stereotype.Service;
import com.pepcap.ecommerce.application.core.orders.OrdersAppService;

import com.pepcap.ecommerce.domain.extended.orders.IOrdersRepositoryExtended;
import com.pepcap.ecommerce.domain.extended.authorization.users.IUsersRepositoryExtended;
import com.pepcap.ecommerce.commons.logging.LoggingHelper;

@Service("ordersAppServiceExtended")
public class OrdersAppServiceExtended extends OrdersAppService implements IOrdersAppServiceExtended {

	public OrdersAppServiceExtended(IOrdersRepositoryExtended ordersRepositoryExtended,
				IUsersRepositoryExtended usersRepositoryExtended,IOrdersMapperExtended mapper,LoggingHelper logHelper) {

		super(ordersRepositoryExtended,
		usersRepositoryExtended,mapper,logHelper);

	}

 	//Add your custom code here
 
}

