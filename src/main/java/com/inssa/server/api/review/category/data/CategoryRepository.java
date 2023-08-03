package com.inssa.server.api.review.category.data;

import com.inssa.server.api.review.category.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
