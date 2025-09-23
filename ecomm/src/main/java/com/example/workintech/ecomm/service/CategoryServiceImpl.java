package com.example.workintech.ecomm.service;


import com.example.workintech.ecomm.dto.CategoryResponse;
import com.example.workintech.ecomm.mapper.CategoryMapper;
import com.example.workintech.ecomm.repository.CategoryRepository;
import com.example.workintech.ecomm.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private final CategoryRepository categoryRepository;

    @Autowired
    private final CategoryMapper categoryMapper;

    @Override
    public List<CategoryResponse> getCategories() {
        return categoryRepository.findAll().stream().map(categoryMapper::toResponse).toList();
    }
}
