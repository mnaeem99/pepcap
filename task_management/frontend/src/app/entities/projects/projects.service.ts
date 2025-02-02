
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { IProjects } from './iprojects';
import { GenericApiService } from 'src/app/common/shared';

@Injectable({
  providedIn: 'root'
})
export class ProjectsService extends GenericApiService<IProjects> { 
  constructor(protected httpclient: HttpClient) { 
    super(httpclient, "projects");
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
