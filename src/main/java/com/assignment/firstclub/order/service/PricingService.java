package com.assignment.firstclub.order.service;

import com.assignment.firstclub.order.dto.ItemDetails;
import com.assignment.firstclub.order.model.Order;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PricingService {

    public void computePrice(Order order) {
        List<ItemDetails> itemDetails = order.getItemDetails();
        BigDecimal totalPrice = BigDecimal.ZERO;
        for(ItemDetails item : itemDetails) {
            totalPrice = totalPrice.add(item.getUnitPrice()
                    .multiply(BigDecimal.valueOf(item.getQuantity())));
        }
        order.setSubtotal(totalPrice);
    }
}
