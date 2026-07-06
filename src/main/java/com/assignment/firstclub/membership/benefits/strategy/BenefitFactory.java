package com.assignment.firstclub.membership.benefits.strategy;

import com.assignment.firstclub.membership.model.BenefitType;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class BenefitFactory {

    private final Map<BenefitType, MemberBenefitStrategy> strategyMap;

    public BenefitFactory(List<MemberBenefitStrategy> strategies) {

        this.strategyMap = strategies.stream()
                .collect(Collectors.toMap(
                        MemberBenefitStrategy::getType,
                        Function.identity()
                ));
    }

    public MemberBenefitStrategy get(BenefitType type) {

        MemberBenefitStrategy strategy = strategyMap.get(type);

        if (strategy == null) {
            throw new IllegalArgumentException(
                    "Unsupported benefit : " + type);
        }

        return strategy;
    }
}
