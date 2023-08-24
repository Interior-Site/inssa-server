package com.inssa.server.api.review.comment.data;

import com.inssa.server.api.review.comment.model.OrderReviewCommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderReviewCommentLikeRepository extends JpaRepository<OrderReviewCommentLike, Long> {
    boolean existsByUserNoAndReviewCommentNo(Long userNo, Long commentNo);
}