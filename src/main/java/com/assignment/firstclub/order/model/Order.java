package com.assignment.firstclub.order.model;

import com.assignment.firstclub.common.data.BaseEntity;
import com.assignment.firstclub.membership.dto.benefit.AppliedBenefit;
import com.assignment.firstclub.order.dto.ItemDetails;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Order implements BaseEntity {
    Long id;
    Long userId;
    List<ItemDetails> itemDetails;
    BigDecimal subtotal;
    BigDecimal discount;
    BigDecimal totalPrice;
    BigDecimal shippingCharge;
    OrderStatus status;
    LocalDateTime createdAt;
    List<AppliedBenefit> appliedBenefits;
}
