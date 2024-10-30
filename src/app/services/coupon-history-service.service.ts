import { Injectable } from '@angular/core';
import { ApiService } from './api.service';
import {  Observable } from 'rxjs';
import {  CouponHistory, PaginationParams } from '../../types';
import { HttpClient, HttpParams } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class CouponsHistoryService {
    private baseUrl = 'http://localhost:8080/history';


  constructor(private httpClient: HttpClient) { }

  getCouponshistory(params: HttpParams): Observable<any> {
    return this.httpClient.get(this.baseUrl, { params });
  }

}