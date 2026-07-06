package com.assignment.firstclub.order.dto.request;

import com.assignment.firstclub.order.dto.ItemDetails;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {
    Long userId;
    List<ItemDetails> items;
}
