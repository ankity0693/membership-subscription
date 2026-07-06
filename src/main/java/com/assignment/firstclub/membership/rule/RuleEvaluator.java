package com.assignment.firstclub.membership.rule;

import com.assignment.firstclub.membership.model.RuleType;
import com.assignment.firstclub.membership.model.entity.TierRule;

public interface RuleEvaluator {
    RuleType supports();

    boolean evaluate(UserMetrics userMetrics, TierRule rule);
}
