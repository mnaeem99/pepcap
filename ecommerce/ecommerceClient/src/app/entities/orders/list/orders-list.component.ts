import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';

import { IOrders } from '../iorders';
import { OrdersService } from '../orders.service';
import { Router, ActivatedRoute } from '@angular/router';
import { OrdersNewComponent } from '../new/orders-new.component';
import { BaseListComponent, ListColumnType, PickerDialogService } from 'src/app/common/shared';
import { ErrorService } from 'src/app/core/services/error.service';
import { Globals } from 'src/app/core/services/globals';
import { GlobalPermissionService } from 'src/app/core/services/global-permission.service';

import { UsersService } from 'src/app/admin/user-management/users/users.service';

@Component({
  selector: 'app-orders-list',
  templateUrl: './orders-list.component.html',
  styleUrls: ['./orders-list.component.scss']
})
export class OrdersListComponent extends BaseListComponent<IOrders> implements OnInit {

	title = 'Orders';
	constructor(
		public router: Router,
		public route: ActivatedRoute,
		public global: Globals,
		public dialog: MatDialog,
		public changeDetectorRefs: ChangeDetectorRef,
		public pickerDialogService: PickerDialogService,
		public ordersService: OrdersService,
		public errorService: ErrorService,
		public usersService: UsersService,
		public globalPermissionService: GlobalPermissionService,
	) { 
		super(router, route, dialog, global, changeDetectorRefs, pickerDialogService, ordersService, errorService)
  }

	ngOnInit() {
		this.entityName = 'Orders';
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
					  	key: 'userId',
					  	value: undefined,
					  	referencedkey: 'id'
					  },
					  
				],
				isParent: false,
				descriptiveField: 'usersDescriptiveField',
				referencedDescriptiveField: 'id',
				service: this.usersService,
				associatedObj: undefined,
				table: 'users',
				type: 'ManyToOne',
				url: 'orders',
				listColumn: 'users',
				label: 'users',

			},
		];
	}
  
  	setColumns(){
  		this.columns = [
    		{
				column: 'totalAmount',
				searchColumn: 'totalAmount',
				label: 'total Amount',
				sort: true,
				filter: true,
				type: ListColumnType.Number
			},
    		{
				column: 'status',
				searchColumn: 'status',
				label: 'status',
				sort: true,
				filter: true,
				type: ListColumnType.String
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
	  			column: 'usersDescriptiveField',
				searchColumn: 'users',
				label: 'users',
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
		comp = OrdersNewComponent;
	}
	super.addNew(comp);
  }
  
}
