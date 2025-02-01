
import { RouterModule, Routes } from '@angular/router';
import { ModuleWithProviders } from "@angular/core";
// import { CanDeactivateGuard } from 'src/app/core/guards/can-deactivate.guard';

// import { CategoriesDetailsComponent, CategoriesListComponent, CategoriesNewComponent } from './';

const routes: Routes = [
	// { path: '', component: CategoriesListComponent, canDeactivate: [CanDeactivateGuard] },
	// { path: ':id', component: CategoriesDetailsComponent, canDeactivate: [CanDeactivateGuard] },
	// { path: 'new', component: CategoriesNewComponent },
];

export const categoriesRoute: ModuleWithProviders<any> = RouterModule.forChild(routes);