
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ITasks } from './itasks';
import { GenericApiService } from 'src/app/common/shared';

@Injectable({
  providedIn: 'root'
})
export class TasksService extends GenericApiService<ITasks> { 
  constructor(protected httpclient: HttpClient) { 
    super(httpclient, "tasks");
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
