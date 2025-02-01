import { NgModule } from '@angular/core';

import { InventoryTransactionsExtendedService, InventoryTransactionsDetailsExtendedComponent, InventoryTransactionsListExtendedComponent, InventoryTransactionsNewExtendedComponent } from './';
import { InventoryTransactionsService } from 'src/app/entities/inventory-transactions';
import { InventoryTransactionsModule } from 'src/app/entities/inventory-transactions/inventory-transactions.module';
import { inventoryTransactionsRoute } from './inventory-transactions.route';

import { SharedModule  } from 'src/app/common/shared';
import { GeneralComponentsExtendedModule } from 'src/app/extended/common/general-components/general-extended.module';

const entities = [
    InventoryTransactionsDetailsExtendedComponent, InventoryTransactionsListExtendedComponent, InventoryTransactionsNewExtendedComponent 
  ]
@NgModule({
	declarations: entities,
	exports: entities,
  imports: [
    inventoryTransactionsRoute,
    InventoryTransactionsModule,
    SharedModule,
    GeneralComponentsExtendedModule,
  ],
  providers: [{ provide: InventoryTransactionsService, useClass: InventoryTransactionsExtendedService }],
})
export class InventoryTransactionsExtendedModule {
}
