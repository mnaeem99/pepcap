
import { RouterModule, Routes } from '@angular/router';
import { ModuleWithProviders } from "@angular/core";
import { CanDeactivateGuard } from 'src/app/core/guards/can-deactivate.guard';
import { InventoryTransactionsDetailsExtendedComponent, InventoryTransactionsListExtendedComponent , InventoryTransactionsNewExtendedComponent } from './';

const routes: Routes = [
	{ path: '', component: InventoryTransactionsListExtendedComponent, canDeactivate: [CanDeactivateGuard] },
	{ path: ':id', component: InventoryTransactionsDetailsExtendedComponent, canDeactivate: [CanDeactivateGuard] },
	{ path: 'new', component: InventoryTransactionsNewExtendedComponent },
];

export const inventoryTransactionsRoute: ModuleWithProviders<any> = RouterModule.forChild(routes);