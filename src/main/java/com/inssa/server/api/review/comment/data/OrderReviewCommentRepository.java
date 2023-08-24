package com.inssa.server.api.review.comment.data;

import com.inssa.server.api.review.comment.model.OrderReviewComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface OrderReviewCommentRepository extends JpaRepository<OrderReviewComment, Long> {
    Page<OrderReviewComment> findByParentNullAndDeletedFalseAndReviewNo(Long reviewNo, Pageable pageable);

    Optional<OrderReviewComment> findByDeletedFalseAndNo(Long no);
}
