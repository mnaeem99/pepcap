
import { RouterModule, Routes } from '@angular/router';
import { ModuleWithProviders } from "@angular/core";
// import { CanDeactivateGuard } from 'src/app/core/guards/can-deactivate.guard';
// import { AuthGuard } from 'src/app/core/guards/auth.guard';

// import { CategoriesDetailsComponent, CategoriesListComponent, CategoriesNewComponent } from './';

const routes: Routes = [
	// { path: '', component: CategoriesListComponent, canDeactivate: [CanDeactivateGuard], canActivate: [ AuthGuard ] },
	// { path: ':id', component: CategoriesDetailsComponent, canDeactivate: [CanDeactivateGuard], canActivate: [ AuthGuard ] },
	// { path: 'new', component: CategoriesNewComponent, canActivate: [ AuthGuard ] },
];

export const categoriesRoute: ModuleWithProviders<any> = RouterModule.forChild(routes);