package com.assignment.firstclub.membership.observer;

import com.assignment.firstclub.common.exception.MembershipApplicationException;
import com.assignment.firstclub.membership.service.TierEvaluationService;
import com.assignment.firstclub.order.model.Order;
import com.assignment.firstclub.common.observer.OrderObserver;
import com.assignment.firstclub.membership.service.MembershipManagerService;
import org.springframework.stereotype.Service;

@Service
public class MembershipOrderObserver implements OrderObserver {

    TierEvaluationService tierEvaluationService;
    public MembershipOrderObserver(TierEvaluationService tierEvaluationService) {
        this.tierEvaluationService = tierEvaluationService;
    }
    @Override
    public void onOrderPlaced(Order order) throws MembershipApplicationException {
        tierEvaluationService.evaluateTierAndUpgradeTier(order);
    }
}
