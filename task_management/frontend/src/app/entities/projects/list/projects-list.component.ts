import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';

import { IProjects } from '../iprojects';
import { ProjectsService } from '../projects.service';
import { Router, ActivatedRoute } from '@angular/router';
import { ProjectsNewComponent } from '../new/projects-new.component';
import { BaseListComponent, ListColumnType, PickerDialogService } from 'src/app/common/shared';
import { ErrorService } from 'src/app/core/services/error.service';
import { Globals } from 'src/app/core/services/globals';
import { GlobalPermissionService } from 'src/app/core/services/global-permission.service';


@Component({
  selector: 'app-projects-list',
  templateUrl: './projects-list.component.html',
  styleUrls: ['./projects-list.component.scss']
})
export class ProjectsListComponent extends BaseListComponent<IProjects> implements OnInit {

	title = 'Projects';
	constructor(
		public router: Router,
		public route: ActivatedRoute,
		public global: Globals,
		public dialog: MatDialog,
		public changeDetectorRefs: ChangeDetectorRef,
		public pickerDialogService: PickerDialogService,
		public projectsService: ProjectsService,
		public errorService: ErrorService,
		public globalPermissionService: GlobalPermissionService,
	) { 
		super(router, route, dialog, global, changeDetectorRefs, pickerDialogService, projectsService, errorService)
  }

	ngOnInit() {
		this.entityName = 'Projects';
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
				column: 'status',
				searchColumn: 'status',
				label: 'status',
				sort: true,
				filter: true,
				type: ListColumnType.String
			},
    		{
				column: 'startDate',
				searchColumn: 'startDate',
				label: 'start Date',
				sort: true,
				filter: true,
				type: ListColumnType.Date
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
				column: 'endDate',
				searchColumn: 'endDate',
				label: 'end Date',
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
		comp = ProjectsNewComponent;
	}
	super.addNew(comp);
  }
  
}
