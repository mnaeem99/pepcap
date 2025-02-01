package com.pepcap.inventorymanagement.restcontrollers.extended;

import org.springframework.web.bind.annotation.*;
import com.pepcap.inventorymanagement.restcontrollers.core.SuppliersController;
import com.pepcap.inventorymanagement.application.extended.suppliers.ISuppliersAppServiceExtended;

import org.springframework.core.env.Environment;
import com.pepcap.inventorymanagement.commons.logging.LoggingHelper;

@RestController
@RequestMapping("/suppliers/extended")
public class SuppliersControllerExtended extends SuppliersController {

		public SuppliersControllerExtended(ISuppliersAppServiceExtended suppliersAppServiceExtended,
	     LoggingHelper helper, Environment env) {
		super(
		suppliersAppServiceExtended,
		helper, env);
	}

	//Add your custom code here

}

