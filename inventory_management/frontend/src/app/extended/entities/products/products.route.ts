
import { RouterModule, Routes } from '@angular/router';
import { ModuleWithProviders } from "@angular/core";
import { CanDeactivateGuard } from 'src/app/core/guards/can-deactivate.guard';
import { ProductsDetailsExtendedComponent, ProductsListExtendedComponent , ProductsNewExtendedComponent } from './';

const routes: Routes = [
	{ path: '', component: ProductsListExtendedComponent, canDeactivate: [CanDeactivateGuard] },
	{ path: ':id', component: ProductsDetailsExtendedComponent, canDeactivate: [CanDeactivateGuard] },
	{ path: 'new', component: ProductsNewExtendedComponent },
];

export const productsRoute: ModuleWithProviders<any> = RouterModule.forChild(routes);