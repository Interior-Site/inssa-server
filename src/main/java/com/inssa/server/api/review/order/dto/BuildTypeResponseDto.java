package com.inssa.server.api.review.order.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Schema(description = "건물 유형 Response")
@RequiredArgsConstructor
@Getter
public class BuildTypeResponseDto {
    @Schema(
            description = "건물 유형 번호",
            type = "Long",
            example = "1"
    )
    private final Long no;

    @Schema(
            description = "건물 유형 이름",
            type = "String",
            example = "아파트"
    )
    private final String name;
}
