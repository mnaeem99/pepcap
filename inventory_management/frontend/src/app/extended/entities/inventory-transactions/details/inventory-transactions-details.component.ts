import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from "@angular/router";
import { FormBuilder } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';

import { InventoryTransactionsExtendedService } from '../inventory-transactions.service';

import { PickerDialogService } from 'src/app/common/shared';
import { ErrorService } from 'src/app/core/services/error.service';

import { ProductsExtendedService } from 'src/app/extended/entities/products/products.service';

import { InventoryTransactionsDetailsComponent } from 'src/app/entities/inventory-transactions/index';

@Component({
  selector: 'app-inventory-transactions-details',
  templateUrl: './inventory-transactions-details.component.html',
  styleUrls: ['./inventory-transactions-details.component.scss']
})
export class InventoryTransactionsDetailsExtendedComponent extends InventoryTransactionsDetailsComponent implements OnInit {
	title:string='InventoryTransactions';
	parentUrl:string='inventorytransactions';
	//roles: IRole[];  
	constructor(
		public formBuilder: FormBuilder,
		public router: Router,
		public route: ActivatedRoute,
		public dialog: MatDialog,
		public inventoryTransactionsExtendedService: InventoryTransactionsExtendedService,
		public pickerDialogService: PickerDialogService,
		public errorService: ErrorService,
		public productsExtendedService: ProductsExtendedService,
	) {
		super(formBuilder, router, route, dialog, inventoryTransactionsExtendedService, pickerDialogService, errorService,
		productsExtendedService,
);
  }

	ngOnInit() {
		super.ngOnInit();
  }
  
}
