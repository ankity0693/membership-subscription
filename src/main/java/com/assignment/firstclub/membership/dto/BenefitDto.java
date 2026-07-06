package com.assignment.firstclub.membership.dto;

import com.assignment.firstclub.membership.model.BenefitType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BenefitDto {
    BenefitType type;
    String value;
}
