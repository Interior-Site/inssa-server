package com.inssa.server.api.review.order.dto;

import com.inssa.server.api.review.build_type.model.BuildType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema(description = "건물 유형 Response")
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

    public BuildTypeResponseDto(BuildType buildType) {
        this.no = buildType.getNo();
        this.name = buildType.getName();
    }
}
