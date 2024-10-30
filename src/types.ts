import { HttpContext, HttpHeaders, HttpParams } from "@angular/common/http";

export interface Options{
    
        headers?: HttpHeaders | {
            [header: string]: string | string[];
        };
        observe?: 'body';
        context?: HttpContext;
        params?: HttpParams | {
            [param: string]: string | number | boolean | ReadonlyArray<string | number | boolean>;
        };
        reportProgress?: boolean;
        responseType?: 'json';
        withCredentials?: boolean;
        transferCache?: {
            includeHeaders?: string[];
        } | boolean;
    
}

export interface Coupons{
    items: Coupon[];
    total: number;
    page: number;
    perPage: number;
    totalPages: number;
}

export interface Coupon {
    id?: number;
    code: string;
    numberOfUsages: number;
    availableUsages?: number;
    expiryDate?: Date;
    type: CouponType;
    active: boolean;
    value: number;
}


export enum CouponType {
    PERCENTAGE,
    FIXED
}

export interface PaginationParams{
    [param: string]: | string | number | boolean | ReadonlyArray<string | number | boolean>;
    page: number;
    perPage: number;
}



export interface CouponHistory {
    id: number;
    code: string;
    orderId: string;
    valueBeforeDiscount: number;
    valueAfterDiscount: number;
    consumptionDate: Date | null;
}
