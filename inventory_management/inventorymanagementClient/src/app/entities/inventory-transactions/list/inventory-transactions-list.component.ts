import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';

import { IInventoryTransactions } from '../iinventory-transactions';
import { InventoryTransactionsService } from '../inventory-transactions.service';
import { Router, ActivatedRoute } from '@angular/router';
import { InventoryTransactionsNewComponent } from '../new/inventory-transactions-new.component';
import { BaseListComponent, ListColumnType, PickerDialogService } from 'src/app/common/shared';
import { ErrorService } from 'src/app/core/services/error.service';
import { Globals } from 'src/app/core/services/globals';

import { ProductsService } from 'src/app/entities/products/products.service';

@Component({
  selector: 'app-inventory-transactions-list',
  templateUrl: './inventory-transactions-list.component.html',
  styleUrls: ['./inventory-transactions-list.component.scss']
})
export class InventoryTransactionsListComponent extends BaseListComponent<IInventoryTransactions> implements OnInit {

	title = 'InventoryTransactions';
	constructor(
		public router: Router,
		public route: ActivatedRoute,
		public global: Globals,
		public dialog: MatDialog,
		public changeDetectorRefs: ChangeDetectorRef,
		public pickerDialogService: PickerDialogService,
		public inventoryTransactionsService: InventoryTransactionsService,
		public errorService: ErrorService,
		public productsService: ProductsService,
	) { 
		super(router, route, dialog, global, changeDetectorRefs, pickerDialogService, inventoryTransactionsService, errorService)
  }

	ngOnInit() {
		this.entityName = 'InventoryTransactions';
		this.setAssociation();
		this.setColumns();
		this.primaryKeys = ['id', ]
		super.ngOnInit();
	}
  
  
	setAssociation(){
  	
		this.associations = [
			{
				column: [
            {
					  	key: 'productId',
					  	value: undefined,
					  	referencedkey: 'id'
					  },
					  
				],
				isParent: false,
				descriptiveField: 'productsDescriptiveField',
				referencedDescriptiveField: 'id',
				service: this.productsService,
				associatedObj: undefined,
				table: 'products',
				type: 'ManyToOne',
				url: 'inventoryTransactions',
				listColumn: 'products',
				label: 'products',

			},
		];
	}
  
  	setColumns(){
  		this.columns = [
    		{
				column: 'transactionType',
				searchColumn: 'transactionType',
				label: 'transaction Type',
				sort: true,
				filter: true,
				type: ListColumnType.String
			},
    		{
				column: 'quantity',
				searchColumn: 'quantity',
				label: 'quantity',
				sort: true,
				filter: true,
				type: ListColumnType.Number
			},
    		{
				column: 'id',
				searchColumn: 'id',
				label: 'id',
				sort: true,
				filter: true,
				type: ListColumnType.Number
			},
    		{
				column: 'createdAt',
				searchColumn: 'createdAt',
				label: 'created At',
				sort: true,
				filter: true,
				type: ListColumnType.DateTime
			},
			{
	  			column: 'productsDescriptiveField',
				searchColumn: 'products',
				label: 'products',
				sort: true,
				filter: true,
				type: ListColumnType.String
	  		},
		  	{
				column: 'actions',
				label: 'Actions',
				sort: false,
				filter: false,
				type: ListColumnType.String
			}
		];
		this.selectedColumns = this.columns;
		this.displayedColumns = this.columns.map((obj) => { return obj.column });
  	}
  addNew(comp: any) {
	if(!comp){
		comp = InventoryTransactionsNewComponent;
	}
	super.addNew(comp);
  }
  
}
