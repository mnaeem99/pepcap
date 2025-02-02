
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
		path: 'projects',
		loadChildren: () => import('./extended/entities/projects/projects.module').then(m => m.ProjectsExtendedModule),
		canActivate: [AuthGuard]
	},
	{
		path: 'timesheets',
		loadChildren: () => import('./extended/entities/timesheets/timesheets.module').then(m => m.TimesheetsExtendedModule),
		canActivate: [AuthGuard]
	},
	{
		path: 'tasks',
		loadChildren: () => import('./extended/entities/tasks/tasks.module').then(m => m.TasksExtendedModule),
		canActivate: [AuthGuard]
	},
	{ path: '**', component:ErrorPageComponent},
	
];

export const routingModule: ModuleWithProviders<any> = RouterModule.forRoot(routes);