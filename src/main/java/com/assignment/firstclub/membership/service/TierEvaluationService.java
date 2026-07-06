package com.assignment.firstclub.membership.service;

import com.assignment.firstclub.common.exception.MembershipApplicationException;
import com.assignment.firstclub.membership.exception.SubscriptionException;
import com.assignment.firstclub.membership.model.entity.Subscription;
import com.assignment.firstclub.membership.model.entity.Tier;
import com.assignment.firstclub.membership.rule.impl.TierRuleEngineService;
import com.assignment.firstclub.order.model.Order;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TierEvaluationService {

    TierRuleEngineService tierRuleEngine;
    SubscriptionService subscriptionService;

    TierService tierService;

    public TierEvaluationService(TierRuleEngineService tierRuleEngineService, SubscriptionService subscriptionService, TierService tierService) {
        this.tierRuleEngine = tierRuleEngineService;
        this.subscriptionService = subscriptionService;
        this.tierService = tierService;

    }

    public void evaluateTierAndUpgradeTier(Order order) throws MembershipApplicationException {
        Tier tier =  tierRuleEngine.evaluateTier(order.getUserId());
        Optional<Subscription> subscription = subscriptionService.getActiveSubscription(order.getUserId());
        if(subscription.isPresent() && !subscription.get().getTierId().equals(tier.getId())) {
            changeTier(subscription.get().getId(), tier.getId());
        }
    }

    public void changeTier(Long subscriptionId, Long tierId) throws SubscriptionException {
        subscriptionService.updateTier(subscriptionId, tierId);
    }

    public void upgradeTier(Long subscriptionId) throws MembershipApplicationException {
        changeTier(subscriptionId, true);
    }

    public void downgradeTier(Long subscriptionId) throws MembershipApplicationException {
        changeTier(subscriptionId, false);
    }

    private void changeTier(Long subscriptionId, boolean upgrade)
            throws MembershipApplicationException {

        Subscription subscription = subscriptionService.get(subscriptionId);

        Tier tier = upgrade
                ? tierService.getNextUpgraded(subscription.getTierId())
                : tierService.getNextDowngraded(subscription.getTierId());
    }

}
