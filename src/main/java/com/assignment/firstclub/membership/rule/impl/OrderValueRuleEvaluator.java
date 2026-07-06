package com.assignment.firstclub.membership.rule.impl;

import com.assignment.firstclub.membership.rule.RuleEvaluator;
import com.assignment.firstclub.membership.rule.UserMetrics;
import com.assignment.firstclub.membership.model.RuleType;
import com.assignment.firstclub.membership.model.entity.TierRule;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class OrderValueRuleEvaluator implements RuleEvaluator {
    @Override
    public RuleType supports() {
        return RuleType.ORDER_VALUE;
    }

    @Override
    public boolean evaluate(UserMetrics userMetrics, TierRule rule) {
        return userMetrics.monthlySpend().compareTo(BigDecimal.valueOf(rule.getValue())) > 0;
    }
}
