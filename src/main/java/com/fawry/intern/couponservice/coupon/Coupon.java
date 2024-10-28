package com.fawry.intern.couponservice.coupon;


import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;

@Entity
public class Coupon {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false, unique = true)
        String code;

        @Column
        private int numberOfUsages;
        @Column
        private int availableUsages;

        @Column
        private Date expiryDate;

        @Enumerated(EnumType.STRING)
        private CouponType type;

        @Column
        private boolean active;

        @Column
        private double value;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getNumberOfUsages() {
        return numberOfUsages;
    }

    public void setNumberOfUsages(int numberOfUsages) {
        this.numberOfUsages = numberOfUsages;
    }

    public int getAvailableUsages() {
        return availableUsages;
    }

    public void setAvailableUsages(int availableUsages) {
        this.availableUsages = availableUsages;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public CouponType getType() {
        return type;
    }

    public void setType(CouponType type) {
        this.type = type;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
