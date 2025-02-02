import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';

import { TestingModule, EntryComponents } from 'src/testing/utils';
import { ProjectsExtendedService, ProjectsNewExtendedComponent } from '../';
import { IProjects } from 'src/app/entities/projects';
import { NewComponent, FieldsComp } from 'src/app/common/general-components';

describe('ProjectsNewExtendedComponent', () => {
  let component: ProjectsNewExtendedComponent;
  let fixture: ComponentFixture<ProjectsNewExtendedComponent>;
  
  let el: HTMLElement;

  describe('Unit tests', () => {
    beforeEach(async(() => {
      TestBed.configureTestingModule({
        declarations: [
          ProjectsNewExtendedComponent,
            NewComponent,
            FieldsComp
        ],
        imports: [TestingModule],
        providers: [
          ProjectsExtendedService,
          { provide: MAT_DIALOG_DATA, useValue: {} }
        ],
        schemas: [NO_ERRORS_SCHEMA] 
      }).compileComponents();
    }));

    beforeEach(() => {
      fixture = TestBed.createComponent(ProjectsNewExtendedComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });

    
  })

  describe('Integration tests', () => {

    describe('', () => {
      beforeEach(async(() => {
        TestBed.configureTestingModule({
          declarations: [
            ProjectsNewExtendedComponent,
            NewComponent,
            FieldsComp
          ].concat(EntryComponents),
          imports: [TestingModule],
          providers: [
            ProjectsExtendedService,
            { provide: MAT_DIALOG_DATA, useValue: {} }
          ]
        });
      }));
  
      beforeEach(() => {
        fixture = TestBed.createComponent(ProjectsNewExtendedComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
      });

    })

  })
  
});