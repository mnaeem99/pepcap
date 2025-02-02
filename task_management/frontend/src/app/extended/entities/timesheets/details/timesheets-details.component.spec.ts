import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { RouterTestingModule } from '@angular/router/testing';
import { DetailsComponent, ListComponent, FieldsComp } from 'src/app/common/general-components';

import { TestingModule, EntryComponents } from 'src/testing/utils';
import { TimesheetsExtendedService, TimesheetsDetailsExtendedComponent, TimesheetsListExtendedComponent } from '../';
import { ITimesheets } from 'src/app/entities/timesheets';
describe('TimesheetsDetailsExtendedComponent', () => {
  let component: TimesheetsDetailsExtendedComponent;
  let fixture: ComponentFixture<TimesheetsDetailsExtendedComponent>;
  let el: HTMLElement;
  
  describe('Unit Tests', () => {
    beforeEach(async(() => {
      TestBed.configureTestingModule({
        declarations: [
          TimesheetsDetailsExtendedComponent,
          DetailsComponent
        ],
        imports: [TestingModule],
        providers: [
          TimesheetsExtendedService,
        ],
        schemas: [NO_ERRORS_SCHEMA]  
      }).compileComponents();
    }));
  
    beforeEach(() => {
      fixture = TestBed.createComponent(TimesheetsDetailsExtendedComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });

  });
  
  describe('Integration Tests', () => {
    beforeEach(async(() => {

      TestBed.configureTestingModule({
        declarations: [
          TimesheetsDetailsExtendedComponent,
          TimesheetsListExtendedComponent,
          DetailsComponent,
          ListComponent,
          FieldsComp
        ].concat(EntryComponents),
        imports: [
          TestingModule,
          RouterTestingModule.withRoutes([
            { path: 'timesheets', component: TimesheetsDetailsExtendedComponent },
            { path: 'timesheets/:id', component: TimesheetsListExtendedComponent }
          ])
        ],
        providers: [
          TimesheetsExtendedService
        ]

      }).compileComponents();
    }));

    beforeEach(() => {
      fixture = TestBed.createComponent(TimesheetsDetailsExtendedComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });

  });
  
});
