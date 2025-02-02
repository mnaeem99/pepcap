
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ProjectsService } from 'src/app/entities/projects/projects.service';
@Injectable({
  providedIn: 'root'
})
export class ProjectsExtendedService extends ProjectsService {
	constructor(protected httpclient: HttpClient) { 
    super(httpclient);
    this.url += '/extended';
  }
}
