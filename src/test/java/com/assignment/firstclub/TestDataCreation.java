package com.assignment.firstclub;

import com.assignment.firstclub.common.Constants;
import com.assignment.firstclub.membership.model.*;
import com.assignment.firstclub.membership.model.entity.TierBenefit;
import com.assignment.firstclub.membership.model.entity.TierRule;
import com.assignment.firstclub.membership.rule.impl.TierRuleEngineService;
import com.assignment.firstclub.membership.service.BenefitService;
import com.assignment.firstclub.membership.service.PlanService;
import com.assignment.firstclub.membership.service.TierService;
import com.assignment.firstclub.user.dto.UserCreateRequest;
import com.assignment.firstclub.user.service.UserService;

import java.math.BigDecimal;
import java.util.Map;

public class TestDataCreation {

    public static void createPlan(PlanService planService) {
        planService.addPlan(PlanType.MONTHLY, BigDecimal.valueOf(199));
        planService.addPlan(PlanType.QUARTERLY, BigDecimal.valueOf(399));
        planService.addPlan(PlanType.YEARLY, BigDecimal.valueOf(599));
    }

    public static void createTier(TierService tierService) {
        tierService.addTier(TierType.SILVER);
        tierService.addTier(TierType.GOLD);
        tierService.addTier(TierType.PLATINUM);
    }

    public static void createUser(UserService userService) {
        userService.createUser(UserCreateRequest.builder().name("JOHN").emailId("john@abc.com").build());
        userService.createUser(UserCreateRequest.builder().name("RAM").emailId("ram@abc.com").build());
        userService.createUser(UserCreateRequest.builder().name("JOE").emailId("joe@abc.com").build());
    }

    public static void createRule(TierRuleEngineService ruleEngineService) {
        ruleEngineService.create(TierRule.builder()
                        .value(3)
                        .type(RuleType.ORDER_COUNT)
                        .tierId(3L)
                .build());

        ruleEngineService.create(TierRule.builder()
                        .type(RuleType.ORDER_VALUE)
                        .value(5000)
                        .tierId(3L)
                .build());

        ruleEngineService.create(TierRule.builder()
                .value(2)
                .type(RuleType.ORDER_COUNT)
                .tierId(2L)
                .build());

        ruleEngineService.create(TierRule.builder()
                .type(RuleType.ORDER_VALUE)
                .value(2500)
                .tierId(2L)
                .build());

        ruleEngineService.create(TierRule.builder()
                .value(1)
                .type(RuleType.ORDER_COUNT)
                .tierId(1L)
                .build());

        ruleEngineService.create(TierRule.builder()
                .type(RuleType.ORDER_VALUE)
                .value(1000)
                .tierId(1L)
                .build());
    }

    public static void createBenefits(BenefitService benefitService) {
        benefitService.create(TierBenefit.builder()
                        .type(BenefitType.DISCOUNT)
                        .usedDuring(BenefitUseDuring.CHECKOUT)
                        .isActive(true)
                        .conditions(Map.of(
                        Constants.PERCENTAGE, "10",
                        Constants.CATEGORIES, "ELECTRONICS"))
                        .tierId(2L)
                .build());

        benefitService.create(TierBenefit.builder()
                .type(BenefitType.FREE_DELIVERY)
                .usedDuring(BenefitUseDuring.CHECKOUT)
                .isActive(true)
                .tierId(2L)
                .build());

        benefitService.create(TierBenefit.builder()
                .type(BenefitType.EARLY_ACCESS)
                .usedDuring(BenefitUseDuring.PRODUCT_SEARCH)
                .isActive(true)
                .tierId(2L)
                .build());
    }
}
