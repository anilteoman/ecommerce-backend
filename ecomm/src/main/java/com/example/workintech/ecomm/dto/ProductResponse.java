package com.example.workintech.ecomm.dto;

import java.util.List;

public record ProductResponse(Long id, String name, String description, Long category_id, Double price, Double rating, Integer sell_count, Integer stock, Long store_id, List<ProductImagesResponse> images) {
}
