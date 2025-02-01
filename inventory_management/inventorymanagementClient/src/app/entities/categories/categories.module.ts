import { NgModule } from '@angular/core';

import { CategoriesDetailsComponent, CategoriesListComponent, CategoriesNewComponent} from './';
import { categoriesRoute } from './categories.route';

import { SharedModule } from 'src/app/common/shared';
import { GeneralComponentsModule } from 'src/app/common/general-components/general.module';

const entities = [
    CategoriesDetailsComponent, CategoriesListComponent,CategoriesNewComponent
  ]
@NgModule({
	declarations: entities,
	exports: entities,
  imports: [
    categoriesRoute,
    SharedModule,
    GeneralComponentsModule,
  ]
})
export class CategoriesModule {
}
