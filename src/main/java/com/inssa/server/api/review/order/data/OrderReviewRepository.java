package com.inssa.server.api.review.order.data;

import com.inssa.server.api.review.order.model.OrderReview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderReviewRepository extends JpaRepository<OrderReview, Long>, OrderReviewCustomRepository {
}
