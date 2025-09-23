package com.example.workintech.ecomm.service;



import com.example.workintech.ecomm.dto.OrderRequest;
import com.example.workintech.ecomm.dto.OrderResponse;

import java.util.List;

public interface OrderService {
    List<OrderResponse> getOrders();
    OrderResponse save(OrderRequest orderRequest);
}
