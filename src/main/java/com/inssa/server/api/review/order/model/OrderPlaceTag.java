package com.inssa.server.api.review.order.model;

import com.inssa.server.common.entity.BaseTimeEntity;
import com.inssa.server.common.entity.PlaceType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class OrderPlaceTag extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_place_tag_no")
    private Long no;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_review_no", insertable = false, updatable = false, nullable = false)
    private OrderReview orderReview;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_type_no", insertable = false, updatable = false, nullable = false)
    private PlaceType placeType;
}
