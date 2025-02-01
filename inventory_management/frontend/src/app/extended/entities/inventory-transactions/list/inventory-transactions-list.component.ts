import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';

import { Router, ActivatedRoute } from '@angular/router';

import { InventoryTransactionsExtendedService } from '../inventory-transactions.service';
import { InventoryTransactionsNewExtendedComponent } from '../new/inventory-transactions-new.component';
import { PickerDialogService } from 'src/app/common/shared';
import { ErrorService } from 'src/app/core/services/error.service';
import { Globals } from 'src/app/core/services/globals';

import { ProductsExtendedService } from 'src/app/extended/entities/products/products.service';
import { InventoryTransactionsListComponent } from 'src/app/entities/inventory-transactions/index';

@Component({
  selector: 'app-inventory-transactions-list',
  templateUrl: './inventory-transactions-list.component.html',
  styleUrls: ['./inventory-transactions-list.component.scss']
})
export class InventoryTransactionsListExtendedComponent extends InventoryTransactionsListComponent implements OnInit {

	title:string = "InventoryTransactions";
	constructor(
		public router: Router,
		public route: ActivatedRoute,
		public global: Globals,
		public dialog: MatDialog,
		public changeDetectorRefs: ChangeDetectorRef,
		public pickerDialogService: PickerDialogService,
		public inventoryTransactionsService: InventoryTransactionsExtendedService,
		public errorService: ErrorService,
		public productsService: ProductsExtendedService,
	) { 
		super(router, route, global, dialog, changeDetectorRefs, pickerDialogService, inventoryTransactionsService, errorService,
		productsService,
)
  }

	ngOnInit() {
		super.ngOnInit();
	}
  
	addNew() {
		super.addNew(InventoryTransactionsNewExtendedComponent);
	}
  
}
