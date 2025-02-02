import { NgModule } from '@angular/core';

import { TasksDetailsComponent, TasksListComponent, TasksNewComponent} from './';
import { tasksRoute } from './tasks.route';

import { SharedModule } from 'src/app/common/shared';
import { GeneralComponentsModule } from 'src/app/common/general-components/general.module';

const entities = [
    TasksDetailsComponent, TasksListComponent,TasksNewComponent
  ]
@NgModule({
	declarations: entities,
	exports: entities,
  imports: [
    tasksRoute,
    SharedModule,
    GeneralComponentsModule,
  ]
})
export class TasksModule {
}
