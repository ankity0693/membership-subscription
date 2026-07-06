package com.assignment.firstclub.membership.exception;

import com.assignment.firstclub.common.exception.ResourceNotFoundException;

public class BenefitNotFoundException extends ResourceNotFoundException {

    public BenefitNotFoundException(Long tierId) {
        super(String.format("No benefits found for tier with id %d", tierId));
    }
}
