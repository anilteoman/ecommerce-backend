package com.example.workintech.ecomm.service;



import com.example.workintech.ecomm.dto.CategoryResponse;

import java.util.List;

public interface CategoryService {
    List<CategoryResponse> getCategories();
}
