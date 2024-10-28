package com.fawry.intern.couponservice.coupon;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public record CouponRequestBody(
        String code,
        double value,
        @JsonIgnoreProperties(ignoreUnknown = true)
        int customerId,
        @JsonIgnoreProperties(ignoreUnknown = true)
        int orderId
) {
}
