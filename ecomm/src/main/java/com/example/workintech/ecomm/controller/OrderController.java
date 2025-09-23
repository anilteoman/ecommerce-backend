package com.example.workintech.ecomm.controller;


import com.example.workintech.ecomm.dto.OrderRequest;
import com.example.workintech.ecomm.dto.OrderResponse;
import com.example.workintech.ecomm.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/order")
    public List<OrderResponse> getOrders() {
        return orderService.getOrders();
    }

    @PostMapping("/order")
    public OrderResponse save(@Validated @RequestBody OrderRequest orderRequest) {
        return orderService.save(orderRequest);
    }
}
