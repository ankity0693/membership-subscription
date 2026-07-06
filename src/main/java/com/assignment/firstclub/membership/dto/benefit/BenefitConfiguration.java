package com.assignment.firstclub.membership.dto.benefit;

import com.assignment.firstclub.membership.model.BenefitType;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class BenefitConfiguration {

    private Long benefitId;

    private BenefitType type;

    private Map<String, Object> config;
}
