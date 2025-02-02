import { NgModule } from '@angular/core';

import { ProjectsExtendedService, ProjectsDetailsExtendedComponent, ProjectsListExtendedComponent, ProjectsNewExtendedComponent } from './';
import { ProjectsService } from 'src/app/entities/projects';
import { ProjectsModule } from 'src/app/entities/projects/projects.module';
import { projectsRoute } from './projects.route';

import { SharedModule  } from 'src/app/common/shared';
import { GeneralComponentsExtendedModule } from 'src/app/extended/common/general-components/general-extended.module';

const entities = [
    ProjectsDetailsExtendedComponent, ProjectsListExtendedComponent, ProjectsNewExtendedComponent 
  ]
@NgModule({
	declarations: entities,
	exports: entities,
  imports: [
    projectsRoute,
    ProjectsModule,
    SharedModule,
    GeneralComponentsExtendedModule,
  ],
  providers: [{ provide: ProjectsService, useClass: ProjectsExtendedService }],
})
export class ProjectsExtendedModule {
}
