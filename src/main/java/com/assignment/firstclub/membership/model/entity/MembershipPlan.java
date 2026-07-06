package com.assignment.firstclub.membership.model.entity;

import com.assignment.firstclub.common.data.BaseEntity;
import com.assignment.firstclub.membership.model.PlanStatus;
import com.assignment.firstclub.membership.model.PlanType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MembershipPlan implements BaseEntity {
    Long id;
    PlanType name;
    BigDecimal price;
    PlanStatus status;
}
