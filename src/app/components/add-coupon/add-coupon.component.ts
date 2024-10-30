import { Component} from '@angular/core';
import { Coupon, CouponType } from '../../../types';
import { FormsModule,FormGroup,  ReactiveFormsModule, FormControl, FormBuilder, Validators } from '@angular/forms';
import { CouponsService } from '../../services/coupons.service';
import { Router, RouterModule } from '@angular/router';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-add-coupon',
  standalone: true,
  imports: [FormsModule, ReactiveFormsModule, RouterModule],
  templateUrl: './add-coupon.component.html',
  styleUrl: './add-coupon.component.css'
})
export class AddCouponComponent {

  couponForm: FormGroup;

  constructor(private fb: FormBuilder, private router: Router, private http: HttpClient) { 
      this.couponForm = this.fb.group({
      code: ['', Validators.required],
      numberOfUsages: [0, [Validators.required, Validators.min(1)]],
      expiryDate: ['', Validators.required],
      type: ['PERCENTAGE', Validators.required],
      active: [true],
      value: [0, [Validators.required, Validators.min(1)]],
    });
}
  
  ngOnInit(): void {
  }

  onSubmit() {
    console.log(this.couponForm.value);
    
    if (this.couponForm.valid) {
      this.http.post('http://localhost:8080/coupon', this.couponForm.value)
        .subscribe({
          next: response => console.log('Coupon added successfully', response),
          error: error => console.error('Error adding coupon:', error)
        });
    } else {
      console.error('Form is invalid');
    }
    this.router.navigate(["/home"]);
  }

}
