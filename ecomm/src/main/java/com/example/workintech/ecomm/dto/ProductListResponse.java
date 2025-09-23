package com.example.workintech.ecomm.dto;

import java.util.List;

public record ProductListResponse(List<ProductResponse> products, Long total) {
}
