package com.assignment.firstclub.user.exception;

import com.assignment.firstclub.common.exception.MembershipApplicationException;

public class UserNotFoundException extends MembershipApplicationException {
    public UserNotFoundException(Long userId) {
        super(String.format("User with id: %s not found", userId));
    }
}
