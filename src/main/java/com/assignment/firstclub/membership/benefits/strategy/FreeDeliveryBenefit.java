package com.assignment.firstclub.membership.benefits.strategy;

import com.assignment.firstclub.membership.dto.benefit.AppliedBenefit;
import com.assignment.firstclub.membership.dto.benefit.BenefitContext;
import com.assignment.firstclub.membership.dto.benefit.BenefitResult;
import com.assignment.firstclub.membership.model.BenefitType;
import com.assignment.firstclub.membership.model.entity.TierBenefit;

import java.math.BigDecimal;
import java.util.Optional;

public class FreeDeliveryBenefit implements  MemberBenefitStrategy{
    @Override
    public BenefitType getType() {
        return BenefitType.FREE_DELIVERY;
    }

    @Override
    public void apply(BenefitContext context,
                      TierBenefit configuration,
                      BenefitResult result) {

        result.setShippingDiscount(Optional.ofNullable(context.getOrder().getShippingCharge()).orElse(BigDecimal.ZERO));

        result.addAppliedBenefit(
                AppliedBenefit.builder()
                        .type(BenefitType.FREE_DELIVERY)
                        .title("Free Delivery")
                        .amount(context.getOrder().getShippingCharge())
                        .build()
        );
    }
}
