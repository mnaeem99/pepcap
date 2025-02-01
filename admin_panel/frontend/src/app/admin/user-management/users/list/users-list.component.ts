import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';

import { IUsers } from '../iusers';
import { UsersService } from '../users.service';
import { Router, ActivatedRoute } from '@angular/router';
import { UsersNewComponent } from '../new/users-new.component';
import { BaseListComponent, ListColumnType, PickerDialogService } from 'src/app/common/shared';
import { ErrorService } from 'src/app/core/services/error.service';
import { Globals } from 'src/app/core/services/globals';
import { GlobalPermissionService } from 'src/app/core/services/global-permission.service';


@Component({
  selector: 'app-users-list',
  templateUrl: './users-list.component.html',
  styleUrls: ['./users-list.component.scss']
})
export class UsersListComponent extends BaseListComponent<IUsers> implements OnInit {

	title = 'Users';
	constructor(
		public router: Router,
		public route: ActivatedRoute,
		public global: Globals,
		public dialog: MatDialog,
		public changeDetectorRefs: ChangeDetectorRef,
		public pickerDialogService: PickerDialogService,
		public usersService: UsersService,
		public errorService: ErrorService,
		public globalPermissionService: GlobalPermissionService,
	) { 
		super(router, route, dialog, global, changeDetectorRefs, pickerDialogService, usersService, errorService)
  }

	ngOnInit() {
		this.entityName = 'Users';
		this.setAssociation();
		this.setColumns();
		this.primaryKeys = ['id', ]
		super.ngOnInit();
	}
  
  
	setAssociation(){
  	
		this.associations = [
		];
	}
  
  	setColumns(){
  		this.columns = [
    		{
				column: 'username',
				searchColumn: 'username',
				label: 'username',
				sort: true,
				filter: true,
				type: ListColumnType.String
			},
    		{
				column: 'updatedAt',
				searchColumn: 'updatedAt',
				label: 'updated At',
				sort: true,
				filter: true,
				type: ListColumnType.DateTime
			},
    		{
				column: 'role',
				searchColumn: 'role',
				label: 'role',
				sort: true,
				filter: true,
				type: ListColumnType.String
			},
    		{
				column: 'phoneNumber',
				searchColumn: 'phoneNumber',
				label: 'phone Number',
				sort: true,
				filter: true,
				type: ListColumnType.String
			},
    		{
				column: 'lastName',
				searchColumn: 'lastName',
				label: 'last Name',
				sort: true,
				filter: true,
				type: ListColumnType.String
			},
    		{
				column: 'isEmailConfirmed',
				searchColumn: 'isEmailConfirmed',
				label: 'is Email Confirmed',
				sort: true,
				filter: true,
				type: ListColumnType.Boolean
			},
    		{
				column: 'isActive',
				searchColumn: 'isActive',
				label: 'is Active',
				sort: true,
				filter: true,
				type: ListColumnType.Boolean
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
				column: 'firstName',
				searchColumn: 'firstName',
				label: 'first Name',
				sort: true,
				filter: true,
				type: ListColumnType.String
			},
    		{
				column: 'email',
				searchColumn: 'email',
				label: 'email',
				sort: true,
				filter: true,
				type: ListColumnType.String
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
		comp = UsersNewComponent;
	}
	super.addNew(comp);
  }
  
}
