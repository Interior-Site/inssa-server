package com.inssa.server.api.review.comment.data;

import com.inssa.server.api.review.comment.model.BuildReviewCommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuildReviewCommentLikeRepository extends JpaRepository<BuildReviewCommentLike, Long> {
    boolean existsByUserNoAndReviewCommentNo(Long userNo, Long commentNo);
}
