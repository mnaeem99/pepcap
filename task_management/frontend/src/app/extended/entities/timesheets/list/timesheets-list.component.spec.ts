import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { ChangeDetectorRef, NO_ERRORS_SCHEMA } from '@angular/core';
import { RouterTestingModule } from '@angular/router/testing';

import { EntryComponents, TestingModule } from 'src/testing/utils';
import { TimesheetsExtendedService, TimesheetsDetailsExtendedComponent, TimesheetsListExtendedComponent, TimesheetsNewExtendedComponent } from '../';
import { ITimesheets } from 'src/app/entities/timesheets';
import { ListFiltersComponent, ServiceUtils } from 'src/app/common/shared';
import { ListComponent, DetailsComponent, NewComponent, FieldsComp } from 'src/app/common/general-components';

describe('TimesheetsListExtendedComponent', () => {
  let fixture: ComponentFixture<TimesheetsListExtendedComponent>;
  let component: TimesheetsListExtendedComponent;
  let el: HTMLElement;

  describe('Unit tests', () => {
  
    beforeEach(async(() => {
      
      TestBed.configureTestingModule({
        declarations: [
          TimesheetsListExtendedComponent,
          ListComponent
        ],
        imports: [TestingModule],
        providers: [
          TimesheetsExtendedService,      
          ChangeDetectorRef,
        ],
        schemas: [NO_ERRORS_SCHEMA]   
      }).compileComponents();

    }));
    
    beforeEach(() => {
      fixture = TestBed.createComponent(TimesheetsListExtendedComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });
  
  });
  
  describe('Integration tests', () => {

    beforeEach(async(() => {

      TestBed.configureTestingModule({
        declarations: [
          TimesheetsListExtendedComponent,
          TimesheetsNewExtendedComponent,
          NewComponent,
          TimesheetsDetailsExtendedComponent,
          ListComponent,
          DetailsComponent,
          FieldsComp
        ].concat(EntryComponents),
        imports: [
          TestingModule,
          RouterTestingModule.withRoutes([
            { path: 'timesheets', component: TimesheetsListExtendedComponent },
            { path: 'timesheets/:id', component: TimesheetsDetailsExtendedComponent }
          ])
        ],
        providers: [
          TimesheetsExtendedService,
          ChangeDetectorRef,
        ]

      }).compileComponents();

    }));

    beforeEach(() => {
      fixture = TestBed.createComponent(TimesheetsListExtendedComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });

    

  });
        
});
