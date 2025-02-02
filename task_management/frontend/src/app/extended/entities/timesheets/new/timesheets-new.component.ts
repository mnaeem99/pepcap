import { Component, OnInit, Inject } from '@angular/core';
import { TimesheetsExtendedService } from '../timesheets.service';

import { ActivatedRoute,Router } from "@angular/router";
import { FormBuilder } from '@angular/forms';
import { PickerDialogService } from 'src/app/common/shared';
import { ErrorService } from 'src/app/core/services/error.service';
import { MatDialogRef, MatDialog, MAT_DIALOG_DATA } from '@angular/material/dialog';

import { TasksExtendedService } from 'src/app/extended/entities/tasks/tasks.service';
import { UsersExtendedService } from 'src/app/extended/admin/user-management/users/users.service';
import { GlobalPermissionService } from 'src/app/core/services/global-permission.service';

import { TimesheetsNewComponent } from 'src/app/entities/timesheets/index';

@Component({
  selector: 'app-timesheets-new',
  templateUrl: './timesheets-new.component.html',
  styleUrls: ['./timesheets-new.component.scss']
})
export class TimesheetsNewExtendedComponent extends TimesheetsNewComponent implements OnInit {
  
    title:string = "New Timesheets";
	constructor(
		public formBuilder: FormBuilder,
		public router: Router,
		public route: ActivatedRoute,
		public dialog: MatDialog,
		public dialogRef: MatDialogRef<TimesheetsNewComponent>,
		@Inject(MAT_DIALOG_DATA) public data: any,
		public pickerDialogService: PickerDialogService,
		public timesheetsExtendedService: TimesheetsExtendedService,
		public errorService: ErrorService,
		public tasksExtendedService: TasksExtendedService,
		public usersExtendedService: UsersExtendedService,
		public globalPermissionService: GlobalPermissionService,
	) {
		super(formBuilder, router, route, dialog, dialogRef, data, pickerDialogService, timesheetsExtendedService, errorService,
		tasksExtendedService,
		usersExtendedService,
		globalPermissionService,
		);
	}
 
	ngOnInit() {
		super.ngOnInit();
  }
     
}
