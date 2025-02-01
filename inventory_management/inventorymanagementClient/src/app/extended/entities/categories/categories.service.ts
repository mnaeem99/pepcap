
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CategoriesService } from 'src/app/entities/categories/categories.service';
@Injectable({
  providedIn: 'root'
})
export class CategoriesExtendedService extends CategoriesService {
	constructor(protected httpclient: HttpClient) { 
    super(httpclient);
    this.url += '/extended';
  }
}
