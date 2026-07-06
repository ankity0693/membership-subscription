package com.assignment.firstclub.membership.benefits.strategy;

import com.assignment.firstclub.membership.dto.benefit.BenefitContext;
import com.assignment.firstclub.membership.dto.benefit.BenefitResult;
import com.assignment.firstclub.membership.model.entity.TierBenefit;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BenefitProcessor {

    private final BenefitFactory benefitFactory;

    public BenefitResult apply(BenefitContext context,
                               List<TierBenefit> benefits) {

        BenefitResult result = BenefitResult.builder().build();

        for (TierBenefit configuration : benefits) {

            MemberBenefitStrategy strategy =
                    benefitFactory.get(configuration.getType());

            strategy.apply(context, configuration, result);
        }

        return result;
    }
}
