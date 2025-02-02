import { Component, OnInit, Inject } from '@angular/core';
import { ProjectsExtendedService } from '../projects.service';

import { ActivatedRoute,Router } from "@angular/router";
import { FormBuilder } from '@angular/forms';
import { PickerDialogService } from 'src/app/common/shared';
import { ErrorService } from 'src/app/core/services/error.service';
import { MatDialogRef, MatDialog, MAT_DIALOG_DATA } from '@angular/material/dialog';

import { GlobalPermissionService } from 'src/app/core/services/global-permission.service';

import { ProjectsNewComponent } from 'src/app/entities/projects/index';

@Component({
  selector: 'app-projects-new',
  templateUrl: './projects-new.component.html',
  styleUrls: ['./projects-new.component.scss']
})
export class ProjectsNewExtendedComponent extends ProjectsNewComponent implements OnInit {
  
    title:string = "New Projects";
	constructor(
		public formBuilder: FormBuilder,
		public router: Router,
		public route: ActivatedRoute,
		public dialog: MatDialog,
		public dialogRef: MatDialogRef<ProjectsNewComponent>,
		@Inject(MAT_DIALOG_DATA) public data: any,
		public pickerDialogService: PickerDialogService,
		public projectsExtendedService: ProjectsExtendedService,
		public errorService: ErrorService,
		public globalPermissionService: GlobalPermissionService,
	) {
		super(formBuilder, router, route, dialog, dialogRef, data, pickerDialogService, projectsExtendedService, errorService,
		globalPermissionService,
		);
	}
 
	ngOnInit() {
		super.ngOnInit();
  }
     
}
