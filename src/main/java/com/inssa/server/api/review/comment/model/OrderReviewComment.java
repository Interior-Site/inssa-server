package com.inssa.server.api.review.comment.model;

import com.inssa.server.api.review.order.model.OrderReview;
import com.inssa.server.api.user.model.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class OrderReviewComment extends ReviewComment<OrderReview, OrderReviewComment> {

    @OneToMany(mappedBy = "parent", fetch = FetchType.EAGER, orphanRemoval = true)
    private final List<OrderReviewComment> children = new ArrayList<>();

    @OneToMany(mappedBy = "reviewComment", fetch = FetchType.EAGER, orphanRemoval = true)
    private final List<OrderReviewCommentLike> likes = new ArrayList<>();

    public int getLikeCount() {
        return likes.size();
    }

    @Builder
    public OrderReviewComment(String content, User user, OrderReview review, OrderReviewComment parent) {
        super(content, user, review, parent);
    }

}
