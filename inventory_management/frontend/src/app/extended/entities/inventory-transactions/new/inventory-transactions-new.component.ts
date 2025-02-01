import { Component, OnInit, Inject } from '@angular/core';
import { InventoryTransactionsExtendedService } from '../inventory-transactions.service';

import { ActivatedRoute,Router } from "@angular/router";
import { FormBuilder } from '@angular/forms';
import { PickerDialogService } from 'src/app/common/shared';
import { ErrorService } from 'src/app/core/services/error.service';
import { MatDialogRef, MatDialog, MAT_DIALOG_DATA } from '@angular/material/dialog';

import { ProductsExtendedService } from 'src/app/extended/entities/products/products.service';

import { InventoryTransactionsNewComponent } from 'src/app/entities/inventory-transactions/index';

@Component({
  selector: 'app-inventory-transactions-new',
  templateUrl: './inventory-transactions-new.component.html',
  styleUrls: ['./inventory-transactions-new.component.scss']
})
export class InventoryTransactionsNewExtendedComponent extends InventoryTransactionsNewComponent implements OnInit {
  
    title:string = "New InventoryTransactions";
	constructor(
		public formBuilder: FormBuilder,
		public router: Router,
		public route: ActivatedRoute,
		public dialog: MatDialog,
		public dialogRef: MatDialogRef<InventoryTransactionsNewComponent>,
		@Inject(MAT_DIALOG_DATA) public data: any,
		public pickerDialogService: PickerDialogService,
		public inventoryTransactionsExtendedService: InventoryTransactionsExtendedService,
		public errorService: ErrorService,
		public productsExtendedService: ProductsExtendedService,
	) {
		super(formBuilder, router, route, dialog, dialogRef, data, pickerDialogService, inventoryTransactionsExtendedService, errorService,
		productsExtendedService,
		);
	}
 
	ngOnInit() {
		super.ngOnInit();
  }
     
}
