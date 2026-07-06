package com.assignment.firstclub.order.service;

import com.assignment.firstclub.membership.dto.benefit.BenefitResult;
import com.assignment.firstclub.order.model.Order;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class OrderBenefitApplier {

    public void applyBenefits(Order order, BenefitResult result) {
        BigDecimal subtotal = order.getSubtotal();

        subtotal = subtotal.subtract(result.getItemDiscount());

        BigDecimal shipping = Optional.ofNullable(order.getShippingCharge()).orElse(BigDecimal.ZERO);

        shipping = shipping.subtract(result.getShippingDiscount());

        order.setShippingCharge(shipping);
        order.setDiscount(result.getItemDiscount());
        order.setTotalPrice(subtotal.add(shipping));

        order.setAppliedBenefits(result.getAppliedBenefits());
    }
}
