package com.assignment.firstclub.order.updates;

import com.assignment.firstclub.common.observer.OrderPublisher;
import com.assignment.firstclub.order.model.Order;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
public class OrderNotificationService {

    OrderPublisher orderPublisher;

    public OrderNotificationService(OrderPublisher orderPublisher) {
        this.orderPublisher = orderPublisher;
    }


    public void orderPlaced(Order order) {
        orderPublisher.notifyObservers(order);
    }
}
