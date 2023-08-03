package com.inssa.server.api.review.order.model;

import com.inssa.server.api.review.build_type.model.BuildType;
import com.inssa.server.share.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class OrderReviewBuildType extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_build_no")
    private Long no;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_no", insertable = false, updatable = false, nullable = false)
    private OrderReview orderReview;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "build_type_no", insertable = false, updatable = false, nullable = false)
    private BuildType buildType;
}
