import { Component, Input } from '@angular/core';
import { Coupon } from '../../../types';

@Component({
  selector: 'app-coupon',
  standalone: true,
  imports: [],
  templateUrl: './coupon.component.html',
  styleUrl: './coupon.component.css'
})
export class CouponComponent {

  @Input() coupon!: Coupon;
    
}
