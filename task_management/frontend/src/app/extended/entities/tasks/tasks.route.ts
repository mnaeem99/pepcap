
import { RouterModule, Routes } from '@angular/router';
import { ModuleWithProviders } from "@angular/core";
import { CanDeactivateGuard } from 'src/app/core/guards/can-deactivate.guard';
import { AuthGuard } from 'src/app/core/guards/auth.guard';
import { TasksDetailsExtendedComponent, TasksListExtendedComponent , TasksNewExtendedComponent } from './';

const routes: Routes = [
	{ path: '', component: TasksListExtendedComponent, canDeactivate: [CanDeactivateGuard], canActivate: [ AuthGuard ] },
	{ path: ':id', component: TasksDetailsExtendedComponent, canDeactivate: [CanDeactivateGuard], canActivate: [ AuthGuard ] },
	{ path: 'new', component: TasksNewExtendedComponent, canActivate: [ AuthGuard ] },
];

export const tasksRoute: ModuleWithProviders<any> = RouterModule.forChild(routes);