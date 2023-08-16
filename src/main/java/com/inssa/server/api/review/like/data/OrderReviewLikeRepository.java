package com.inssa.server.api.review.like.data;

import com.inssa.server.api.review.like.model.OrderReviewLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderReviewLikeRepository extends JpaRepository<OrderReviewLike, Long> {
    Long countByUserNoAndOrderReviewNo(Long userNo, Long orderReviewNo);
    Optional<OrderReviewLike> findByUserNoAndOrderReviewNo(Long userNo, Long orderReviewNo);
}
