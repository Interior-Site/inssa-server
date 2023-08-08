package com.inssa.server.api.review.order.data;

import com.inssa.server.api.review.order.model.OrderReviewBuildType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderReviewBuildTypeRepository extends JpaRepository<OrderReviewBuildType, Long> {
}