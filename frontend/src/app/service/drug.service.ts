import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

const API = 'https://placebo-api.herokuapp.com';

@Injectable({
  providedIn: 'root'
})
export class DrugService {

  constructor(private http: HttpClient) {
  }

  getTypes(): Observable<any> {
    return this.http.get(`${API}/drugs/types`, httpOptions);
  }

  getDrugs(reqBody: any, page: number, size: number = 20): Observable<any> {
    let params = new HttpParams();
    params = params.append('page', page.toString());
    params = params.append('size', size.toString());

    return this.http.post(`${API}/drugs`, reqBody, {params});
  }

  getDrugById(id: any): Observable<any> {
    return this.http.get(`${API}/drugs/${id}`, httpOptions);
  }

}
