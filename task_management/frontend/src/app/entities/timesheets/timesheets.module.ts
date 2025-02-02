import { NgModule } from '@angular/core';

import { TimesheetsDetailsComponent, TimesheetsListComponent, TimesheetsNewComponent} from './';
import { timesheetsRoute } from './timesheets.route';

import { SharedModule } from 'src/app/common/shared';
import { GeneralComponentsModule } from 'src/app/common/general-components/general.module';

const entities = [
    TimesheetsDetailsComponent, TimesheetsListComponent,TimesheetsNewComponent
  ]
@NgModule({
	declarations: entities,
	exports: entities,
  imports: [
    timesheetsRoute,
    SharedModule,
    GeneralComponentsModule,
  ]
})
export class TimesheetsModule {
}
