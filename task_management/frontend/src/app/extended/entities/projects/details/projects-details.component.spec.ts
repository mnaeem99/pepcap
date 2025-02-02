import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { RouterTestingModule } from '@angular/router/testing';
import { DetailsComponent, ListComponent, FieldsComp } from 'src/app/common/general-components';

import { TestingModule, EntryComponents } from 'src/testing/utils';
import { ProjectsExtendedService, ProjectsDetailsExtendedComponent, ProjectsListExtendedComponent } from '../';
import { IProjects } from 'src/app/entities/projects';
describe('ProjectsDetailsExtendedComponent', () => {
  let component: ProjectsDetailsExtendedComponent;
  let fixture: ComponentFixture<ProjectsDetailsExtendedComponent>;
  let el: HTMLElement;
  
  describe('Unit Tests', () => {
    beforeEach(async(() => {
      TestBed.configureTestingModule({
        declarations: [
          ProjectsDetailsExtendedComponent,
          DetailsComponent
        ],
        imports: [TestingModule],
        providers: [
          ProjectsExtendedService,
        ],
        schemas: [NO_ERRORS_SCHEMA]  
      }).compileComponents();
    }));
  
    beforeEach(() => {
      fixture = TestBed.createComponent(ProjectsDetailsExtendedComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });

  });
  
  describe('Integration Tests', () => {
    beforeEach(async(() => {

      TestBed.configureTestingModule({
        declarations: [
          ProjectsDetailsExtendedComponent,
          ProjectsListExtendedComponent,
          DetailsComponent,
          ListComponent,
          FieldsComp
        ].concat(EntryComponents),
        imports: [
          TestingModule,
          RouterTestingModule.withRoutes([
            { path: 'projects', component: ProjectsDetailsExtendedComponent },
            { path: 'projects/:id', component: ProjectsListExtendedComponent }
          ])
        ],
        providers: [
          ProjectsExtendedService
        ]

      }).compileComponents();
    }));

    beforeEach(() => {
      fixture = TestBed.createComponent(ProjectsDetailsExtendedComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });

  });
  
});
