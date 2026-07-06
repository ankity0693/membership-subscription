package com.assignment.firstclub.membership.dto.response;

import com.assignment.firstclub.membership.model.entity.MembershipPlan;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class MembershipPlanDetails {
    List<MembershipPlan> plans;
    List<TierBenefitResponse> tierInfo;
}
