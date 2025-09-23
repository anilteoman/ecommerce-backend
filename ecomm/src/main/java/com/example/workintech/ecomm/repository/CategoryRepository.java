package com.example.workintech.ecomm.repository;


import com.example.workintech.ecomm.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
