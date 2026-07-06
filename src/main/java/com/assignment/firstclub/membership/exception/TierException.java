package com.assignment.firstclub.membership.exception;

import com.assignment.firstclub.common.exception.MembershipApplicationException;

public class TierException extends MembershipApplicationException {
    public TierException(String msg) {
        super(msg);
    }
}
