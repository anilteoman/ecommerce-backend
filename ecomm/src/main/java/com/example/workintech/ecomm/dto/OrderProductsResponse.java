package com.example.workintech.ecomm.dto;

import java.util.List;

public record OrderProductsResponse(Long id, Integer count, String name, String description, Double price, List<ProductImagesResponse> images) {
}
