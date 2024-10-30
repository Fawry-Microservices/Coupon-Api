import { Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { CouponHistoryComponent } from './history/history.component';
import { AddCouponComponent } from './components/add-coupon/add-coupon.component';

export const routes: Routes = [
    {
        path: '',
        component: HomeComponent
    },
    {
        path: 'home',
        component: HomeComponent
    },
    {
        path: 'add',
        component: AddCouponComponent
    }
    ,
    {
        path: 'history',
        component: CouponHistoryComponent
    }
];
