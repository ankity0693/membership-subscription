package com.assignment.firstclub.membership.model.entity;

import com.assignment.firstclub.common.data.BaseEntity;
import com.assignment.firstclub.membership.model.PlanType;
import com.assignment.firstclub.membership.model.SubscriptionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Subscription implements BaseEntity {
    Long id;
    Long planId;
    Long userId;
    Long tierId;
    BigDecimal price;
    SubscriptionStatus status;
    LocalDate startDate;
    LocalDate expiryDate;
}
