package com.fawry.intern.couponservice.coupon_history;

import com.fawry.intern.couponservice.coupon.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface CouponHistoryRepository extends JpaRepository<CouponHistory, Long> {
    Optional<CouponHistory> findByOrderId(String order_id);

}
