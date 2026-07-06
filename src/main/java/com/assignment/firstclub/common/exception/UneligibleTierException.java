package com.assignment.firstclub.common.exception;

public class UneligibleTierException extends MembershipApplicationException{
    public UneligibleTierException(String msg) {
        super(msg);
    }

    public UneligibleTierException(Long userId) {
        super(String.format("No eligible tier found for userId: %s", userId.toString()));
    }
}
