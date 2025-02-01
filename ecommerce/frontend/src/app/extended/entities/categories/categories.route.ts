
import { RouterModule, Routes } from '@angular/router';
import { ModuleWithProviders } from "@angular/core";
import { CanDeactivateGuard } from 'src/app/core/guards/can-deactivate.guard';
import { AuthGuard } from 'src/app/core/guards/auth.guard';
import { CategoriesDetailsExtendedComponent, CategoriesListExtendedComponent , CategoriesNewExtendedComponent } from './';

const routes: Routes = [
	{ path: '', component: CategoriesListExtendedComponent, canDeactivate: [CanDeactivateGuard], canActivate: [ AuthGuard ] },
	{ path: ':id', component: CategoriesDetailsExtendedComponent, canDeactivate: [CanDeactivateGuard], canActivate: [ AuthGuard ] },
	{ path: 'new', component: CategoriesNewExtendedComponent, canActivate: [ AuthGuard ] },
];

export const categoriesRoute: ModuleWithProviders<any> = RouterModule.forChild(routes);