
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { InventoryTransactionsService } from 'src/app/entities/inventory-transactions/inventory-transactions.service';
@Injectable({
  providedIn: 'root'
})
export class InventoryTransactionsExtendedService extends InventoryTransactionsService {
	constructor(protected httpclient: HttpClient) { 
    super(httpclient);
    this.url += '/extended';
  }
}
