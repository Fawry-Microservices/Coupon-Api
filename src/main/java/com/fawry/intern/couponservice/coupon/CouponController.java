package com.fawry.intern.couponservice.coupon;


import com.fawry.intern.couponservice.coupon_history.CouponHistoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.Optional;


@RestController
@RequestMapping("/coupon")
public class CouponController {

    private final CouponService couponService;

    public CouponController(CouponService couponService, CouponHistoryRepository couponHistoryRepository, CouponRepository couponRepository) {
        this.couponService = couponService;
    }

    @GetMapping
    public Page<Coupon> getAllCoupons(Pageable pageable) {
        return couponService.getAllCoupons(pageable);
    }

    @GetMapping("/{id}")
    public Coupon getSpecificCoupons(@PathVariable Long id) {
        return couponService.getSpecificCoupon(id);
    }

    @PutMapping("/{id}")
    public Coupon updateCoupon(@PathVariable Long id, @RequestBody Coupon coupon) {
        System.out.println(id);
        System.out.println(coupon);
        return couponService.updateCoupon(id, coupon);
    }

    @PostMapping
    public Coupon addCoupon(@RequestBody Coupon coupon) {
        System.out.println("Coupon to be added is: " + coupon.getCode());
        return couponService.addCoupon(coupon);
    }

    @DeleteMapping("/{id}")
    public boolean deleteCoupon(@PathVariable Long id) {
        return couponService.deleteCoupon(id);
    }

    @PostMapping("/consume")
    public ResponseEntity<CouponResponse> consumeCoupon(@RequestBody CouponRequestBody request) {
        CouponResponse couponResponse =  couponService.consume(request);

        if(couponResponse.status().equalsIgnoreCase("Fail")){
            return ResponseEntity.badRequest().body(couponResponse);
        }
        return ResponseEntity.ok(couponResponse);
    }

    @PostMapping("/validate")
    public ResponseEntity<CouponResponse> validateCoupon(@RequestBody CouponRequestBody request) {
        CouponResponse couponResponse =  couponService.validate(request);
        if(couponResponse.status().equalsIgnoreCase("Fail")){
            return ResponseEntity.badRequest().body(couponResponse);
        }
        return ResponseEntity.ok(couponResponse);
    }
    @PostMapping("/unconsume/{transactionId}")
    public ResponseEntity<CouponResponse> unconsume(@PathVariable String transactionId){
        System.out.println(transactionId);
        CouponResponse couponResponse = couponService.unconsume(transactionId);

        if(couponResponse.status().equalsIgnoreCase("Fail")){
            return ResponseEntity.badRequest().body(couponResponse);
        }
        return ResponseEntity.ok(couponResponse);

    }



}
