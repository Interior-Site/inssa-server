package com.inssa.server.api.review.order.model;

import com.inssa.server.api.review.build_type.model.BuildType;
import com.inssa.server.share.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Entity
public class OrderReviewBuildType extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_build_no")
    private Long no;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orderReviewNo", insertable = false, updatable = false, nullable = false)
    private OrderReview orderReview;
    private Long orderReviewNo;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BuildTypeNo", insertable = false, updatable = false, nullable = false)
    private BuildType buildType;
    private Long buildTypeNo;

    @Builder
    public OrderReviewBuildType(OrderReview orderReview, BuildType buildType) {
        this.orderReviewNo = orderReview.getNo();
        this.buildTypeNo = buildType.getNo();
    }
}
