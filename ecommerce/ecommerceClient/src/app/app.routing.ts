
import { RouterModule, Routes } from '@angular/router';
import { ModuleWithProviders } from "@angular/core";
import { SwaggerComponent } from './core/swagger/swagger.component';
import { ErrorPageComponent  } from './core/error-page/error-page.component';
import { AuthGuard } from './core/guards/auth.guard';

const routes: Routes = [
	{
		path: '',
		loadChildren: () => import('./extended/core/core.module').then(m => m.CoreExtendedModule),
	},
  	{ path: "swagger-ui", component: SwaggerComponent , canActivate: [ AuthGuard ] },
	{
		path: '',
		loadChildren: () => import('./extended/admin/admin.module').then(m => m.AdminExtendedModule),
	},
	{
		path: '',
		loadChildren: () => import('./extended/account/account.module').then(m => m.AccountExtendedModule),
	},
	{
		path: 'products',
		loadChildren: () => import('./extended/entities/products/products.module').then(m => m.ProductsExtendedModule),
		canActivate: [AuthGuard]
	},
	{
		path: 'orders',
		loadChildren: () => import('./extended/entities/orders/orders.module').then(m => m.OrdersExtendedModule),
		canActivate: [AuthGuard]
	},
	{
		path: 'categories',
		loadChildren: () => import('./extended/entities/categories/categories.module').then(m => m.CategoriesExtendedModule),
		canActivate: [AuthGuard]
	},
	{
		path: 'orderitems',
		loadChildren: () => import('./extended/entities/order-items/order-items.module').then(m => m.OrderItemsExtendedModule),
		canActivate: [AuthGuard]
	},
	{ path: '**', component:ErrorPageComponent},
	
];

export const routingModule: ModuleWithProviders<any> = RouterModule.forRoot(routes);