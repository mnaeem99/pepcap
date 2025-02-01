
import { RouterModule, Routes } from '@angular/router';
import { ModuleWithProviders } from "@angular/core";
import { CanDeactivateGuard } from 'src/app/core/guards/can-deactivate.guard';
import { SuppliersDetailsExtendedComponent, SuppliersListExtendedComponent , SuppliersNewExtendedComponent } from './';

const routes: Routes = [
	{ path: '', component: SuppliersListExtendedComponent, canDeactivate: [CanDeactivateGuard] },
	{ path: ':id', component: SuppliersDetailsExtendedComponent, canDeactivate: [CanDeactivateGuard] },
	{ path: 'new', component: SuppliersNewExtendedComponent },
];

export const suppliersRoute: ModuleWithProviders<any> = RouterModule.forChild(routes);