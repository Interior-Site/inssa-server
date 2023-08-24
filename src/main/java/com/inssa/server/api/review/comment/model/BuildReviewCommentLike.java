package com.inssa.server.api.review.comment.model;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class BuildReviewCommentLike extends ReviewCommentLike {
    @ManyToOne(optional = false)
    @JoinColumn(name = "comment_no", updatable = false)
    private BuildReviewComment reviewComment;
}