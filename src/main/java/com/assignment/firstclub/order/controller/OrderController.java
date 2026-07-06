package com.assignment.firstclub.order.controller;

import com.assignment.firstclub.order.dto.request.OrderRequest;
import com.assignment.firstclub.order.model.Order;
import com.assignment.firstclub.order.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.assignment.firstclub.common.Constants.API_VERSION_V1;

@RestController
@RequestMapping(API_VERSION_V1 + "/orders")
@RequiredArgsConstructor
@Tag(name = "Orders", description = "Order management APIs")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @Operation(summary = "Place an order")
    public ResponseEntity<Order> placeOrder(@RequestBody OrderRequest request) {
        return ResponseEntity.ok(orderService.placeOrder(request));
    }

    @PatchMapping("/{orderId}/confirm")
    @Operation(summary = "Confirm an order")
    public ResponseEntity<Void> confirmOrder(@PathVariable Long orderId) {
        orderService.confirmOrder(orderId);
        return ResponseEntity.ok().build();
    }
}