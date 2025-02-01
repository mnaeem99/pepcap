import { NgModule } from '@angular/core';

import { InventoryTransactionsDetailsComponent, InventoryTransactionsListComponent, InventoryTransactionsNewComponent} from './';
import { inventoryTransactionsRoute } from './inventory-transactions.route';

import { SharedModule } from 'src/app/common/shared';
import { GeneralComponentsModule } from 'src/app/common/general-components/general.module';

const entities = [
    InventoryTransactionsDetailsComponent, InventoryTransactionsListComponent,InventoryTransactionsNewComponent
  ]
@NgModule({
	declarations: entities,
	exports: entities,
  imports: [
    inventoryTransactionsRoute,
    SharedModule,
    GeneralComponentsModule,
  ]
})
export class InventoryTransactionsModule {
}
