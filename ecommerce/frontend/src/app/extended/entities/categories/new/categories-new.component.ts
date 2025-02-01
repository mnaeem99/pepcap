import { Component, OnInit, Inject } from '@angular/core';
import { CategoriesExtendedService } from '../categories.service';

import { ActivatedRoute,Router } from "@angular/router";
import { FormBuilder } from '@angular/forms';
import { PickerDialogService } from 'src/app/common/shared';
import { ErrorService } from 'src/app/core/services/error.service';
import { MatDialogRef, MatDialog, MAT_DIALOG_DATA } from '@angular/material/dialog';

import { GlobalPermissionService } from 'src/app/core/services/global-permission.service';

import { CategoriesNewComponent } from 'src/app/entities/categories/index';

@Component({
  selector: 'app-categories-new',
  templateUrl: './categories-new.component.html',
  styleUrls: ['./categories-new.component.scss']
})
export class CategoriesNewExtendedComponent extends CategoriesNewComponent implements OnInit {
  
    title:string = "New Categories";
	constructor(
		public formBuilder: FormBuilder,
		public router: Router,
		public route: ActivatedRoute,
		public dialog: MatDialog,
		public dialogRef: MatDialogRef<CategoriesNewComponent>,
		@Inject(MAT_DIALOG_DATA) public data: any,
		public pickerDialogService: PickerDialogService,
		public categoriesExtendedService: CategoriesExtendedService,
		public errorService: ErrorService,
		public globalPermissionService: GlobalPermissionService,
	) {
		super(formBuilder, router, route, dialog, dialogRef, data, pickerDialogService, categoriesExtendedService, errorService,
		globalPermissionService,
		);
	}
 
	ngOnInit() {
		super.ngOnInit();
  }
     
}
