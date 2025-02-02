import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { ChangeDetectorRef, NO_ERRORS_SCHEMA } from '@angular/core';
import { RouterTestingModule } from '@angular/router/testing';

import { EntryComponents, TestingModule } from 'src/testing/utils';
import { TasksExtendedService, TasksDetailsExtendedComponent, TasksListExtendedComponent, TasksNewExtendedComponent } from '../';
import { ITasks } from 'src/app/entities/tasks';
import { ListFiltersComponent, ServiceUtils } from 'src/app/common/shared';
import { ListComponent, DetailsComponent, NewComponent, FieldsComp } from 'src/app/common/general-components';

describe('TasksListExtendedComponent', () => {
  let fixture: ComponentFixture<TasksListExtendedComponent>;
  let component: TasksListExtendedComponent;
  let el: HTMLElement;

  describe('Unit tests', () => {
  
    beforeEach(async(() => {
      
      TestBed.configureTestingModule({
        declarations: [
          TasksListExtendedComponent,
          ListComponent
        ],
        imports: [TestingModule],
        providers: [
          TasksExtendedService,      
          ChangeDetectorRef,
        ],
        schemas: [NO_ERRORS_SCHEMA]   
      }).compileComponents();

    }));
    
    beforeEach(() => {
      fixture = TestBed.createComponent(TasksListExtendedComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });
  
  });
  
  describe('Integration tests', () => {

    beforeEach(async(() => {

      TestBed.configureTestingModule({
        declarations: [
          TasksListExtendedComponent,
          TasksNewExtendedComponent,
          NewComponent,
          TasksDetailsExtendedComponent,
          ListComponent,
          DetailsComponent,
          FieldsComp
        ].concat(EntryComponents),
        imports: [
          TestingModule,
          RouterTestingModule.withRoutes([
            { path: 'tasks', component: TasksListExtendedComponent },
            { path: 'tasks/:id', component: TasksDetailsExtendedComponent }
          ])
        ],
        providers: [
          TasksExtendedService,
          ChangeDetectorRef,
        ]

      }).compileComponents();

    }));

    beforeEach(() => {
      fixture = TestBed.createComponent(TasksListExtendedComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });

    

  });
        
});
