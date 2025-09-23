package com.example.workintech.ecomm.mapper;


import com.example.workintech.ecomm.dto.CategoryResponse;
import com.example.workintech.ecomm.entity.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {
    public CategoryResponse toResponse(Category category) {
        return new CategoryResponse(category.getId(), category.getCode(), category.getTitle(), category.getImg(), category.getRating(), category.getGender());
    }
}
