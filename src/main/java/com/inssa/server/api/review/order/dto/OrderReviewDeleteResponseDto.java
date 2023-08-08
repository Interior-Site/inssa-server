package com.inssa.server.api.review.order.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class OrderReviewDeleteResponseDto {

    @Schema(description = "삭제된 견적 후기 번호")
    private final Long orderReviewNo;
}
