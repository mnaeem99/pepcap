import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { ChangeDetectorRef, NO_ERRORS_SCHEMA } from '@angular/core';
import { RouterTestingModule } from '@angular/router/testing';

import { EntryComponents, TestingModule } from 'src/testing/utils';
import { InventoryTransactionsExtendedService, InventoryTransactionsDetailsExtendedComponent, InventoryTransactionsListExtendedComponent, InventoryTransactionsNewExtendedComponent } from '../';
import { IInventoryTransactions } from 'src/app/entities/inventory-transactions';
import { ListFiltersComponent, ServiceUtils } from 'src/app/common/shared';
import { ListComponent, DetailsComponent, NewComponent, FieldsComp } from 'src/app/common/general-components';

describe('InventoryTransactionsListExtendedComponent', () => {
  let fixture: ComponentFixture<InventoryTransactionsListExtendedComponent>;
  let component: InventoryTransactionsListExtendedComponent;
  let el: HTMLElement;

  describe('Unit tests', () => {
  
    beforeEach(async(() => {
      
      TestBed.configureTestingModule({
        declarations: [
          InventoryTransactionsListExtendedComponent,
          ListComponent
        ],
        imports: [TestingModule],
        providers: [
          InventoryTransactionsExtendedService,      
          ChangeDetectorRef,
        ],
        schemas: [NO_ERRORS_SCHEMA]   
      }).compileComponents();

    }));
    
    beforeEach(() => {
      fixture = TestBed.createComponent(InventoryTransactionsListExtendedComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });
  
  });
  
  describe('Integration tests', () => {

    beforeEach(async(() => {

      TestBed.configureTestingModule({
        declarations: [
          InventoryTransactionsListExtendedComponent,
          InventoryTransactionsNewExtendedComponent,
          NewComponent,
          InventoryTransactionsDetailsExtendedComponent,
          ListComponent,
          DetailsComponent,
          FieldsComp
        ].concat(EntryComponents),
        imports: [
          TestingModule,
          RouterTestingModule.withRoutes([
            { path: 'inventorytransactions', component: InventoryTransactionsListExtendedComponent },
            { path: 'inventorytransactions/:id', component: InventoryTransactionsDetailsExtendedComponent }
          ])
        ],
        providers: [
          InventoryTransactionsExtendedService,
          ChangeDetectorRef,
        ]

      }).compileComponents();

    }));

    beforeEach(() => {
      fixture = TestBed.createComponent(InventoryTransactionsListExtendedComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });

    

  });
        
});
