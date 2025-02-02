import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { ChangeDetectorRef, NO_ERRORS_SCHEMA } from '@angular/core';
import { RouterTestingModule } from '@angular/router/testing';

import { EntryComponents, TestingModule } from 'src/testing/utils';
import { ProjectsExtendedService, ProjectsDetailsExtendedComponent, ProjectsListExtendedComponent, ProjectsNewExtendedComponent } from '../';
import { IProjects } from 'src/app/entities/projects';
import { ListFiltersComponent, ServiceUtils } from 'src/app/common/shared';
import { ListComponent, DetailsComponent, NewComponent, FieldsComp } from 'src/app/common/general-components';

describe('ProjectsListExtendedComponent', () => {
  let fixture: ComponentFixture<ProjectsListExtendedComponent>;
  let component: ProjectsListExtendedComponent;
  let el: HTMLElement;

  describe('Unit tests', () => {
  
    beforeEach(async(() => {
      
      TestBed.configureTestingModule({
        declarations: [
          ProjectsListExtendedComponent,
          ListComponent
        ],
        imports: [TestingModule],
        providers: [
          ProjectsExtendedService,      
          ChangeDetectorRef,
        ],
        schemas: [NO_ERRORS_SCHEMA]   
      }).compileComponents();

    }));
    
    beforeEach(() => {
      fixture = TestBed.createComponent(ProjectsListExtendedComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });
  
  });
  
  describe('Integration tests', () => {

    beforeEach(async(() => {

      TestBed.configureTestingModule({
        declarations: [
          ProjectsListExtendedComponent,
          ProjectsNewExtendedComponent,
          NewComponent,
          ProjectsDetailsExtendedComponent,
          ListComponent,
          DetailsComponent,
          FieldsComp
        ].concat(EntryComponents),
        imports: [
          TestingModule,
          RouterTestingModule.withRoutes([
            { path: 'projects', component: ProjectsListExtendedComponent },
            { path: 'projects/:id', component: ProjectsDetailsExtendedComponent }
          ])
        ],
        providers: [
          ProjectsExtendedService,
          ChangeDetectorRef,
        ]

      }).compileComponents();

    }));

    beforeEach(() => {
      fixture = TestBed.createComponent(ProjectsListExtendedComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });

    

  });
        
});
