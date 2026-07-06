package com.assignment.firstclub.order.dto.request;

import com.assignment.firstclub.order.dto.ItemDetails;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {

    @NotNull(message = "userId must not be null")
    Long userId;

    @NotEmpty(message = "Order must contain at least one item")
    List<ItemDetails> items;
}
