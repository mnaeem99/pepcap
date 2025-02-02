import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from "@angular/router";
import { FormBuilder } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';

import { TimesheetsExtendedService } from '../timesheets.service';

import { PickerDialogService } from 'src/app/common/shared';
import { ErrorService } from 'src/app/core/services/error.service';

import { TasksExtendedService } from 'src/app/extended/entities/tasks/tasks.service';
import { UsersExtendedService } from 'src/app/extended/admin/user-management/users/users.service';

import { GlobalPermissionService } from 'src/app/core/services/global-permission.service';
import { TimesheetsDetailsComponent } from 'src/app/entities/timesheets/index';

@Component({
  selector: 'app-timesheets-details',
  templateUrl: './timesheets-details.component.html',
  styleUrls: ['./timesheets-details.component.scss']
})
export class TimesheetsDetailsExtendedComponent extends TimesheetsDetailsComponent implements OnInit {
	title:string='Timesheets';
	parentUrl:string='timesheets';
	//roles: IRole[];  
	constructor(
		public formBuilder: FormBuilder,
		public router: Router,
		public route: ActivatedRoute,
		public dialog: MatDialog,
		public timesheetsExtendedService: TimesheetsExtendedService,
		public pickerDialogService: PickerDialogService,
		public errorService: ErrorService,
		public tasksExtendedService: TasksExtendedService,
		public usersExtendedService: UsersExtendedService,
		public globalPermissionService: GlobalPermissionService,
	) {
		super(formBuilder, router, route, dialog, timesheetsExtendedService, pickerDialogService, errorService,
		tasksExtendedService,
		usersExtendedService,
		globalPermissionService,
		);
  }

	ngOnInit() {
		super.ngOnInit();
  }
  
}
