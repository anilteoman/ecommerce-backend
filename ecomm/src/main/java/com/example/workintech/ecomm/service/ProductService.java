package com.example.workintech.ecomm.service;


import com.example.workintech.ecomm.dto.ProductFilterRequest;
import com.example.workintech.ecomm.dto.ProductListResponse;
import com.example.workintech.ecomm.dto.ProductResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    ProductListResponse getProducts();
    ProductListResponse getFilteredProducts(ProductFilterRequest productFilterRequest);
    ProductResponse getProductById(Long id);
    ProductListResponse getBestSellerProducts(Integer limit);
}
