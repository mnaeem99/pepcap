import { NgModule } from '@angular/core';

import { ProjectsDetailsComponent, ProjectsListComponent, ProjectsNewComponent} from './';
import { projectsRoute } from './projects.route';

import { SharedModule } from 'src/app/common/shared';
import { GeneralComponentsModule } from 'src/app/common/general-components/general.module';

const entities = [
    ProjectsDetailsComponent, ProjectsListComponent,ProjectsNewComponent
  ]
@NgModule({
	declarations: entities,
	exports: entities,
  imports: [
    projectsRoute,
    SharedModule,
    GeneralComponentsModule,
  ]
})
export class ProjectsModule {
}
