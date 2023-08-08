package com.inssa.server.api.review.order.dto;

import com.inssa.server.api.review.order.model.OrderReview;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class OrderReviewUpdateResponseDto {

    @Schema(description = "수정된 견적 후기 번호")
    private final Long orderReviewNo;

    public OrderReviewUpdateResponseDto(OrderReview orderReview) {
        this.orderReviewNo = orderReview.getNo();
    }
}
