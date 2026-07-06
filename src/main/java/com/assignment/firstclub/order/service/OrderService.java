package com.assignment.firstclub.order.service;

import com.assignment.firstclub.membership.benefits.strategy.BenefitProcessor;
import com.assignment.firstclub.membership.dto.benefit.BenefitContext;
import com.assignment.firstclub.membership.dto.benefit.BenefitResult;
import com.assignment.firstclub.membership.exception.SubscriptionException;
import com.assignment.firstclub.membership.model.BenefitUseDuring;
import com.assignment.firstclub.membership.model.entity.TierBenefit;
import com.assignment.firstclub.membership.service.MembershipManagerService;
import com.assignment.firstclub.order.dao.OrderDao;
import com.assignment.firstclub.order.model.Order;
import com.assignment.firstclub.order.dto.request.OrderRequest;
import com.assignment.firstclub.order.model.OrderStatus;
import com.assignment.firstclub.order.updates.OrderNotificationService;
import com.assignment.firstclub.order.utils.OrderUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class OrderService {
    OrderNotificationService orderNotificationService;
    PricingService pricingService;
    OrderDao orderDao;
    MembershipManagerService membershipManagerService;
    OrderBenefitApplier benefitApplier;
    BenefitProcessor benefitProcessor;

    public Order placeOrder(OrderRequest request) {
        Order order = OrderUtils.toOrder(request);
        pricingService.computePrice(order);
        order.setCreatedAt(LocalDateTime.now());
        applyBenefits(order);
        orderDao.create(order);
        orderNotificationService.orderPlaced(order);
        return order;
    }

    private void applyBenefits(Order order) {
        try {
            List<TierBenefit> benefits = getBenefits(order.getUserId());
            if(!benefits.isEmpty()) {
                BenefitContext context = BenefitContext.builder().order(order).tierId(benefits.getFirst().getTierId()).build();
                BenefitResult result = benefitProcessor.apply(context, benefits);
                benefitApplier.applyBenefits(order, result);
            }
        } catch (Exception e) {
            log.error("Exception while applying benefits: {}", e.getMessage());
        }
    }

    public List<TierBenefit> getBenefits(Long userId) throws SubscriptionException {
       return membershipManagerService.getBenefits(userId, BenefitUseDuring.CHECKOUT);
    }

    public void confirmOrder(Long id) {
        Order order = orderDao.get(id);
        order.setStatus(OrderStatus.SUCCESS);
        orderDao.update(order);
    }
}
