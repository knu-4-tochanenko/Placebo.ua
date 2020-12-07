import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

const API = 'http://localhost:8989/api';

@Injectable({
  providedIn: 'root'
})
export class DrugService {

  constructor(private http: HttpClient) {
  }

  getDrugs(data: any, settings: any): Observable<any> {
    let queryString = '?page=' + settings['page'] + '&size=' + settings['size'];
    if (settings['sortType']) {
      queryString += '&sort=type' + (settings['ascSort'] ? '' : ',desc');
    }
    if (settings['sortName']) {
      queryString += '&sort=name' + (settings['ascName'] ? '' : ',desc');
    }
    if (settings['sortPrice']) {
      queryString += '&sort=price' + (settings['ascPrice'] ? '' : ',desc');
    }

    return this.http.get(API + queryString, data);
  }

  searchDrugs(data: any): Observable<any> {
    return this.http.get(API + 'drugs', data);
  }

  getTypes(): Observable<any> {
    return this.http.get(API + 'drugs/types');
  }

}
