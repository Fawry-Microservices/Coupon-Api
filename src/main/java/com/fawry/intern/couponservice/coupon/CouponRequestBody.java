package com.fawry.intern.couponservice.coupon;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

public record CouponRequestBody(
        String couponCode,
        double amount,
        @JsonIgnoreProperties(ignoreUnknown = true)
        int customerId,
        @JsonIgnoreProperties(ignoreUnknown = true)
        @JsonProperty(value = "transactionId")
        String orderId
) {
}
