import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { CONFIG } from '../../app.config';

@Injectable({providedIn: 'root'})
export class MainService {
  constructor(private httpClient: HttpClient) {
  }

  private buildHeaders(): HttpHeaders {
    let headers = new HttpHeaders().set('Access-Control-Allow-Origin', '*');
    headers = headers.set('Content-Type', 'application/json;charset=UTF-8');
    return headers;
  }

  public listTenants(email: string) {
    const url = CONFIG.apiEndpoint + '/tenants/userTenants';
    const params = new HttpParams().set('userId', email);
    const options = {
      headers: this.buildHeaders(),
      params: params
    };
    return this.httpClient.get(url, options);
  }
}
