package com.assignment.firstclub.membership.exception;

import com.assignment.firstclub.common.exception.ResourceNotFoundException;

public class PlanNotFoundException extends ResourceNotFoundException {

    public PlanNotFoundException(Long planId) {
        super("MembershipPlan", planId);
    }
}
