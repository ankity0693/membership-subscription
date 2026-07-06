package com.assignment.firstclub.common.observer;

import com.assignment.firstclub.common.exception.MembershipApplicationException;
import com.assignment.firstclub.order.model.Order;

public interface OrderObserver {

    public void onOrderPlaced(Order order) throws MembershipApplicationException;
}
