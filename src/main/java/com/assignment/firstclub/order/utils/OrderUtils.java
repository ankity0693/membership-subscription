package com.assignment.firstclub.order.utils;

import com.assignment.firstclub.order.dto.request.OrderRequest;
import com.assignment.firstclub.order.model.Order;
import com.assignment.firstclub.order.model.OrderStatus;

import java.time.LocalDate;

public class OrderUtils {

    public static Order toOrder(OrderRequest orderRequest) {
        return Order.builder()
                .userId(orderRequest.getUserId())
                .status(OrderStatus.PENDING)
                .itemDetails(orderRequest.getItems())
                .build();

    }

    public static boolean isBetween(LocalDate date,
                              LocalDate from,
                              LocalDate to) {
        return !date.isBefore(from) && !date.isAfter(to);
    }


}
