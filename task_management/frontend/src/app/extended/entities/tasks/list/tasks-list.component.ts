import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';

import { Router, ActivatedRoute } from '@angular/router';

import { TasksExtendedService } from '../tasks.service';
import { TasksNewExtendedComponent } from '../new/tasks-new.component';
import { PickerDialogService } from 'src/app/common/shared';
import { ErrorService } from 'src/app/core/services/error.service';
import { Globals } from 'src/app/core/services/globals';

import { ProjectsExtendedService } from 'src/app/extended/entities/projects/projects.service';
import { UsersExtendedService } from 'src/app/extended/admin/user-management/users/users.service';
import { GlobalPermissionService } from 'src/app/core/services/global-permission.service';
import { TasksListComponent } from 'src/app/entities/tasks/index';

@Component({
  selector: 'app-tasks-list',
  templateUrl: './tasks-list.component.html',
  styleUrls: ['./tasks-list.component.scss']
})
export class TasksListExtendedComponent extends TasksListComponent implements OnInit {

	title:string = "Tasks";
	constructor(
		public router: Router,
		public route: ActivatedRoute,
		public global: Globals,
		public dialog: MatDialog,
		public changeDetectorRefs: ChangeDetectorRef,
		public pickerDialogService: PickerDialogService,
		public tasksService: TasksExtendedService,
		public errorService: ErrorService,
		public projectsService: ProjectsExtendedService,
		public usersService: UsersExtendedService,
		public globalPermissionService: GlobalPermissionService,
	) { 
		super(router, route, global, dialog, changeDetectorRefs, pickerDialogService, tasksService, errorService,
		projectsService,
		usersService,
		globalPermissionService,
		)
  }

	ngOnInit() {
		super.ngOnInit();
	}
  
	addNew() {
		super.addNew(TasksNewExtendedComponent);
	}
  
}
