import { Component, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CouponsService } from '../services/coupons.service';
import { Coupon, Coupons } from '../../types';
import { CouponComponent } from '../components/coupon/coupon.component';
import { CommonModule } from '@angular/common';
import { TableLazyLoadEvent, TableModule } from 'primeng/table';
import { ButtonModule } from 'primeng/button';
import { HttpClient, HttpErrorResponse, HttpParams } from '@angular/common/http';


@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CouponComponent, CommonModule, TableModule, ButtonModule],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css',
})
export class HomeComponent {

  coupons: Coupon[] = [];
  coupon!: Coupon;
    totalElements: number = 0;
  totalRecords: number = 0;
  loading: boolean = true;
  pageSize: number = 10;
  pageNumber: number = 0;


  constructor(private couponService: CouponsService) { }
  

  ngOnInit() {
    this.loadCoupons(0, this.pageSize, 'code', 'desc');
  }

  loadCoupons(page: number, size: number, sortField: string, sortOrder: string) {
    this.loading = true;
    const params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString())
      .set('sort', `${sortField},${sortOrder}`);

    this.couponService.getCoupons(params).subscribe((data) => {
      this.coupons = data.content;
      this.totalRecords = data.totalElements;
      this.loading = false;
    });
  }

  onLazyLoad(event: TableLazyLoadEvent) {
    const page = event.first! / event.rows!;
    const sortOrder = event.sortOrder === 1 ? 'asc' : 'desc';
    const sortField = Array.isArray(event.sortField) 
    ? event.sortField.join(',') 
    : event.sortField || "code";
    
    this.loadCoupons(page, this.pageSize, sortField, sortOrder);
  }
  onSubmitToDelete(id: number) {
    this.couponService.deleteCoupon(id).subscribe({
    next: () => this.ngOnInit(),
    error: error => console.error("Error deleting coupon:", error)
  });
  }
}
