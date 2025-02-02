
import { RouterModule, Routes } from '@angular/router';
import { ModuleWithProviders } from "@angular/core";
import { CanDeactivateGuard } from 'src/app/core/guards/can-deactivate.guard';
import { AuthGuard } from 'src/app/core/guards/auth.guard';
import { ProjectsDetailsExtendedComponent, ProjectsListExtendedComponent , ProjectsNewExtendedComponent } from './';

const routes: Routes = [
	{ path: '', component: ProjectsListExtendedComponent, canDeactivate: [CanDeactivateGuard], canActivate: [ AuthGuard ] },
	{ path: ':id', component: ProjectsDetailsExtendedComponent, canDeactivate: [CanDeactivateGuard], canActivate: [ AuthGuard ] },
	{ path: 'new', component: ProjectsNewExtendedComponent, canActivate: [ AuthGuard ] },
];

export const projectsRoute: ModuleWithProviders<any> = RouterModule.forChild(routes);