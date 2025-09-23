package com.example.workintech.ecomm.service;


import com.example.workintech.ecomm.dto.ProductFilterRequest;
import com.example.workintech.ecomm.dto.ProductListResponse;
import com.example.workintech.ecomm.dto.ProductResponse;
import com.example.workintech.ecomm.entity.Product;
import com.example.workintech.ecomm.mapper.ProductMapper;
import com.example.workintech.ecomm.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{

    @Autowired
    private final ProductRepository productRepository;

    @Autowired
    private final ProductMapper productMapper;

    @Override
    public ProductListResponse getProducts() {
        Pageable pageable = PageRequest.of(0, 24, Sort.by(Sort.Direction.ASC, "id"));

        Page<Product> page = productRepository.findAll(pageable);

        List<ProductResponse> productResponses = page.getContent().stream().map(productMapper::toResponse).toList();

        return new ProductListResponse(productResponses, page.getTotalElements());
    }

    @Override
    public ProductListResponse getFilteredProducts(ProductFilterRequest request) {
        String sortParam = request.sort();
        String sortField = "id";
        Sort.Direction direction = Sort.Direction.ASC;

        if(sortParam != null) {
            String[] parts = sortParam.split(":");
            sortField = parts[0];
            if(parts.length > 1) {
                direction = Sort.Direction.fromString(parts[1]);
            }
        } else if(request.direction() != null) {
            direction = Sort.Direction.fromString(request.direction());
        }

        Sort sort = Sort.by(direction, sortField);
        int limit = request.limit() != null ? request.limit() : 24;
        int offset = request.offset() != null ? request.offset() : 0;
        int pageNumber = offset / limit;

        Pageable pageable = PageRequest.of(pageNumber, limit, sort);

        Page<Product> page = productRepository.findFilteredProducts(request.categoryId(), request.filter(), pageable);

        List<ProductResponse> productResponses = page.getContent().stream().map(productMapper::toResponse).toList();

        return new ProductListResponse(productResponses, page.getTotalElements());
    }

    @Override
    public ProductResponse getProductById(Long id) {
        Optional<Product> productOptional = productRepository.findById(id);
        if(productOptional.isPresent()) {
            return productMapper.toResponse(productOptional.get());
        }
        return null;
    }

    @Override
    public ProductListResponse getBestSellerProducts(Integer limit) {
        Pageable pageable = PageRequest.of(0, limit, Sort.by(Sort.Direction.DESC, "sellCount"));
        Page<Product> page = productRepository.findAll(pageable);
        List<Product> products = productRepository.findAll(pageable).getContent();
        List<ProductResponse> productResponses = products.stream().map(productMapper::toResponse).collect(Collectors.toList());
        long total = page.getTotalElements();
        return new ProductListResponse(productResponses, total);
    }
}
