package com.inssa.server.api.review.order.model;

import com.inssa.server.common.entity.BaseTimeEntity;
import com.inssa.server.common.entity.BuildType;
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
