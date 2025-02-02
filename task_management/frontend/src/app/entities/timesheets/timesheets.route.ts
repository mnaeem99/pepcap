
import { RouterModule, Routes } from '@angular/router';
import { ModuleWithProviders } from "@angular/core";
// import { CanDeactivateGuard } from 'src/app/core/guards/can-deactivate.guard';
// import { AuthGuard } from 'src/app/core/guards/auth.guard';

// import { TimesheetsDetailsComponent, TimesheetsListComponent, TimesheetsNewComponent } from './';

const routes: Routes = [
	// { path: '', component: TimesheetsListComponent, canDeactivate: [CanDeactivateGuard], canActivate: [ AuthGuard ] },
	// { path: ':id', component: TimesheetsDetailsComponent, canDeactivate: [CanDeactivateGuard], canActivate: [ AuthGuard ] },
	// { path: 'new', component: TimesheetsNewComponent, canActivate: [ AuthGuard ] },
];

export const timesheetsRoute: ModuleWithProviders<any> = RouterModule.forChild(routes);