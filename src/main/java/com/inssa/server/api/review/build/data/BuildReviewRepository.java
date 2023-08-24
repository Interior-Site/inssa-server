package com.inssa.server.api.review.build.data;

import com.inssa.server.api.review.build.model.BuildReview;
import com.inssa.server.share.board.BoardStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BuildReviewRepository extends JpaRepository<BuildReview, Long> {
    Optional<BuildReview> findByStatusAndNo(BoardStatus status, Long reviewNo);
}
