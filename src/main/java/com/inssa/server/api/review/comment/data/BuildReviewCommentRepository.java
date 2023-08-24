package com.inssa.server.api.review.comment.data;

import com.inssa.server.api.review.comment.model.BuildReviewComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuildReviewCommentRepository extends JpaRepository<BuildReviewComment, Long> {
    Page<BuildReviewComment> findByParentNullAndDeletedFalseAndReviewNo(Long reviewNo, Pageable pageable);
}
