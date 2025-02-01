import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from "@angular/router";
import { FormBuilder } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';

import { CategoriesExtendedService } from '../categories.service';

import { PickerDialogService } from 'src/app/common/shared';
import { ErrorService } from 'src/app/core/services/error.service';


import { CategoriesDetailsComponent } from 'src/app/entities/categories/index';

@Component({
  selector: 'app-categories-details',
  templateUrl: './categories-details.component.html',
  styleUrls: ['./categories-details.component.scss']
})
export class CategoriesDetailsExtendedComponent extends CategoriesDetailsComponent implements OnInit {
	title:string='Categories';
	parentUrl:string='categories';
	//roles: IRole[];  
	constructor(
		public formBuilder: FormBuilder,
		public router: Router,
		public route: ActivatedRoute,
		public dialog: MatDialog,
		public categoriesExtendedService: CategoriesExtendedService,
		public pickerDialogService: PickerDialogService,
		public errorService: ErrorService,
	) {
		super(formBuilder, router, route, dialog, categoriesExtendedService, pickerDialogService, errorService,
);
  }

	ngOnInit() {
		super.ngOnInit();
  }
  
}
