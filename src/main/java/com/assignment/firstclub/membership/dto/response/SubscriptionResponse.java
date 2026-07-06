package com.assignment.firstclub.membership.dto.response;

import com.assignment.firstclub.membership.model.PlanType;
import com.assignment.firstclub.membership.model.SubscriptionStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class SubscriptionResponse {
    private Long subscriptionId;
    private Long userId;
    private PlanType planType;
    private BigDecimal price;
    private LocalDate startDate;
    private LocalDate expiryDate;
    private SubscriptionStatus status;
    private TierBenefitResponse tierDetails;
}
