package com.example.workintech.ecomm.dto;

public record OrderProductsRequest(
        Long product_id,
        Integer count,
        String detail
) {
}
