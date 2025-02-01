
import { RouterModule, Routes } from '@angular/router';
import { ModuleWithProviders } from "@angular/core";
// import { CanDeactivateGuard } from 'src/app/core/guards/can-deactivate.guard';

// import { InventoryTransactionsDetailsComponent, InventoryTransactionsListComponent, InventoryTransactionsNewComponent } from './';

const routes: Routes = [
	// { path: '', component: InventoryTransactionsListComponent, canDeactivate: [CanDeactivateGuard] },
	// { path: ':id', component: InventoryTransactionsDetailsComponent, canDeactivate: [CanDeactivateGuard] },
	// { path: 'new', component: InventoryTransactionsNewComponent },
];

export const inventoryTransactionsRoute: ModuleWithProviders<any> = RouterModule.forChild(routes);