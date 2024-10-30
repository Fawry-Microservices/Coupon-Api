import { Component, OnInit } from '@angular/core';
import { CouponsService } from '../services/coupons.service';
import { Coupon, CouponHistory, Coupons } from '../../types';
import { CouponComponent } from '../components/coupon/coupon.component';
import { CommonModule } from '@angular/common';
import { TableLazyLoadEvent, TableModule } from 'primeng/table';
import { ButtonModule } from 'primeng/button';
import { CouponsHistoryService } from '../services/coupon-history-service.service';
import { PaginatorModule } from 'primeng/paginator';
import { LazyLoadEvent } from 'primeng/api';
import { HttpParams } from '@angular/common/http';


@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CouponComponent, CommonModule, TableModule, ButtonModule, PaginatorModule],
  templateUrl: './history.component.html',
  styleUrl: './history.component.css',
})
export class CouponHistoryComponent implements OnInit{



  couponHistory: CouponHistory[] = [];
  totalElements: number = 0;
  totalRecords: number = 0;
  loading: boolean = true;
  pageSize: number = 10;
  pageNumber: number = 0;
  

  constructor(private couponHistoryService: CouponsHistoryService) { }
  
  ngOnInit() {
    this.loadCoupons(0, this.pageSize, 'consumptionDate', 'desc');
  }

  loadCoupons(page: number, size: number, sortField: string, sortOrder: string) {
    this.loading = true;
    const params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString())
      .set('sort', `${sortField},${sortOrder}`);

    this.couponHistoryService.getCouponshistory(params).subscribe((data) => {
      this.couponHistory = data.content;
      this.totalRecords = data.totalElements;
      this.loading = false;
    });
  }

  onLazyLoad(event: TableLazyLoadEvent) {
    const page = event.first! / event.rows!;
    const sortOrder = event.sortOrder === 1 ? 'asc' : 'desc';
    const sortField = Array.isArray(event.sortField) 
    ? event.sortField.join(',') 
    : event.sortField || 'consumptionDate';
    


    this.loadCoupons(page, this.pageSize, sortField, sortOrder);
  }

}
