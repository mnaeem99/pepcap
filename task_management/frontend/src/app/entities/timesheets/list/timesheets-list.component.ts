import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';

import { ITimesheets } from '../itimesheets';
import { TimesheetsService } from '../timesheets.service';
import { Router, ActivatedRoute } from '@angular/router';
import { TimesheetsNewComponent } from '../new/timesheets-new.component';
import { BaseListComponent, ListColumnType, PickerDialogService } from 'src/app/common/shared';
import { ErrorService } from 'src/app/core/services/error.service';
import { Globals } from 'src/app/core/services/globals';
import { GlobalPermissionService } from 'src/app/core/services/global-permission.service';

import { TasksService } from 'src/app/entities/tasks/tasks.service';
import { UsersService } from 'src/app/admin/user-management/users/users.service';

@Component({
  selector: 'app-timesheets-list',
  templateUrl: './timesheets-list.component.html',
  styleUrls: ['./timesheets-list.component.scss']
})
export class TimesheetsListComponent extends BaseListComponent<ITimesheets> implements OnInit {

	title = 'Timesheets';
	constructor(
		public router: Router,
		public route: ActivatedRoute,
		public global: Globals,
		public dialog: MatDialog,
		public changeDetectorRefs: ChangeDetectorRef,
		public pickerDialogService: PickerDialogService,
		public timesheetsService: TimesheetsService,
		public errorService: ErrorService,
		public tasksService: TasksService,
		public usersService: UsersService,
		public globalPermissionService: GlobalPermissionService,
	) { 
		super(router, route, dialog, global, changeDetectorRefs, pickerDialogService, timesheetsService, errorService)
  }

	ngOnInit() {
		this.entityName = 'Timesheets';
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
					  	key: 'taskId',
					  	value: undefined,
					  	referencedkey: 'id'
					  },
					  
				],
				isParent: false,
				descriptiveField: 'tasksDescriptiveField',
				referencedDescriptiveField: 'id',
				service: this.tasksService,
				associatedObj: undefined,
				table: 'tasks',
				type: 'ManyToOne',
				url: 'timesheets',
				listColumn: 'tasks',
				label: 'tasks',

			},
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
				url: 'timesheets',
				listColumn: 'users',
				label: 'users',

			},
		];
	}
  
  	setColumns(){
  		this.columns = [
    		{
				column: 'id',
				searchColumn: 'id',
				label: 'id',
				sort: true,
				filter: true,
				type: ListColumnType.Number
			},
    		{
				column: 'hoursWorked',
				searchColumn: 'hoursWorked',
				label: 'hours Worked',
				sort: true,
				filter: true,
				type: ListColumnType.Number
			},
    		{
				column: 'date',
				searchColumn: 'date',
				label: 'date',
				sort: true,
				filter: true,
				type: ListColumnType.Date
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
	  			column: 'tasksDescriptiveField',
				searchColumn: 'tasks',
				label: 'tasks',
				sort: true,
				filter: true,
				type: ListColumnType.String
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
		comp = TimesheetsNewComponent;
	}
	super.addNew(comp);
  }
  
}
