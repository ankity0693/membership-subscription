package com.assignment.firstclub.membership.benefits.strategy;

import com.assignment.firstclub.membership.dto.benefit.BenefitConfiguration;
import com.assignment.firstclub.membership.dto.benefit.BenefitContext;
import com.assignment.firstclub.membership.dto.benefit.BenefitResult;
import com.assignment.firstclub.membership.model.BenefitType;
import com.assignment.firstclub.membership.model.entity.TierBenefit;

public interface MemberBenefitStrategy {
    public BenefitType getType() ;

    void apply(BenefitContext context, TierBenefit configuration, BenefitResult result);
}
