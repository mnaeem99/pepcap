import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';

import { TestingModule, EntryComponents } from 'src/testing/utils';
import { InventoryTransactionsExtendedService, InventoryTransactionsNewExtendedComponent } from '../';
import { IInventoryTransactions } from 'src/app/entities/inventory-transactions';
import { NewComponent, FieldsComp } from 'src/app/common/general-components';

describe('InventoryTransactionsNewExtendedComponent', () => {
  let component: InventoryTransactionsNewExtendedComponent;
  let fixture: ComponentFixture<InventoryTransactionsNewExtendedComponent>;
  
  let el: HTMLElement;

  describe('Unit tests', () => {
    beforeEach(async(() => {
      TestBed.configureTestingModule({
        declarations: [
          InventoryTransactionsNewExtendedComponent,
            NewComponent,
            FieldsComp
        ],
        imports: [TestingModule],
        providers: [
          InventoryTransactionsExtendedService,
          { provide: MAT_DIALOG_DATA, useValue: {} }
        ],
        schemas: [NO_ERRORS_SCHEMA] 
      }).compileComponents();
    }));

    beforeEach(() => {
      fixture = TestBed.createComponent(InventoryTransactionsNewExtendedComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });

    
  })

  describe('Integration tests', () => {

    describe('', () => {
      beforeEach(async(() => {
        TestBed.configureTestingModule({
          declarations: [
            InventoryTransactionsNewExtendedComponent,
            NewComponent,
            FieldsComp
          ].concat(EntryComponents),
          imports: [TestingModule],
          providers: [
            InventoryTransactionsExtendedService,
            { provide: MAT_DIALOG_DATA, useValue: {} }
          ]
        });
      }));
  
      beforeEach(() => {
        fixture = TestBed.createComponent(InventoryTransactionsNewExtendedComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
      });

    })

  })
  
});