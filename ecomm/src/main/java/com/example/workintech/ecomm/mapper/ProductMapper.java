package com.example.workintech.ecomm.mapper;


import com.example.workintech.ecomm.dto.ProductImagesResponse;
import com.example.workintech.ecomm.dto.ProductResponse;
import com.example.workintech.ecomm.entity.Product;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductMapper {
    public ProductResponse toResponse(Product product) {

        List<ProductImagesResponse> productImagesResponses = product.getImages().stream().map(image -> new ProductImagesResponse(image.getUrl(), image.getIndex())).toList();

        return new ProductResponse(product.getId(), product.getName(), product.getDescription(), product.getCategory().getId(), product.getPrice(), product.getRating(), product.getSellCount(), product.getStock(), product.getStoreId(), productImagesResponses);
    }
}
