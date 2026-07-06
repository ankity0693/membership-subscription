package com.assignment.firstclub.order.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemDetails {
    Long itemId;
    Integer quantity;
    BigDecimal unitPrice;
}
