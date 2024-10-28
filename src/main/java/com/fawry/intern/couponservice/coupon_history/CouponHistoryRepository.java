package com.fawry.intern.couponservice.coupon_history;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CouponHistoryRepository extends JpaRepository<CouponHistory, Long> {

}
