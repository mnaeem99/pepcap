
import { RouterModule, Routes } from '@angular/router';
import { ModuleWithProviders } from "@angular/core";
import { CanDeactivateGuard } from 'src/app/core/guards/can-deactivate.guard';
import { AuthGuard } from 'src/app/core/guards/auth.guard';
import { TimesheetsDetailsExtendedComponent, TimesheetsListExtendedComponent , TimesheetsNewExtendedComponent } from './';

const routes: Routes = [
	{ path: '', component: TimesheetsListExtendedComponent, canDeactivate: [CanDeactivateGuard], canActivate: [ AuthGuard ] },
	{ path: ':id', component: TimesheetsDetailsExtendedComponent, canDeactivate: [CanDeactivateGuard], canActivate: [ AuthGuard ] },
	{ path: 'new', component: TimesheetsNewExtendedComponent, canActivate: [ AuthGuard ] },
];

export const timesheetsRoute: ModuleWithProviders<any> = RouterModule.forChild(routes);