package com.inssa.server.api.review.order.dto;

import com.inssa.server.api.review.order.model.OrderReview;
import lombok.Getter;

@Getter
public class OrderReviewCreateResponseDto {
    private final Long orderReviewNo;

    public OrderReviewCreateResponseDto(OrderReview orderReview) {
        this.orderReviewNo = orderReview.getNo();
    }
}
