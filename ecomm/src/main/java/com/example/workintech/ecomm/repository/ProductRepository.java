package com.example.workintech.ecomm.repository;


import com.example.workintech.ecomm.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("""
            SELECT p FROM Product p
            WHERE (:categoryId IS NULL OR p.category.id = :categoryId)
            AND (:filter IS NULL OR :filter = '' OR LOWER(p.name) LIKE LOWER(CONCAT('%', :filter, '%')))
            """)
    Page<Product> findFilteredProducts(@Param("categoryId") Long categoryId, @Param("filter") String filter, Pageable pageable);

    @Query("SELECT p FROM Product p ORDER BY p.sellCount DESC")
    List<Product> findProductsBySellCount(Pageable pageable);
}
