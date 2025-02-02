import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';

import { ITasks } from '../itasks';
import { TasksService } from '../tasks.service';
import { Router, ActivatedRoute } from '@angular/router';
import { TasksNewComponent } from '../new/tasks-new.component';
import { BaseListComponent, ListColumnType, PickerDialogService } from 'src/app/common/shared';
import { ErrorService } from 'src/app/core/services/error.service';
import { Globals } from 'src/app/core/services/globals';
import { GlobalPermissionService } from 'src/app/core/services/global-permission.service';

import { ProjectsService } from 'src/app/entities/projects/projects.service';
import { UsersService } from 'src/app/admin/user-management/users/users.service';

@Component({
  selector: 'app-tasks-list',
  templateUrl: './tasks-list.component.html',
  styleUrls: ['./tasks-list.component.scss']
})
export class TasksListComponent extends BaseListComponent<ITasks> implements OnInit {

	title = 'Tasks';
	constructor(
		public router: Router,
		public route: ActivatedRoute,
		public global: Globals,
		public dialog: MatDialog,
		public changeDetectorRefs: ChangeDetectorRef,
		public pickerDialogService: PickerDialogService,
		public tasksService: TasksService,
		public errorService: ErrorService,
		public projectsService: ProjectsService,
		public usersService: UsersService,
		public globalPermissionService: GlobalPermissionService,
	) { 
		super(router, route, dialog, global, changeDetectorRefs, pickerDialogService, tasksService, errorService)
  }

	ngOnInit() {
		this.entityName = 'Tasks';
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
					  	key: 'projectId',
					  	value: undefined,
					  	referencedkey: 'id'
					  },
					  
				],
				isParent: false,
				descriptiveField: 'projectsDescriptiveField',
				referencedDescriptiveField: 'id',
				service: this.projectsService,
				associatedObj: undefined,
				table: 'projects',
				type: 'ManyToOne',
				url: 'tasks',
				listColumn: 'projects',
				label: 'projects',

			},
			{
				column: [
            {
					  	key: 'assigneeId',
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
				url: 'tasks',
				listColumn: 'users',
				label: 'users',

			},
		];
	}
  
  	setColumns(){
  		this.columns = [
    		{
				column: 'status',
				searchColumn: 'status',
				label: 'status',
				sort: true,
				filter: true,
				type: ListColumnType.String
			},
    		{
				column: 'name',
				searchColumn: 'name',
				label: 'name',
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
				column: 'dueDate',
				searchColumn: 'dueDate',
				label: 'due Date',
				sort: true,
				filter: true,
				type: ListColumnType.Date
			},
    		{
				column: 'description',
				searchColumn: 'description',
				label: 'description',
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
	  			column: 'projectsDescriptiveField',
				searchColumn: 'projects',
				label: 'projects',
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
		comp = TasksNewComponent;
	}
	super.addNew(comp);
  }
  
}
