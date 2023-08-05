package com.inssa.server.api.review.order.model;

import com.inssa.server.api.review.category.model.Category;
import com.inssa.server.share.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Entity
public class OrderReviewCategory extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_category_no")
    private Long no;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orderReviewNo", insertable = false, updatable = false, nullable = false)
    private OrderReview orderReview;
    private Long orderReviewNo;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoryNo", insertable = false, updatable = false, nullable = false)
    private Category category;
    private Long categoryNo;

    @Builder
    public OrderReviewCategory(OrderReview orderReview, Category category) {
        this.orderReview = orderReview;
        this.category = category;
    }
}
