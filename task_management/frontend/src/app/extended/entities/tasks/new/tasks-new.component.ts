import { Component, OnInit, Inject } from '@angular/core';
import { TasksExtendedService } from '../tasks.service';

import { ActivatedRoute,Router } from "@angular/router";
import { FormBuilder } from '@angular/forms';
import { PickerDialogService } from 'src/app/common/shared';
import { ErrorService } from 'src/app/core/services/error.service';
import { MatDialogRef, MatDialog, MAT_DIALOG_DATA } from '@angular/material/dialog';

import { ProjectsExtendedService } from 'src/app/extended/entities/projects/projects.service';
import { UsersExtendedService } from 'src/app/extended/admin/user-management/users/users.service';
import { GlobalPermissionService } from 'src/app/core/services/global-permission.service';

import { TasksNewComponent } from 'src/app/entities/tasks/index';

@Component({
  selector: 'app-tasks-new',
  templateUrl: './tasks-new.component.html',
  styleUrls: ['./tasks-new.component.scss']
})
export class TasksNewExtendedComponent extends TasksNewComponent implements OnInit {
  
    title:string = "New Tasks";
	constructor(
		public formBuilder: FormBuilder,
		public router: Router,
		public route: ActivatedRoute,
		public dialog: MatDialog,
		public dialogRef: MatDialogRef<TasksNewComponent>,
		@Inject(MAT_DIALOG_DATA) public data: any,
		public pickerDialogService: PickerDialogService,
		public tasksExtendedService: TasksExtendedService,
		public errorService: ErrorService,
		public projectsExtendedService: ProjectsExtendedService,
		public usersExtendedService: UsersExtendedService,
		public globalPermissionService: GlobalPermissionService,
	) {
		super(formBuilder, router, route, dialog, dialogRef, data, pickerDialogService, tasksExtendedService, errorService,
		projectsExtendedService,
		usersExtendedService,
		globalPermissionService,
		);
	}
 
	ngOnInit() {
		super.ngOnInit();
  }
     
}
