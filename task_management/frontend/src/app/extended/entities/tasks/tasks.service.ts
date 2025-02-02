
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { TasksService } from 'src/app/entities/tasks/tasks.service';
@Injectable({
  providedIn: 'root'
})
export class TasksExtendedService extends TasksService {
	constructor(protected httpclient: HttpClient) { 
    super(httpclient);
    this.url += '/extended';
  }
}
