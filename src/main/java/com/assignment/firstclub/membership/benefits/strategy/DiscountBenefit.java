package com.assignment.firstclub.membership.benefits.strategy;

import com.assignment.firstclub.common.Constants;
import com.assignment.firstclub.membership.dto.benefit.AppliedBenefit;
import com.assignment.firstclub.membership.dto.benefit.BenefitContext;
import com.assignment.firstclub.membership.dto.benefit.BenefitResult;
import com.assignment.firstclub.membership.model.BenefitType;
import com.assignment.firstclub.membership.model.entity.TierBenefit;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DiscountBenefit implements MemberBenefitStrategy {

    @Override
    public BenefitType getType() {
        return BenefitType.DISCOUNT;
    }

    @Override
    public void apply(BenefitContext context, TierBenefit configuration, BenefitResult result) {
        Integer percentage =
                 Integer.parseInt(configuration.getConditions().get(Constants.PERCENTAGE));

        BigDecimal discount = context.getOrder().getSubtotal()
                .multiply(BigDecimal.valueOf(percentage))
                .divide(BigDecimal.valueOf(100));

        result.setItemDiscount(discount.add(result.getItemDiscount()));

        result.addAppliedBenefit(
                AppliedBenefit.builder()
                        .type(BenefitType.DISCOUNT)
                        .title("Membership Discount")
                        .amount(discount)
                        .build()
        );
    }

}
