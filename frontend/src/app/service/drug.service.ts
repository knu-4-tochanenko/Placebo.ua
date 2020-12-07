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

  getDrugs(
    page: number = 0,
    size: number = 15,
    sortType: boolean = false,
    ascSort: boolean = true,
    sortName: boolean = false,
    ascName: boolean = true,
    sortPrice: boolean = false,
    ascPrice = true,
  ): Observable<any> {
    let queryString = '?page=' + page + '&size=' + size;
    if (sortType) {
      queryString += '&sort=type' + (ascSort ? '' : ',desc');
    }
    if (sortName) {
      queryString += '&sort=name' + (ascName ? '' : ',desc');
    }
    if (sortPrice) {
      queryString += '&sort=price' + (ascPrice ? '' : ',desc');
    }

    return this.http.get(API + queryString, httpOptions);
  }

  getTypes(): Observable<any> {
    return this.http.get(API + 'drugs/types');
  }

}
