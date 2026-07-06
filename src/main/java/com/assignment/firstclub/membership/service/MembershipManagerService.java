package com.assignment.firstclub.membership.service;

import com.assignment.firstclub.membership.dto.request.SubscribeRequest;
import com.assignment.firstclub.membership.dto.response.MembershipPlanDetails;
import com.assignment.firstclub.membership.dto.response.SubscriptionResponse;
import com.assignment.firstclub.membership.exception.SubscriptionException;
import com.assignment.firstclub.membership.mapper.Mapper;
import com.assignment.firstclub.membership.model.BenefitUseDuring;
import com.assignment.firstclub.membership.model.entity.*;
import com.assignment.firstclub.user.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.ReentrantLock;

@Service
@AllArgsConstructor
@Slf4j
public class MembershipManagerService {

    PlanService planService;
    TierService tierService;
    BenefitService benefitService;
    SubscriptionService subscriptionService;
    Mapper mapper;
    UserService userService;
    UserLockManager userLockManager;


    public MembershipPlanDetails getPlanDetails() {
        List<MembershipPlan> plans = planService.getAll();
        List<Tier> tiers = tierService.getAll();
        List<TierBenefit> tierBenefits = benefitService.fetchAllActiveBenefits();
        return mapper.createPlanResponse(plans, tiers, tierBenefits);
    }

    public SubscriptionResponse subscribe(SubscribeRequest request) throws SubscriptionException {
        ReentrantLock lock = userLockManager.getLock(request.getUserId());
        lock.lock();
        try {
            userService.getUser(request.getUserId());
            MembershipPlan plan = planService.get(request.getPlanId());
            Tier tier = tierService.get(request.getTierId());
            subscriptionService.validateNoActiveSubscription(request.getUserId());
            Subscription subscription = mapper.formSubscription(request, plan, tier);
            subscriptionService.create(subscription);
            return mapper.getSubscriptionDetails(subscription, plan.getName(), tier.getTierType());
        } finally {
            lock.unlock();
        }
    }

    public SubscriptionResponse getSubscriptionDetails(Long userId) throws SubscriptionException {
        Optional<Subscription> subscription = subscriptionService.getActiveSubscription(userId);
        if(subscription.isPresent()) {
            Tier tier = tierService.get(subscription.get().getTierId());
            MembershipPlan plan = planService.get(subscription.get().getPlanId());
            return mapper.getSubscriptionDetails(subscription.get(), plan.getName(), tier.getTierType());
        }
        throw new SubscriptionException("User is not subscribed to any subscription");
    }

    public void cancelMembership(Long subscriptionId) {
        subscriptionService.cancelSubscription(subscriptionId);
    }


    public List<TierBenefit> getBenefits(Long userId, BenefitUseDuring useDuring) throws SubscriptionException {
        return benefitService.getBenefitsForTier(getSubscriptionDetails(userId).getTierDetails().getTierId())
                .stream().filter(benefit -> benefit.getUsedDuring().equals(useDuring)).toList();
    }
}
