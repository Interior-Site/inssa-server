package com.inssa.server.api.review.order.dto;

import com.inssa.server.api.review.order.model.OrderReview;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class OrderReviewUpdateResponseDto {
    private final Long orderReviewNo;

    public OrderReviewUpdateResponseDto(OrderReview orderReview) {
        this.orderReviewNo = orderReview.getNo();
    }
}
