import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';

import { TestingModule, EntryComponents } from 'src/testing/utils';
import { TasksExtendedService, TasksNewExtendedComponent } from '../';
import { ITasks } from 'src/app/entities/tasks';
import { NewComponent, FieldsComp } from 'src/app/common/general-components';

describe('TasksNewExtendedComponent', () => {
  let component: TasksNewExtendedComponent;
  let fixture: ComponentFixture<TasksNewExtendedComponent>;
  
  let el: HTMLElement;

  describe('Unit tests', () => {
    beforeEach(async(() => {
      TestBed.configureTestingModule({
        declarations: [
          TasksNewExtendedComponent,
            NewComponent,
            FieldsComp
        ],
        imports: [TestingModule],
        providers: [
          TasksExtendedService,
          { provide: MAT_DIALOG_DATA, useValue: {} }
        ],
        schemas: [NO_ERRORS_SCHEMA] 
      }).compileComponents();
    }));

    beforeEach(() => {
      fixture = TestBed.createComponent(TasksNewExtendedComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });

    
  })

  describe('Integration tests', () => {

    describe('', () => {
      beforeEach(async(() => {
        TestBed.configureTestingModule({
          declarations: [
            TasksNewExtendedComponent,
            NewComponent,
            FieldsComp
          ].concat(EntryComponents),
          imports: [TestingModule],
          providers: [
            TasksExtendedService,
            { provide: MAT_DIALOG_DATA, useValue: {} }
          ]
        });
      }));
  
      beforeEach(() => {
        fixture = TestBed.createComponent(TasksNewExtendedComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
      });

    })

  })
  
});