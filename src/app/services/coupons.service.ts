import { Injectable, numberAttribute } from '@angular/core';
import { ApiService } from './api.service';
import {  catchError, map, Observable, throwError } from 'rxjs';
import {  Coupon, Coupons, PaginationParams } from '../../types';
import { url } from 'node:inspector';
import { HttpClient, HttpParams } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class CouponsService {

  private base_url = 'http://localhost:8080/coupon';

  constructor(private apiService: ApiService, private httpClient: HttpClient) { }

  getCoupons(params: HttpParams): Observable<any> {
    return this.httpClient.get(this.base_url, { params });
  }
  deleteCoupon(id: number): Observable<void> {
    return this.apiService.delete(this.base_url ,id);
  }

  addCoupon = (coupon: Coupon): Observable<Coupon> => {
    console.log("In addCoupon method: ", coupon);
    
    return this.apiService.add(this.base_url, coupon) as Observable<Coupon>;
  }
}