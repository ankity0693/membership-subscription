package com.assignment.firstclub.membership.dto.benefit;

import com.assignment.firstclub.order.dto.ItemDetails;
import com.assignment.firstclub.order.model.Order;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Value
@Builder
public class BenefitContext {
    Long tierId;
    Order order;
}
