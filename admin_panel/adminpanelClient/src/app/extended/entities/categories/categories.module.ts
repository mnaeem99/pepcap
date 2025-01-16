import { NgModule } from '@angular/core';

import { CategoriesExtendedService, CategoriesDetailsExtendedComponent, CategoriesListExtendedComponent, CategoriesNewExtendedComponent } from './';
import { CategoriesService } from 'src/app/entities/categories';
import { CategoriesModule } from 'src/app/entities/categories/categories.module';
import { categoriesRoute } from './categories.route';

import { SharedModule  } from 'src/app/common/shared';
import { GeneralComponentsExtendedModule } from 'src/app/extended/common/general-components/general-extended.module';

const entities = [
    CategoriesDetailsExtendedComponent, CategoriesListExtendedComponent, CategoriesNewExtendedComponent 
  ]
@NgModule({
	declarations: entities,
	exports: entities,
  imports: [
    categoriesRoute,
    CategoriesModule,
    SharedModule,
    GeneralComponentsExtendedModule,
  ],
  providers: [{ provide: CategoriesService, useClass: CategoriesExtendedService }],
})
export class CategoriesExtendedModule {
}
