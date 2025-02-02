import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { RouterTestingModule } from '@angular/router/testing';
import { DetailsComponent, ListComponent, FieldsComp } from 'src/app/common/general-components';

import { TestingModule, EntryComponents } from 'src/testing/utils';
import { TasksExtendedService, TasksDetailsExtendedComponent, TasksListExtendedComponent } from '../';
import { ITasks } from 'src/app/entities/tasks';
describe('TasksDetailsExtendedComponent', () => {
  let component: TasksDetailsExtendedComponent;
  let fixture: ComponentFixture<TasksDetailsExtendedComponent>;
  let el: HTMLElement;
  
  describe('Unit Tests', () => {
    beforeEach(async(() => {
      TestBed.configureTestingModule({
        declarations: [
          TasksDetailsExtendedComponent,
          DetailsComponent
        ],
        imports: [TestingModule],
        providers: [
          TasksExtendedService,
        ],
        schemas: [NO_ERRORS_SCHEMA]  
      }).compileComponents();
    }));
  
    beforeEach(() => {
      fixture = TestBed.createComponent(TasksDetailsExtendedComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });

  });
  
  describe('Integration Tests', () => {
    beforeEach(async(() => {

      TestBed.configureTestingModule({
        declarations: [
          TasksDetailsExtendedComponent,
          TasksListExtendedComponent,
          DetailsComponent,
          ListComponent,
          FieldsComp
        ].concat(EntryComponents),
        imports: [
          TestingModule,
          RouterTestingModule.withRoutes([
            { path: 'tasks', component: TasksDetailsExtendedComponent },
            { path: 'tasks/:id', component: TasksListExtendedComponent }
          ])
        ],
        providers: [
          TasksExtendedService
        ]

      }).compileComponents();
    }));

    beforeEach(() => {
      fixture = TestBed.createComponent(TasksDetailsExtendedComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });

  });
  
});
