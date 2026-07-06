package com.assignment.firstclub.membership.benefits.strategy;

import com.assignment.firstclub.membership.dto.benefit.AppliedBenefit;
import com.assignment.firstclub.membership.dto.benefit.BenefitConfiguration;
import com.assignment.firstclub.membership.dto.benefit.BenefitContext;
import com.assignment.firstclub.membership.dto.benefit.BenefitResult;
import com.assignment.firstclub.membership.model.BenefitType;
import com.assignment.firstclub.membership.model.entity.TierBenefit;

public class EarlyAccessBenefit implements MemberBenefitStrategy{
    @Override
    public BenefitType getType() {
        return BenefitType.EARLY_ACCESS;
    }

    @Override
    public void apply(BenefitContext context,
                      TierBenefit configuration,
                      BenefitResult result) {

        result.addAppliedBenefit(
                AppliedBenefit.builder()
                        .type(BenefitType.EARLY_ACCESS)
                        .title("Eligible for Early Access Sales")
                        .build()
        );
    }
}
