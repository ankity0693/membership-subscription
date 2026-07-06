package com.assignment.firstclub.membership.model.entity;

import com.assignment.firstclub.common.data.BaseEntity;
import com.assignment.firstclub.membership.model.BenefitType;
import com.assignment.firstclub.membership.model.BenefitUseDuring;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TierBenefit implements BaseEntity {
    Long id;
    Long tierId;
    BenefitType type;
    Map<String, String> conditions;
    BenefitUseDuring usedDuring;
    Boolean isActive;
}
