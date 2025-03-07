
import { RouterModule, Routes } from '@angular/router';
import { ModuleWithProviders } from '@angular/core';
import { AuthGuard } from 'src/app/core/guards/auth.guard';


const routes: Routes = [
	
	{
		path: 'rolepermission',
		loadChildren: () => import('./user-management/rolepermission/rolepermission.module').then(m => m.RolepermissionExtendedModule),
		canActivate: [AuthGuard]
	},
	{
		path: 'role',
		loadChildren: () => import('./user-management/role/role.module').then(m => m.RoleExtendedModule),
		canActivate: [AuthGuard]
	},
	{
		path: 'permission',
		loadChildren: () => import('./user-management/permission/permission.module').then(m => m.PermissionExtendedModule),
		canActivate: [AuthGuard]
	},
	{
		path: 'usersrole',
		loadChildren: () => import('./user-management/usersrole/usersrole.module').then(m => m.UsersroleExtendedModule),
		canActivate: [AuthGuard]
	},
	{
		path: 'users',
		loadChildren: () => import('./user-management/users/users.module').then(m => m.UsersExtendedModule),
		canActivate: [AuthGuard]
	},
	{
		path: 'userspermission',
		loadChildren: () => import('./user-management/userspermission/userspermission.module').then(m => m.UserspermissionExtendedModule),
		canActivate: [AuthGuard]
	},
	
];

export const routingModule: ModuleWithProviders<any> = RouterModule.forChild(routes);