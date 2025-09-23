package com.example.workintech.ecomm.controller;


import com.example.workintech.ecomm.dto.ProductFilterRequest;
import com.example.workintech.ecomm.dto.ProductListResponse;
import com.example.workintech.ecomm.dto.ProductResponse;
import com.example.workintech.ecomm.service.ProductService;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;


    @GetMapping("/products")
    public ProductListResponse getFilteredProducts(@RequestParam(required = false) String filter,
                                                   @RequestParam(required = false) Long category,
                                                   @RequestParam(required = false) String sort,
                                                   @RequestParam(required = false, defaultValue = "asc") String direction,
                                                   @RequestParam(defaultValue = "24") Integer limit,
                                                   @RequestParam(defaultValue = "0") Integer offset) {
        ProductFilterRequest request = new ProductFilterRequest(filter, category, sort, direction, limit, offset);
        return productService.getFilteredProducts(request);
    }

    @GetMapping("/products/{id}")
    public ProductResponse getProductById(@Positive @PathVariable("id") Long id) {
        return productService.getProductById(id);
    }

    @GetMapping("products/bestsellers")
    public ProductListResponse getBestSellers(@RequestParam(defaultValue = "10") Integer limit) {
        return productService.getBestSellerProducts(limit);
    }
}
