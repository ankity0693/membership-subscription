package com.assignment.firstclub.membership.mapper;

import com.assignment.firstclub.membership.dto.request.SubscribeRequest;
import com.assignment.firstclub.membership.dto.response.MembershipPlanDetails;
import com.assignment.firstclub.membership.dto.response.SubscriptionResponse;
import com.assignment.firstclub.membership.dto.response.TierBenefitResponse;
import com.assignment.firstclub.membership.model.PlanType;
import com.assignment.firstclub.membership.model.SubscriptionStatus;
import com.assignment.firstclub.membership.model.TierType;
import com.assignment.firstclub.membership.model.entity.MembershipPlan;
import com.assignment.firstclub.membership.model.entity.Subscription;
import com.assignment.firstclub.membership.model.entity.Tier;
import com.assignment.firstclub.membership.model.entity.TierBenefit;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class Mapper {

    public Subscription formSubscription(SubscribeRequest request, MembershipPlan plan, Tier tier) {
        return Subscription.builder()
                .planId(request.getPlanId())
                .userId(request.getUserId())
                .tierId(request.getTierId())
                .status(SubscriptionStatus.ACTIVE)
                .startDate(LocalDate.now())
                .tierId(tier.getId())
                .price(plan.getPrice())
                .expiryDate(LocalDate.now().plusMonths(plan.getName().getDurationInMonths()))
                .build();
    }

    public SubscriptionResponse getSubscriptionDetails(Subscription subscription, PlanType type, TierType tierType) {
        return SubscriptionResponse.builder()
                .subscriptionId(subscription.getId())
                .userId(subscription.getUserId())
                .startDate(subscription.getStartDate())
                .expiryDate(subscription.getExpiryDate())
                .status(subscription.getStatus())
                .price(subscription.getPrice())
                .planType(type)
                .tierDetails(TierBenefitResponse.builder().tierId(subscription.getTierId()).tierType(tierType).build())
                .build();
    }

    public MembershipPlanDetails createPlanResponse(List<MembershipPlan> plans, List<Tier> tiers, List<TierBenefit> tierBenefits) {
        MembershipPlanDetails planDetails = MembershipPlanDetails.builder().plans(plans).build();
        planDetails.setTierInfo(formTierBenefitResponse(tierBenefits, tiers));
        return planDetails;
    }

    private List<TierBenefitResponse> formTierBenefitResponse(List<TierBenefit> tierBenefits, List<Tier> tiers) {
        Map<Long, TierType> tierTypeMap = tiers.stream()
                .collect(Collectors.toMap(
                        Tier::getId,
                        Tier::getTierType
                ));

        Map<Long, List<TierBenefit>> tierBenefitMap = new HashMap<>();

        for(TierBenefit benefit : tierBenefits) {
            Long tierId = benefit.getTierId();
            tierBenefitMap.computeIfAbsent(tierId, key -> new ArrayList<>()).add(benefit);
        }

        List<TierBenefitResponse> tierBenefitResponses = new ArrayList<>();

        tierTypeMap.forEach((k,v) -> {
            tierBenefitResponses.add(TierBenefitResponse.builder()
                            .tierType(v)
                            .tierId(k)
                            .benefits(tierBenefitMap.get(k))
                    .build());
        });

        return tierBenefitResponses;
    }
}
