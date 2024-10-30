import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Coupon, Coupons, Options } from '../../types';


@Injectable({
  providedIn: 'root'
})
export class ApiService {
 
  private coupon!: Observable<Coupon>;

  constructor(private httpClient: HttpClient) { }

  get<T>(url: string): Observable<T>{
    return this.httpClient.get<T>(url) as Observable<T>;
  }
  add<T>(base_url: string, coupon: Coupon): Observable<Coupon> {
    this.coupon = this.httpClient.post(base_url, coupon) as Observable<Coupon>;
    console.log("Returned coupon is: " , this.coupon);
    return this.coupon;
  }
  delete(url:string ,id: number): Observable<void> {
    return this.httpClient.delete<void>(`${url}/${id}`);
  }


}


