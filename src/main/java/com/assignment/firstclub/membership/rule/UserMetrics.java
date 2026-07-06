package com.assignment.firstclub.membership.rule;

import java.math.BigDecimal;
import java.util.Set;

public record UserMetrics(
        Long userId,
        Integer orderCount,
        BigDecimal monthlySpend,
        Set<Long> cohortId
) {}
