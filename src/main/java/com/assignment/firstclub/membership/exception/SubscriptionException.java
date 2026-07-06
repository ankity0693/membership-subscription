package com.assignment.firstclub.membership.exception;

import com.assignment.firstclub.common.exception.MembershipApplicationException;

public class SubscriptionException extends MembershipApplicationException {
    public SubscriptionException(String msg) {
        super(msg);
    }
}
