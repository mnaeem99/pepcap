import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from "@angular/router";
import { FormBuilder } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';

import { ProjectsExtendedService } from '../projects.service';

import { PickerDialogService } from 'src/app/common/shared';
import { ErrorService } from 'src/app/core/services/error.service';


import { GlobalPermissionService } from 'src/app/core/services/global-permission.service';
import { ProjectsDetailsComponent } from 'src/app/entities/projects/index';

@Component({
  selector: 'app-projects-details',
  templateUrl: './projects-details.component.html',
  styleUrls: ['./projects-details.component.scss']
})
export class ProjectsDetailsExtendedComponent extends ProjectsDetailsComponent implements OnInit {
	title:string='Projects';
	parentUrl:string='projects';
	//roles: IRole[];  
	constructor(
		public formBuilder: FormBuilder,
		public router: Router,
		public route: ActivatedRoute,
		public dialog: MatDialog,
		public projectsExtendedService: ProjectsExtendedService,
		public pickerDialogService: PickerDialogService,
		public errorService: ErrorService,
		public globalPermissionService: GlobalPermissionService,
	) {
		super(formBuilder, router, route, dialog, projectsExtendedService, pickerDialogService, errorService,
		globalPermissionService,
		);
  }

	ngOnInit() {
		super.ngOnInit();
  }
  
}
