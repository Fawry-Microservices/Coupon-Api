package com.fawry.intern.couponservice.coupon;

import com.fawry.intern.couponservice.coupon_history.CouponHistory;
import com.fawry.intern.couponservice.coupon_history.CouponHistoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CouponService {

    private final CouponRepository couponRepository;
    private final CouponHistoryRepository couponHistoryRepository;

    public CouponService(CouponRepository couponRepository, CouponHistoryRepository couponHistoryRepository) {
        this.couponRepository = couponRepository;
        this.couponHistoryRepository = couponHistoryRepository;
    }

    public Coupon addCoupon(Coupon coupon) {
        coupon.setAvailableUsages(coupon.getNumberOfUsages());
        return couponRepository.save(coupon);
    }

    public Page<Coupon> getAllCoupons(Pageable pageable) {
        return couponRepository.findAll(pageable);
    }

    public Coupon getSpecificCoupon(Long id) {
        return couponRepository.findById(id).orElse(null);
    }


    public Coupon updateCoupon(Long id, Coupon newCoupon) {
         Coupon existingCoupon = couponRepository
                 .findById(id)
                 .orElseThrow(() -> new RuntimeException("Coupon not found with id " + id));

            if (newCoupon.getCode() != null && !newCoupon.getCode().equals(existingCoupon.getCode())) {
                existingCoupon.setCode(newCoupon.getCode());
            }
            if (newCoupon.getNumberOfUsages() != 0 && newCoupon.getNumberOfUsages() != existingCoupon.getNumberOfUsages()) {
                existingCoupon.setNumberOfUsages(newCoupon.getNumberOfUsages());
            }
            if (newCoupon.getAvailableUsages() != 0 && newCoupon.getAvailableUsages() != existingCoupon.getAvailableUsages()) {
                existingCoupon.setAvailableUsages(newCoupon.getAvailableUsages());
            }
            if (newCoupon.getExpiryDate() != null && !newCoupon.getExpiryDate().equals(existingCoupon.getExpiryDate())) {
                existingCoupon.setExpiryDate(newCoupon.getExpiryDate());
            }
            if (newCoupon.getType() != null && !newCoupon.getType().equals(existingCoupon.getType())) {
                existingCoupon.setType(newCoupon.getType());
            }
            if (newCoupon.isActive() != existingCoupon.isActive()) {
                existingCoupon.setActive(newCoupon.isActive());
            }
            if (newCoupon.getValue() != 0 && newCoupon.getValue() != existingCoupon.getValue()) {
                existingCoupon.setValue(newCoupon.getValue());
            }
            return couponRepository.save(existingCoupon);
    }

    public boolean deleteCoupon(Long id) {
        Optional<Coupon> coupon = couponRepository.findById(id);
        if (coupon.isPresent()) {
            couponRepository.deleteById(id);
            return true;
        }
        else
            throw new RuntimeException("This Coupon with Id: " + id + " is not found");
    }


    public CouponResponse consume(CouponRequestBody request) {
        Optional<Coupon> requiredCoupon = couponRepository.findByCode(request.couponCode());
        if (requiredCoupon.isPresent()) {
            Coupon coupon = requiredCoupon.get();
            if (coupon.isActive() && coupon.getAvailableUsages() > 0) {
                double couponValue = coupon.getValue();
                double valueAfterDiscount;
                if(coupon.getType() == CouponType.PERCENTAGE){
                    valueAfterDiscount = request.amount() - (0.01 * couponValue * request.amount());
                }else
                    valueAfterDiscount = request.amount() - couponValue;

                if(valueAfterDiscount <= 100){
                    return new CouponResponse("Fail", request.amount(),
                            "Coupon can not bee applied for this order");
                }
                CouponHistory couponHistory = new CouponHistory();
                couponHistory.setCode(coupon.getCode());
                couponHistory.setOrderId(request.orderId());
                couponHistory.setValueBeforeDiscount(request.amount());
                couponHistory.setValueAfterDiscount(valueAfterDiscount);
                couponHistory.setConsumptionDate(Date.valueOf(LocalDate.now()));
                couponHistoryRepository.save(couponHistory);
                coupon.setAvailableUsages(coupon.getAvailableUsages()-1);
                couponRepository.save(coupon);



                return new CouponResponse("Success", valueAfterDiscount,
                        "this order is sufficient to get the coupon");
            }else{
                if(!coupon.isActive()){
                    return new CouponResponse("Fail", request.amount(), "this coupon " + request.couponCode() + " is not active");
                }
                if(coupon.getAvailableUsages() == 0){
                    Optional<Coupon> coupon1 = couponRepository.findByCode(request.couponCode());
                    if(coupon1.isPresent()){
                        Coupon coupon2 = coupon1.get();
                        coupon2.setActive(false);
                        couponRepository.save(coupon2);
                    }
                    return new CouponResponse("Fail", request.amount(),
                            "this coupon " + request.couponCode() + " is not available");
                }
            }
        }
        return new CouponResponse("Fail", request.amount() ,
                "this coupon " + request.couponCode()  + " is not existed");
    }
    public CouponResponse validate(CouponRequestBody request) {
        Optional<Coupon> requiredCoupon = couponRepository.findByCode(request.couponCode());
        if (requiredCoupon.isPresent()) {
            Coupon coupon = requiredCoupon.get();
            if (coupon.isActive() && coupon.getAvailableUsages() > 0) {
                double couponValue = coupon.getValue();
                double valueAfterDiscount;
                if(coupon.getType() == CouponType.PERCENTAGE){
                    valueAfterDiscount = request.amount() - (0.01 * couponValue * request.amount());
                }else
                    valueAfterDiscount = request.amount() - couponValue;

                if(valueAfterDiscount <= 100){
                    return new CouponResponse("Fail", request.amount(),
                            "Coupon can not bee applied for this order");
                }

                return new CouponResponse("Success", valueAfterDiscount,
                        "this order is sufficient to get the coupon");
            }
            else
            {
                if(!coupon.isActive()){
                    return new CouponResponse("Fail", request.amount(), "this coupon " + request.couponCode() + " is not active");
                }
                if(coupon.getAvailableUsages() == 0){
                    Optional<Coupon> coupon1 = couponRepository.findByCode(request.couponCode());
                    if(coupon1.isPresent()){
                        Coupon coupon2 = coupon1.get();
                        coupon2.setActive(false);
                        couponRepository.save(coupon2);
                    }
                    return new CouponResponse("Fail", request.amount(),
                            "this coupon " + request.couponCode() + " is not available");
                }
            }
        }
        return new CouponResponse("Fail", request.amount() ,
                "this coupon " + request.couponCode()  + " is not existed");
    }


    public CouponResponse unconsume(String orderId) {
        Optional<CouponHistory> requiredCouponHistory = couponHistoryRepository.findByOrderId(orderId);

        if(requiredCouponHistory.isEmpty()){
            return new CouponResponse("Fail", 0,
                    "History of this transactionId \"" + orderId + "\" does not exist");
        }
        else
        {
            CouponHistory couponHistory= requiredCouponHistory.get();
            Optional<Coupon> requiredCoupon = couponRepository.findByCode(couponHistory.getCode());
            if (requiredCoupon.isEmpty()) {
                double discount = couponHistory.getValueBeforeDiscount() - couponHistory.getValueAfterDiscount();
                CouponType couponType;
                int value = (int) discount;
                couponType = CouponType.FIXED;
                couponRepository.save(new Coupon(couponHistory.getCode(),
                        1,
                        1,
                        Date.valueOf(LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonthValue()+1, 1)),
                        couponType, true, value));
            }
            else
            {
                Coupon coupon = requiredCoupon.get();
                coupon.setActive(true);
                coupon.setAvailableUsages(coupon.getAvailableUsages()+1);
                couponRepository.save(coupon);
            }

            couponHistoryRepository.delete(couponHistory);
            return new CouponResponse("Success", 0, "this Coupon is unconsumed successfully");

        }
    }
}
