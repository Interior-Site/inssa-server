package com.inssa.server.api.review.like.data;

import com.inssa.server.api.review.like.model.BuildReviewLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BuildReviewLikeRepository extends JpaRepository<BuildReviewLike, Long> {
    boolean existsByUserNoAndBuildReviewNo(Long userNo, Long buildReviewNo);
    Optional<BuildReviewLike> findByUserNoAndBuildReviewNo(Long userNo, Long buildReviewNo);
}
