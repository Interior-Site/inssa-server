package com.inssa.server.api.review.order.dto;

import com.inssa.server.api.review.order.model.OrderReview;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class OrderReviewCreateResponseDto {

    @Schema(
            description = "생성된 견적 후기 번호",
            type = "Long",
            example = "5"
    )
    private final Long orderReviewNo;

    public OrderReviewCreateResponseDto(OrderReview orderReview) {
        this.orderReviewNo = orderReview.getNo();
    }
}
