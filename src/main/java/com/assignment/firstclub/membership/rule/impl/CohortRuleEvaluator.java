package com.assignment.firstclub.membership.rule.impl;

import com.assignment.firstclub.membership.rule.RuleEvaluator;
import com.assignment.firstclub.membership.rule.UserMetrics;
import com.assignment.firstclub.membership.model.RuleType;
import com.assignment.firstclub.membership.model.entity.TierRule;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class CohortRuleEvaluator implements RuleEvaluator {
    @Override
    public RuleType supports() {
        return RuleType.COHORT;
    }

    @Override
    public boolean evaluate(UserMetrics userMetrics, TierRule rule) {
        Set<Long> cohortUserPresent = userMetrics.cohortId();
        for(Long cohortId : cohortUserPresent) {
            if(rule.getCohortIds().contains(cohortId)) {
                return true;
            }
        }
        return false;
    }
}
