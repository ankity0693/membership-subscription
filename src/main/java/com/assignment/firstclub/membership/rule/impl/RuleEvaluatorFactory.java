package com.assignment.firstclub.membership.rule.impl;

import com.assignment.firstclub.membership.rule.RuleEvaluator;
import com.assignment.firstclub.membership.model.RuleType;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class RuleEvaluatorFactory {

    private Map<RuleType, RuleEvaluator> ruleEvaluatorMap;
    public RuleEvaluatorFactory(List<RuleEvaluator> ruleEvaluators) {
        ruleEvaluatorMap = ruleEvaluators.stream().collect(Collectors.toMap(RuleEvaluator::supports, Function.identity()));
    }

    public RuleEvaluator getRuleEvaluator(RuleType ruleType) {
        return ruleEvaluatorMap.get(ruleType);
    }



}
