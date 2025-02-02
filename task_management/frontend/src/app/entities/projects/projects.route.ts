
import { RouterModule, Routes } from '@angular/router';
import { ModuleWithProviders } from "@angular/core";
// import { CanDeactivateGuard } from 'src/app/core/guards/can-deactivate.guard';
// import { AuthGuard } from 'src/app/core/guards/auth.guard';

// import { ProjectsDetailsComponent, ProjectsListComponent, ProjectsNewComponent } from './';

const routes: Routes = [
	// { path: '', component: ProjectsListComponent, canDeactivate: [CanDeactivateGuard], canActivate: [ AuthGuard ] },
	// { path: ':id', component: ProjectsDetailsComponent, canDeactivate: [CanDeactivateGuard], canActivate: [ AuthGuard ] },
	// { path: 'new', component: ProjectsNewComponent, canActivate: [ AuthGuard ] },
];

export const projectsRoute: ModuleWithProviders<any> = RouterModule.forChild(routes);