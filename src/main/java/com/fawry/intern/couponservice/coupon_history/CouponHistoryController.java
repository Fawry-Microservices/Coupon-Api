package com.fawry.intern.couponservice.coupon_history;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/history")
public class CouponHistoryController {

    private final CouponHistoryService couponHistoryService;
    public CouponHistoryController(CouponHistoryService couponHistoryService) {
        this.couponHistoryService = couponHistoryService;
    }

    @GetMapping
    public Page<CouponHistory> getAll(Pageable pageable){
        return couponHistoryService.getAll(pageable);
    }

}
