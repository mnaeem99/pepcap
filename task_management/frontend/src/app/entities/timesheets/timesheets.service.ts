
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ITimesheets } from './itimesheets';
import { GenericApiService } from 'src/app/common/shared';

@Injectable({
  providedIn: 'root'
})
export class TimesheetsService extends GenericApiService<ITimesheets> { 
  constructor(protected httpclient: HttpClient) { 
    super(httpclient, "timesheets");
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
