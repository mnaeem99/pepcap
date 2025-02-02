
import { RouterModule, Routes } from '@angular/router';
import { ModuleWithProviders } from "@angular/core";
// import { CanDeactivateGuard } from 'src/app/core/guards/can-deactivate.guard';
// import { AuthGuard } from 'src/app/core/guards/auth.guard';

// import { TasksDetailsComponent, TasksListComponent, TasksNewComponent } from './';

const routes: Routes = [
	// { path: '', component: TasksListComponent, canDeactivate: [CanDeactivateGuard], canActivate: [ AuthGuard ] },
	// { path: ':id', component: TasksDetailsComponent, canDeactivate: [CanDeactivateGuard], canActivate: [ AuthGuard ] },
	// { path: 'new', component: TasksNewComponent, canActivate: [ AuthGuard ] },
];

export const tasksRoute: ModuleWithProviders<any> = RouterModule.forChild(routes);