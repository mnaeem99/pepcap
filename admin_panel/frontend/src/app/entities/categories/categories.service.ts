
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ICategories } from './icategories';
import { GenericApiService } from 'src/app/common/shared';

@Injectable({
  providedIn: 'root'
})
export class CategoriesService extends GenericApiService<ICategories> { 
  constructor(protected httpclient: HttpClient) { 
    super(httpclient, "categories");
  }
  
  convertEnumToArray(enumm:any){
      const arrayObjects = []
        // Retrieve key and values using Object.entries() method.
        for (const [propertyKey, propertyValue] of Object.entries(enumm)) {
         // Add values to array
         arrayObjects.push(propertyValue);
       }

     return arrayObjects;
   }
  
}
