package com.assignment.firstclub.membership.dto.benefit;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class BenefitResult {

    @Builder.Default
    private BigDecimal itemDiscount = BigDecimal.ZERO;

    @Builder.Default
    private BigDecimal shippingDiscount = BigDecimal.ZERO;

    @Builder.Default
    private List<AppliedBenefit> appliedBenefits = new ArrayList<>();

    @Builder.Default
    private List<String> unlockedCoupons = new ArrayList<>();

    @Builder.Default
    private boolean earlyAccess = false;

    @Builder.Default
    private boolean prioritySupport = false;

    /**
     * Total savings from membership.
     */
    public BigDecimal getTotalSavings() {
        return itemDiscount.add(shippingDiscount);
    }

    public void addItemDiscount(BigDecimal amount) {
        this.itemDiscount = this.itemDiscount.add(amount);
    }

    public void addShippingDiscount(BigDecimal amount) {
        this.shippingDiscount = this.shippingDiscount.add(amount);
    }

    public void addAppliedBenefit(AppliedBenefit benefit) {
        this.appliedBenefits.add(benefit);
    }

    public void addCoupon(String coupon) {
        this.unlockedCoupons.add(coupon);
    }
}
