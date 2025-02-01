import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { RouterTestingModule } from '@angular/router/testing';
import { DetailsComponent, ListComponent, FieldsComp } from 'src/app/common/general-components';

import { TestingModule, EntryComponents } from 'src/testing/utils';
import { InventoryTransactionsExtendedService, InventoryTransactionsDetailsExtendedComponent, InventoryTransactionsListExtendedComponent } from '../';
import { IInventoryTransactions } from 'src/app/entities/inventory-transactions';
describe('InventoryTransactionsDetailsExtendedComponent', () => {
  let component: InventoryTransactionsDetailsExtendedComponent;
  let fixture: ComponentFixture<InventoryTransactionsDetailsExtendedComponent>;
  let el: HTMLElement;
  
  describe('Unit Tests', () => {
    beforeEach(async(() => {
      TestBed.configureTestingModule({
        declarations: [
          InventoryTransactionsDetailsExtendedComponent,
          DetailsComponent
        ],
        imports: [TestingModule],
        providers: [
          InventoryTransactionsExtendedService,
        ],
        schemas: [NO_ERRORS_SCHEMA]  
      }).compileComponents();
    }));
  
    beforeEach(() => {
      fixture = TestBed.createComponent(InventoryTransactionsDetailsExtendedComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });

  });
  
  describe('Integration Tests', () => {
    beforeEach(async(() => {

      TestBed.configureTestingModule({
        declarations: [
          InventoryTransactionsDetailsExtendedComponent,
          InventoryTransactionsListExtendedComponent,
          DetailsComponent,
          ListComponent,
          FieldsComp
        ].concat(EntryComponents),
        imports: [
          TestingModule,
          RouterTestingModule.withRoutes([
            { path: 'inventorytransactions', component: InventoryTransactionsDetailsExtendedComponent },
            { path: 'inventorytransactions/:id', component: InventoryTransactionsListExtendedComponent }
          ])
        ],
        providers: [
          InventoryTransactionsExtendedService
        ]

      }).compileComponents();
    }));

    beforeEach(() => {
      fixture = TestBed.createComponent(InventoryTransactionsDetailsExtendedComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });

  });
  
});
