package com.assignment.firstclub.order.exception;

import com.assignment.firstclub.common.exception.ResourceNotFoundException;

public class OrderNotFoundException extends ResourceNotFoundException {

    public OrderNotFoundException(Long orderId) {
        super("Order", orderId);
    }
}
