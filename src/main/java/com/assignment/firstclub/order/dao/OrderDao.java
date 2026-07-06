package com.assignment.firstclub.order.dao;

import com.assignment.firstclub.common.data.CrudOperation;
import com.assignment.firstclub.common.data.Storage;
import com.assignment.firstclub.order.dto.OrderDetails;
import com.assignment.firstclub.order.model.Order;
import com.assignment.firstclub.order.model.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static com.assignment.firstclub.order.utils.OrderUtils.isBetween;

@Repository
public class OrderDao extends CrudOperation<Order> {

    @Autowired
    public OrderDao(Storage storage) {
        super(storage, Order.class);
    }

    public OrderDetails getOrderDetails(Long userId,
                                        LocalDate fromDate,
                                        LocalDate toDate) {
        List<Order> orders = getOrders(userId, fromDate, toDate);
        BigDecimal sumExpense = sumExpense(orders);
        return new OrderDetails(orders.size(), sumExpense, fromDate, toDate);
    }

    public List<Order> getOrders(Long userId,
                                 LocalDate fromDate,
                                 LocalDate toDate) {

        return getAll().stream()
                .filter(order -> order.getUserId().equals(userId))
                .filter(order -> isBetween(order.getCreatedAt().toLocalDate(), fromDate, toDate))
                .toList();
    }

    private BigDecimal sumExpense(List<Order> orders) {
        return orders.stream().filter(order -> !order.getStatus().equals(OrderStatus.FAILED))
                .map(Order::getSubtotal).reduce(BigDecimal.ZERO, BigDecimal::add);
    }


}
