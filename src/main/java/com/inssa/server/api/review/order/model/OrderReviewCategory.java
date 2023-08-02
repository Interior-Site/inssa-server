package com.inssa.server.api.review.order.model;

import com.inssa.server.common.entity.BaseTimeEntity;
import com.inssa.server.common.entity.Category;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class OrderReviewCategory extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_category_no")
    private Long no;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_no", insertable = false, updatable = false, nullable = false)
    private OrderReview orderReview;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_no", insertable = false, updatable = false, nullable = false)
    private Category category;
}
