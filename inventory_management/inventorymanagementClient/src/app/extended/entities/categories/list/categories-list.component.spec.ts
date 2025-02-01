import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { ChangeDetectorRef, NO_ERRORS_SCHEMA } from '@angular/core';
import { RouterTestingModule } from '@angular/router/testing';

import { EntryComponents, TestingModule } from 'src/testing/utils';
import { CategoriesExtendedService, CategoriesDetailsExtendedComponent, CategoriesListExtendedComponent, CategoriesNewExtendedComponent } from '../';
import { ICategories } from 'src/app/entities/categories';
import { ListFiltersComponent, ServiceUtils } from 'src/app/common/shared';
import { ListComponent, DetailsComponent, NewComponent, FieldsComp } from 'src/app/common/general-components';

describe('CategoriesListExtendedComponent', () => {
  let fixture: ComponentFixture<CategoriesListExtendedComponent>;
  let component: CategoriesListExtendedComponent;
  let el: HTMLElement;

  describe('Unit tests', () => {
  
    beforeEach(async(() => {
      
      TestBed.configureTestingModule({
        declarations: [
          CategoriesListExtendedComponent,
          ListComponent
        ],
        imports: [TestingModule],
        providers: [
          CategoriesExtendedService,      
          ChangeDetectorRef,
        ],
        schemas: [NO_ERRORS_SCHEMA]   
      }).compileComponents();

    }));
    
    beforeEach(() => {
      fixture = TestBed.createComponent(CategoriesListExtendedComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });
  
  });
  
  describe('Integration tests', () => {

    beforeEach(async(() => {

      TestBed.configureTestingModule({
        declarations: [
          CategoriesListExtendedComponent,
          CategoriesNewExtendedComponent,
          NewComponent,
          CategoriesDetailsExtendedComponent,
          ListComponent,
          DetailsComponent,
          FieldsComp
        ].concat(EntryComponents),
        imports: [
          TestingModule,
          RouterTestingModule.withRoutes([
            { path: 'categories', component: CategoriesListExtendedComponent },
            { path: 'categories/:id', component: CategoriesDetailsExtendedComponent }
          ])
        ],
        providers: [
          CategoriesExtendedService,
          ChangeDetectorRef,
        ]

      }).compileComponents();

    }));

    beforeEach(() => {
      fixture = TestBed.createComponent(CategoriesListExtendedComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });

    

  });
        
});
