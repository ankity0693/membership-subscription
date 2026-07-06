package com.assignment.firstclub;

import com.assignment.firstclub.common.data.Storage;
import com.assignment.firstclub.common.observer.OrderPublisher;
import com.assignment.firstclub.membership.benefits.strategy.*;
import com.assignment.firstclub.membership.dto.request.SubscribeRequest;
import com.assignment.firstclub.membership.dto.response.SubscriptionResponse;
import com.assignment.firstclub.membership.exception.SubscriptionException;
import com.assignment.firstclub.membership.mapper.Mapper;
import com.assignment.firstclub.membership.model.BenefitType;
import com.assignment.firstclub.membership.observer.MembershipOrderObserver;
import com.assignment.firstclub.membership.rule.impl.*;
import com.assignment.firstclub.membership.service.*;
import com.assignment.firstclub.order.dao.OrderDao;
import com.assignment.firstclub.order.dto.ItemDetails;
import com.assignment.firstclub.order.dto.request.OrderRequest;
import com.assignment.firstclub.order.model.Order;
import com.assignment.firstclub.order.service.OrderBenefitApplier;
import com.assignment.firstclub.order.service.OrderService;
import com.assignment.firstclub.order.service.PricingService;
import com.assignment.firstclub.order.updates.OrderNotificationService;
import com.assignment.firstclub.user.model.User;
import com.assignment.firstclub.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoveTierBasedOnCriteria {

    private PlanService planService;
    private TierService tierService;
    private UserService userService;
    private BenefitService benefitService;
    private SubscriptionService subscriptionService;
    private MembershipManagerService membershipManagerService;
    private TierRuleEngineService tierRuleEngineService;
    private OrderService orderService;
    private OrderNotificationService orderNotificationService;
    private OrderPublisher orderPublisher;
    private MembershipOrderObserver membershipOrderObserver;
    private CohortManagerService cohortManagerService;
    private TierEvaluationService tierEvaluationService;
    private OrderDao orderDao;

    private OrderBenefitApplier orderBenefitApplier;
    private BenefitProcessor benefitProcessor;

    private TestDataCreation dataCreation;

    @BeforeEach
    public void setup() {
        Storage storage = new Storage();
        this.orderDao = new OrderDao(storage);
        this.planService = new PlanService(storage);
        this.tierService = new TierService(storage);
        this.userService = new UserService(storage);
        this.benefitService = new BenefitService(storage);
        this.subscriptionService = new SubscriptionService(storage);
        this.cohortManagerService = new CohortManagerService(storage);
        this.orderBenefitApplier = new OrderBenefitApplier();
        this.benefitProcessor = new BenefitProcessor(new BenefitFactory(List.of(new DiscountBenefit(), new FreeDeliveryBenefit(), new EarlyAccessBenefit())));

        this.tierRuleEngineService = new TierRuleEngineService(storage, tierService, orderDao, cohortManagerService, getRuleEvaluatorFactory());
        this.tierEvaluationService = new TierEvaluationService(tierRuleEngineService, subscriptionService, tierService);
        this.membershipOrderObserver = new MembershipOrderObserver(tierEvaluationService);
        this.orderPublisher = new OrderPublisher(List.of(membershipOrderObserver));
        this.orderNotificationService = new OrderNotificationService(orderPublisher);
        this.membershipManagerService = new MembershipManagerService(planService, tierService, benefitService, subscriptionService, new Mapper(), userService, new UserLockManager());
        this.orderService = new OrderService(orderNotificationService, new PricingService(), orderDao, membershipManagerService, orderBenefitApplier, benefitProcessor);


        TestDataCreation.createPlan(planService);
        TestDataCreation.createTier(tierService);
        TestDataCreation.createRule(tierRuleEngineService);
        TestDataCreation.createUser(userService);
        TestDataCreation.createBenefits(benefitService);

    }

    public RuleEvaluatorFactory getRuleEvaluatorFactory() {
        CohortRuleEvaluator cohortRuleEvaluator = new CohortRuleEvaluator();
        OrderValueRuleEvaluator orderValueRuleEvaluator = new OrderValueRuleEvaluator();
        OrderCountRuleEvaluator orderCountRuleEvaluator = new OrderCountRuleEvaluator();
        return new RuleEvaluatorFactory(List.of(cohortRuleEvaluator, orderValueRuleEvaluator, orderCountRuleEvaluator));
    }

    @Test
    public void testPlacingOrder() throws SubscriptionException {
        User user = userService.get(1L);
        SubscriptionResponse subscriptionResponse = membershipManagerService.subscribe(SubscribeRequest.builder()
                .tierId(1L)
                .planId(1L)
                .userId(user.getId())
                .build());

        SubscriptionResponse beforePlacingOrder = membershipManagerService.getSubscriptionDetails(user.getId());

        assertEquals(1L, beforePlacingOrder.getTierDetails().getTierId());

        orderService.placeOrder(OrderRequest.builder()
                .userId(user.getId())
                .items(List.of(ItemDetails.builder()
                        .quantity(5)
                        .unitPrice(BigDecimal.valueOf(1000))
                        .build()))
                .build());

        SubscriptionResponse afterPlacingOrder = membershipManagerService.getSubscriptionDetails(user.getId());

        assertEquals(1L, afterPlacingOrder.getTierDetails().getTierId());

        orderService.placeOrder(OrderRequest.builder()
                .userId(user.getId())
                .items(List.of(ItemDetails.builder()
                        .quantity(5)
                        .unitPrice(BigDecimal.valueOf(1000))
                        .build()))
                .build());

        SubscriptionResponse afterPlacingSecondOrder = membershipManagerService.getSubscriptionDetails(user.getId());

        assertEquals(2L, afterPlacingSecondOrder.getTierDetails().getTierId());

        Order order = orderService.placeOrder(OrderRequest.builder()
                .userId(user.getId())
                .items(List.of(ItemDetails.builder()
                        .quantity(5)
                        .unitPrice(BigDecimal.valueOf(1000))
                        .build()))
                .build());

        assertEquals(2, order.getAppliedBenefits().size());
        assertEquals(BigDecimal.valueOf(4500), order.getTotalPrice());
        assertEquals(BigDecimal.valueOf(5000), order.getSubtotal());
        assertEquals(BenefitType.DISCOUNT, order.getAppliedBenefits().get(0).getType());
        assertEquals(BenefitType.FREE_DELIVERY, order.getAppliedBenefits().get(1).getType());
    }


}
