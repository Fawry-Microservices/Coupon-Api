package com.fawry.intern.couponservice.coupon;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/coupon")
public class CouponController {

    private final CouponService couponService;
    public CouponController(CouponService couponService) {
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
        System.out.println("Coupon to be added is: " + coupon);
        return couponService.addCoupon(coupon);
    }

    @DeleteMapping("/{id}")
    public boolean deleteCoupon(@PathVariable Long id) {
        return couponService.deleteCoupon(id);
    }

    @PostMapping("/consume")
    public CouponResponse consumeCoupon(@RequestBody CouponRequestBody request) {
        return couponService.consume(request);
    }

    @PostMapping("/validate")
    public CouponResponse validateCoupon(@RequestBody CouponRequestBody request) {
        return couponService.validate(request);
    }



}
