
import { RouterModule, Routes } from '@angular/router';
import { ModuleWithProviders } from "@angular/core";
import { CanDeactivateGuard } from 'src/app/core/guards/can-deactivate.guard';
import { CategoriesDetailsExtendedComponent, CategoriesListExtendedComponent , CategoriesNewExtendedComponent } from './';

const routes: Routes = [
	{ path: '', component: CategoriesListExtendedComponent, canDeactivate: [CanDeactivateGuard] },
	{ path: ':id', component: CategoriesDetailsExtendedComponent, canDeactivate: [CanDeactivateGuard] },
	{ path: 'new', component: CategoriesNewExtendedComponent },
];

export const categoriesRoute: ModuleWithProviders<any> = RouterModule.forChild(routes);