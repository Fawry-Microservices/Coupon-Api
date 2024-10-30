package com.fawry.intern.couponservice.coupon;

import com.fawry.intern.couponservice.coupon_history.CouponHistory;
import com.fawry.intern.couponservice.coupon_history.CouponHistoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

public class CouponServiceTest {
    @Test
    public void givenNotExistedCoupon_whenConsuming_thenGetStatusFail() {

        CouponRepository couponRepository = Mockito.mock(CouponRepository.class);
        CouponHistoryRepository couponHistoryRepository = Mockito.mock(CouponHistoryRepository.class);

        CouponService couponService = new CouponService(couponRepository, couponHistoryRepository);
        Mockito.when(couponRepository.findByCode(Mockito.anyString())).thenReturn(Optional.empty());

        CouponResponse couponResponse = couponService.consume(new CouponRequestBody("Save 10%", 1000, 1012, "order_11"));

        Assertions.assertEquals("Fail", couponResponse.status());
        Assertions.assertEquals(1000, couponResponse.amount());
    }


    @Test
    public void givenInactiveCoupon_whenConsuming_thenGetStatusFail() {

        CouponRepository couponRepository = Mockito.mock(CouponRepository.class);
        CouponHistoryRepository couponHistoryRepository = Mockito.mock(CouponHistoryRepository.class);

        CouponService couponService = new CouponService(couponRepository, couponHistoryRepository);
        Mockito.when(couponRepository.findByCode(Mockito.anyString())).thenReturn(Optional.of(new Coupon()));

        CouponResponse couponResponse = couponService.consume(new CouponRequestBody("Save 10%", 1000, 1012, "order_11"));

        Assertions.assertEquals("Fail", couponResponse.status());
        Assertions.assertEquals(1000, couponResponse.amount());
    }

    @Test
    public void givenCouponWithZeroAvailable_whenConsuming_thenGetStatusFail() {

        CouponRepository couponRepository = Mockito.mock(CouponRepository.class);
        CouponHistoryRepository couponHistoryRepository = Mockito.mock(CouponHistoryRepository.class);
        CouponService couponService = new CouponService(couponRepository, couponHistoryRepository);

        Coupon coupon = new Coupon();
        coupon.setActive(true);
        Mockito.when(couponRepository.findByCode(Mockito.anyString()))
                .thenReturn(Optional.of(coupon));

        CouponResponse couponResponse = couponService.consume(new CouponRequestBody("Save 10%", 1000, 1012, "order_11"));

        Assertions.assertEquals("Fail", couponResponse.status());
        Assertions.assertEquals(1000, couponResponse.amount());
        Assertions.assertFalse(coupon.isActive());
        Mockito.verify(couponRepository, Mockito.times(1)).save(Mockito.any(Coupon.class));
        Mockito.verify(couponRepository, Mockito.times(2)).findByCode(Mockito.anyString());
    }

    @Test
    public void givenValidPercentageCoupon_whenConsuming_thenGetStatusSuccess() {

        CouponRepository couponRepository = Mockito.mock(CouponRepository.class);
        CouponHistoryRepository couponHistoryRepository = Mockito.mock(CouponHistoryRepository.class);
        CouponService couponService = new CouponService(couponRepository, couponHistoryRepository);

        Coupon coupon = new Coupon();
        coupon.setAvailableUsages(1);
        coupon.setActive(true);
        coupon.setType(CouponType.PERCENTAGE);
        coupon.setValue(10);

        Mockito.when(couponRepository.findByCode(Mockito.anyString())).thenReturn(Optional.of(coupon));

        CouponResponse couponResponse = couponService.consume(new CouponRequestBody("Save 10%", 1000, 1012, "order_11"));

        Assertions.assertEquals("Success", couponResponse.status());
        Assertions.assertEquals(900, couponResponse.amount());
    }
    @Test
    public void givenValidFixedCoupon_whenConsuming_thenGetStatusSuccess() {

        CouponRepository couponRepository = Mockito.mock(CouponRepository.class);
        CouponHistoryRepository couponHistoryRepository = Mockito.mock(CouponHistoryRepository.class);
        CouponService couponService = new CouponService(couponRepository, couponHistoryRepository);

        Coupon coupon = new Coupon();
        coupon.setAvailableUsages(2);
        coupon.setActive(true);
        coupon.setType(CouponType.FIXED);
        coupon.setValue(100);

        Mockito.when(couponRepository.findByCode(Mockito.anyString())).thenReturn(Optional.of(coupon));

        CouponResponse couponResponse = couponService.consume(new CouponRequestBody("Save 100 L.E", 1000, 1012, "order_11"));

        Assertions.assertEquals("Success", couponResponse.status());
        Assertions.assertEquals(900, couponResponse.amount());
        Assertions.assertEquals(1, coupon.getAvailableUsages());
    }


