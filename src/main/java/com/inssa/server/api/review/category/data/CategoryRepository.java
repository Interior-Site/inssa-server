package com.inssa.server.api.review.category.data;

import com.inssa.server.api.review.category.model.Category;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Cacheable("categoryIds")
    List<Category> findAll();
}
