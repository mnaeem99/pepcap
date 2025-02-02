import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from "@angular/router";
import { FormBuilder } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';

import { TasksExtendedService } from '../tasks.service';

import { PickerDialogService } from 'src/app/common/shared';
import { ErrorService } from 'src/app/core/services/error.service';

import { ProjectsExtendedService } from 'src/app/extended/entities/projects/projects.service';
import { UsersExtendedService } from 'src/app/extended/admin/user-management/users/users.service';

import { GlobalPermissionService } from 'src/app/core/services/global-permission.service';
import { TasksDetailsComponent } from 'src/app/entities/tasks/index';

@Component({
  selector: 'app-tasks-details',
  templateUrl: './tasks-details.component.html',
  styleUrls: ['./tasks-details.component.scss']
})
export class TasksDetailsExtendedComponent extends TasksDetailsComponent implements OnInit {
	title:string='Tasks';
	parentUrl:string='tasks';
	//roles: IRole[];  
	constructor(
		public formBuilder: FormBuilder,
		public router: Router,
		public route: ActivatedRoute,
		public dialog: MatDialog,
		public tasksExtendedService: TasksExtendedService,
		public pickerDialogService: PickerDialogService,
		public errorService: ErrorService,
		public projectsExtendedService: ProjectsExtendedService,
		public usersExtendedService: UsersExtendedService,
		public globalPermissionService: GlobalPermissionService,
	) {
		super(formBuilder, router, route, dialog, tasksExtendedService, pickerDialogService, errorService,
		projectsExtendedService,
		usersExtendedService,
		globalPermissionService,
		);
  }

	ngOnInit() {
		super.ngOnInit();
  }
  
}
