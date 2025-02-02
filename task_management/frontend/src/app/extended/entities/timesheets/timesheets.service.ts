
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { TimesheetsService } from 'src/app/entities/timesheets/timesheets.service';
@Injectable({
  providedIn: 'root'
})
export class TimesheetsExtendedService extends TimesheetsService {
	constructor(protected httpclient: HttpClient) { 
    super(httpclient);
    this.url += '/extended';
  }
}