    @Test
    public void givenValidFixedCoupon_whenConsumingAndThePaidAmountIsLessThan100_thenGetStatusFail() {

        CouponRepository couponRepository = Mockito.mock(CouponRepository.class);
        CouponHistoryRepository couponHistoryRepository = Mockito.mock(CouponHistoryRepository.class);
        CouponService couponService = new CouponService(couponRepository, couponHistoryRepository);

        Coupon coupon = new Coupon();
        coupon.setAvailableUsages(1);
        coupon.setActive(true);
        coupon.setType(CouponType.FIXED);
        coupon.setValue(100);

        Mockito.when(couponRepository.findByCode(Mockito.anyString())).thenReturn(Optional.of(coupon));

        CouponResponse couponResponse = couponService.consume(new CouponRequestBody("Save 75 L.E", 150, 1012, "order_11"));

        Assertions.assertEquals("Fail", couponResponse.status());
        Assertions.assertEquals(150, couponResponse.amount());
    }



    @Test
    public void givenNotExistedOrderId_whenUnconsuming_thenGetStatusFail() {

        CouponRepository couponRepository = Mockito.mock(CouponRepository.class);
        CouponHistoryRepository couponHistoryRepository = Mockito.mock(CouponHistoryRepository.class);
        CouponService couponService = new CouponService(couponRepository, couponHistoryRepository);


        Mockito.when(couponHistoryRepository.findByOrderId(Mockito.anyString())).thenReturn(Optional.empty());

        CouponResponse couponResponse = couponService.unconsume(Mockito.anyString());

        Assertions.assertEquals("Fail", couponResponse.status());
        Assertions.assertEquals(0, couponResponse.amount());
    }
    @Test
    public void givenValidOrderIdOfNotExistingCoupon_whenUnconsuming_thenGetStatusSuccess() {

        CouponRepository couponRepository = Mockito.mock(CouponRepository.class);
        CouponHistoryRepository couponHistoryRepository = Mockito.mock(CouponHistoryRepository.class);
        CouponService couponService = new CouponService(couponRepository, couponHistoryRepository);



        Mockito.when(couponHistoryRepository.findByOrderId(Mockito.anyString())).thenReturn(Optional.of(new CouponHistory()));
        Mockito.when(couponRepository.findByCode(Mockito.anyString())).thenReturn(Optional.empty());
        CouponResponse couponResponse = couponService.unconsume(Mockito.anyString());


        Mockito.verify(couponRepository, Mockito.times(1)).save(Mockito.any(Coupon.class));
        Assertions.assertEquals("Success", couponResponse.status());
        Assertions.assertEquals(0, couponResponse.amount());
    }

    @Test
    public void givenValidOrderIdOfExistingCoupon_whenUnconsuming_thenGetStatusSuccess() {

        CouponRepository couponRepository = Mockito.mock(CouponRepository.class);
        CouponHistoryRepository couponHistoryRepository = Mockito.mock(CouponHistoryRepository.class);
        Mockito.when(couponHistoryRepository.findByOrderId(Mockito.anyString())).thenReturn(Optional.of(new CouponHistory()));
        Coupon coupon = new Coupon();
        coupon.setAvailableUsages(10);
        Mockito.when(couponRepository.findByCode(Mockito.any())).thenReturn(Optional.of(coupon));
        CouponService couponService = new CouponService(couponRepository, couponHistoryRepository);


        CouponResponse couponResponse = couponService.unconsume(Mockito.anyString());


        Assertions.assertEquals(11, coupon.getAvailableUsages());
        Assertions.assertEquals("Success", couponResponse.status());
        Assertions.assertEquals(0, couponResponse.amount());
    }




}
