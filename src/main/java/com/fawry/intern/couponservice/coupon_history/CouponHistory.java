package com.fawry.intern.couponservice.coupon_history;



import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;

@Entity
@Table(name = "coupon_history")
public class CouponHistory {

    public CouponHistory() {

    }
    public CouponHistory(int id,
                         String code,
                         String orderId,
                         double valueBeforeDiscount,
                         double valueAfterDiscount,
                         Date consumptionDate) {
        this.id = id;
        this.code = code;
        this.orderId = orderId;
        this.valueBeforeDiscount = valueBeforeDiscount;
        this.valueAfterDiscount = valueAfterDiscount;
        this.consumptionDate = consumptionDate;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String code;

    @Column(name = "order_id", nullable = false, unique = true)
    private String orderId;

    @Column(nullable = false, name = "value_before_discount")
    private double valueBeforeDiscount;

    @Column(nullable = false, name = "value_after_discount")

    private double valueAfterDiscount;

    @Column(name = "consumption_date")
    @Temporal(TemporalType.DATE)
    private Date consumptionDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public double getValueBeforeDiscount() {
        return valueBeforeDiscount;
    }

    public void setValueBeforeDiscount(double valueBeforeDiscount) {
        this.valueBeforeDiscount = valueBeforeDiscount;
    }

    public double getValueAfterDiscount() {
        return valueAfterDiscount;
    }

    public void setValueAfterDiscount(double valueAfterDiscount) {
        this.valueAfterDiscount = valueAfterDiscount;
    }

    public Date getConsumptionDate() {
        return consumptionDate;
    }

    public void setConsumptionDate(Date consumptionDate) {
        this.consumptionDate = consumptionDate;
    }
}
