import { NgModule } from '@angular/core';

import { TasksExtendedService, TasksDetailsExtendedComponent, TasksListExtendedComponent, TasksNewExtendedComponent } from './';
import { TasksService } from 'src/app/entities/tasks';
import { TasksModule } from 'src/app/entities/tasks/tasks.module';
import { tasksRoute } from './tasks.route';

import { SharedModule  } from 'src/app/common/shared';
import { GeneralComponentsExtendedModule } from 'src/app/extended/common/general-components/general-extended.module';

const entities = [
    TasksDetailsExtendedComponent, TasksListExtendedComponent, TasksNewExtendedComponent 
  ]
@NgModule({
	declarations: entities,
	exports: entities,
  imports: [
    tasksRoute,
    TasksModule,
    SharedModule,
    GeneralComponentsExtendedModule,
  ],
  providers: [{ provide: TasksService, useClass: TasksExtendedService }],
})
export class TasksExtendedModule {
}
