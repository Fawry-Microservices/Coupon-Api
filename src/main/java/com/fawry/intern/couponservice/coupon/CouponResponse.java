package com.fawry.intern.couponservice.coupon;

public record CouponResponse(
        String status,
        double amount,
        String message
) {
}
