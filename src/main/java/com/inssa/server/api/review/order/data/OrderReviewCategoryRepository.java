package com.inssa.server.api.review.order.data;

import com.inssa.server.api.review.order.model.OrderReviewCategory;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrderReviewCategoryRepository extends JpaRepository<OrderReviewCategory, Long> {
}