import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';

import { Router, ActivatedRoute } from '@angular/router';

import { SuppliersExtendedService } from '../suppliers.service';
import { SuppliersNewExtendedComponent } from '../new/suppliers-new.component';
import { PickerDialogService } from 'src/app/common/shared';
import { ErrorService } from 'src/app/core/services/error.service';
import { Globals } from 'src/app/core/services/globals';

import { SuppliersListComponent } from 'src/app/entities/suppliers/index';

@Component({
  selector: 'app-suppliers-list',
  templateUrl: './suppliers-list.component.html',
  styleUrls: ['./suppliers-list.component.scss']
})
export class SuppliersListExtendedComponent extends SuppliersListComponent implements OnInit {

	title:string = "Suppliers";
	constructor(
		public router: Router,
		public route: ActivatedRoute,
		public global: Globals,
		public dialog: MatDialog,
		public changeDetectorRefs: ChangeDetectorRef,
		public pickerDialogService: PickerDialogService,
		public suppliersService: SuppliersExtendedService,
		public errorService: ErrorService,
	) { 
		super(router, route, global, dialog, changeDetectorRefs, pickerDialogService, suppliersService, errorService,
)
  }

	ngOnInit() {
		super.ngOnInit();
	}
  
	addNew() {
		super.addNew(SuppliersNewExtendedComponent);
	}
  
}
