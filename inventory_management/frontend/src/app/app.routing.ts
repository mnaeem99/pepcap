
import { RouterModule, Routes } from '@angular/router';
import { ModuleWithProviders } from "@angular/core";
import { SwaggerComponent } from './core/swagger/swagger.component';
import { ErrorPageComponent  } from './core/error-page/error-page.component';

const routes: Routes = [
	{
		path: '',
		loadChildren: () => import('./extended/core/core.module').then(m => m.CoreExtendedModule),
	},
  	{ path: "swagger-ui", component: SwaggerComponent },
	{
		path: 'inventorytransactions',
		loadChildren: () => import('./extended/entities/inventory-transactions/inventory-transactions.module').then(m => m.InventoryTransactionsExtendedModule),

	},
	{
		path: 'suppliers',
		loadChildren: () => import('./extended/entities/suppliers/suppliers.module').then(m => m.SuppliersExtendedModule),

	},
	{
		path: 'categories',
		loadChildren: () => import('./extended/entities/categories/categories.module').then(m => m.CategoriesExtendedModule),

	},
	{
		path: 'products',
		loadChildren: () => import('./extended/entities/products/products.module').then(m => m.ProductsExtendedModule),

	},
	{ path: '**', component:ErrorPageComponent},
	
];

export const routingModule: ModuleWithProviders<any> = RouterModule.forRoot(routes);