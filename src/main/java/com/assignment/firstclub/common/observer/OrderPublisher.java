package com.assignment.firstclub.common.observer;

import com.assignment.firstclub.common.exception.MembershipApplicationException;
import com.assignment.firstclub.order.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderPublisher {

    public List<OrderObserver> orderObservers;

    @Autowired
    public OrderPublisher (List<OrderObserver> orderObservers) {
        this.orderObservers = orderObservers;
    }

    public void notifyObservers(Order order) {
        orderObservers.forEach(orderObserver -> {
            try {
                orderObserver.onOrderPlaced(order);
            } catch (MembershipApplicationException e) {
                throw new RuntimeException(e);
            }
        });
    }

}
