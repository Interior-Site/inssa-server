package com.inssa.server.api.review.comment.model;

import com.inssa.server.api.review.order.model.OrderReview;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
public class OrderReviewComment extends ReviewComment<OrderReview, OrderReviewComment> {
    @OneToMany(mappedBy = "parent", fetch = FetchType.EAGER, orphanRemoval = true)
    private List<OrderReviewComment> children = new ArrayList<>();
}
