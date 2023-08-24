package com.inssa.server.api.review.order.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Schema(description = "시공 유형 Response")
@RequiredArgsConstructor
@Getter
public class CategoryResponseDto {

    @Schema(
            description = "시공 유형 번호",
            type = "Long",
            example = "1"
    )
    private final Long no;

    @Schema(
            description = "시공 유형 이름",
            type = "String",
            example = "도배"
    )
    private final String name;
}
