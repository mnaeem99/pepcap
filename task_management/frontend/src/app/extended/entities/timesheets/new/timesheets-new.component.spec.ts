import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';

import { TestingModule, EntryComponents } from 'src/testing/utils';
import { TimesheetsExtendedService, TimesheetsNewExtendedComponent } from '../';
import { ITimesheets } from 'src/app/entities/timesheets';
import { NewComponent, FieldsComp } from 'src/app/common/general-components';

describe('TimesheetsNewExtendedComponent', () => {
  let component: TimesheetsNewExtendedComponent;
  let fixture: ComponentFixture<TimesheetsNewExtendedComponent>;
  
  let el: HTMLElement;

  describe('Unit tests', () => {
    beforeEach(async(() => {
      TestBed.configureTestingModule({
        declarations: [
          TimesheetsNewExtendedComponent,
            NewComponent,
            FieldsComp
        ],
        imports: [TestingModule],
        providers: [
          TimesheetsExtendedService,
          { provide: MAT_DIALOG_DATA, useValue: {} }
        ],
        schemas: [NO_ERRORS_SCHEMA] 
      }).compileComponents();
    }));

    beforeEach(() => {
      fixture = TestBed.createComponent(TimesheetsNewExtendedComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });

    
  })

  describe('Integration tests', () => {

    describe('', () => {
      beforeEach(async(() => {
        TestBed.configureTestingModule({
          declarations: [
            TimesheetsNewExtendedComponent,
            NewComponent,
            FieldsComp
          ].concat(EntryComponents),
          imports: [TestingModule],
          providers: [
            TimesheetsExtendedService,
            { provide: MAT_DIALOG_DATA, useValue: {} }
          ]
        });
      }));
  
      beforeEach(() => {
        fixture = TestBed.createComponent(TimesheetsNewExtendedComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
      });

    })

  })
  
});