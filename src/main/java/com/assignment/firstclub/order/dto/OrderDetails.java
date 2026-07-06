package com.assignment.firstclub.order.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record OrderDetails(Integer ordersCount, BigDecimal expense, LocalDate fromDate, LocalDate toDate) {
}
