package com.assignment.firstclub.membership.dto.response;

import com.assignment.firstclub.membership.model.TierType;
import com.assignment.firstclub.membership.model.entity.TierBenefit;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TierBenefitResponse {
    Long tierId;
    TierType tierType;
    private List<TierBenefit> benefits;
}
