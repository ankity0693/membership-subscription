package com.assignment.firstclub.membership.service;

import com.assignment.firstclub.common.data.CrudOperation;
import com.assignment.firstclub.common.data.Storage;
import com.assignment.firstclub.membership.model.PlanStatus;
import com.assignment.firstclub.membership.model.PlanType;
import com.assignment.firstclub.membership.model.entity.MembershipPlan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class PlanService extends CrudOperation<MembershipPlan> {
    @Autowired
    public PlanService(Storage storage) {
        super(storage, MembershipPlan.class);
    }

    public MembershipPlan addPlan(PlanType planType, BigDecimal price) {
        LocalDate expiryAt = LocalDate.now().plusMonths(planType.getDurationInMonths());
        MembershipPlan plan = MembershipPlan.builder().name(planType).price(price)
                .status(PlanStatus.ACTIVE).build();
        create(plan);
        return plan;
    }
}
