package com.fawry.intern.couponservice.coupon_history;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CouponHistoryService {

    private final CouponHistoryRepository couponHistoryRepository;

    public CouponHistoryService(CouponHistoryRepository couponHistoryRepository) {
        this.couponHistoryRepository = couponHistoryRepository;
    }

    public Page<CouponHistory> getAll(Pageable pageable) {
        return couponHistoryRepository.findAll(pageable);
    }
    public CouponHistory get(Long id) {
        return couponHistoryRepository.findById(id).orElse(null);
    }
}
