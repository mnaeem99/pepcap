import { NgModule } from '@angular/core';

import { TimesheetsExtendedService, TimesheetsDetailsExtendedComponent, TimesheetsListExtendedComponent, TimesheetsNewExtendedComponent } from './';
import { TimesheetsService } from 'src/app/entities/timesheets';
import { TimesheetsModule } from 'src/app/entities/timesheets/timesheets.module';
import { timesheetsRoute } from './timesheets.route';

import { SharedModule  } from 'src/app/common/shared';
import { GeneralComponentsExtendedModule } from 'src/app/extended/common/general-components/general-extended.module';

const entities = [
    TimesheetsDetailsExtendedComponent, TimesheetsListExtendedComponent, TimesheetsNewExtendedComponent 
  ]
@NgModule({
	declarations: entities,
	exports: entities,
  imports: [
    timesheetsRoute,
    TimesheetsModule,
    SharedModule,
    GeneralComponentsExtendedModule,
  ],
  providers: [{ provide: TimesheetsService, useClass: TimesheetsExtendedService }],
})
export class TimesheetsExtendedModule {
}
