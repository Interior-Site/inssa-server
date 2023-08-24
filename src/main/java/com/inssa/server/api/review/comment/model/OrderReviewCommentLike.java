package com.inssa.server.api.review.comment.model;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class OrderReviewCommentLike extends ReviewCommentLike {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_no", nullable = false)
    private OrderReviewComment reviewComment;
}