package com.inssa.server.api.review.like.model;

import com.inssa.server.api.review.order.model.OrderReview;
import com.inssa.server.api.user.model.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Getter
@Entity
@Table(name = "OrderReviewLike",
        uniqueConstraints = {
            @UniqueConstraint(name = "uc_orderreviewlike_user_no", columnNames = {"user_no", "order_review_no"}
        )
})
public class OrderReviewLike extends ReviewLike {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_review_no", nullable = false, updatable = false)
    private OrderReview orderReview;

    @Builder
    public OrderReviewLike(Long id, User user, OrderReview orderReview) {
        super(id, user);
        this.orderReview = orderReview;
    }

}