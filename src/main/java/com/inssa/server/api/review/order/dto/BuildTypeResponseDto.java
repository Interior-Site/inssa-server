package com.inssa.server.api.review.order.dto;

import com.inssa.server.api.review.build_type.model.BuildType;
import lombok.Getter;

@Getter
public class BuildTypeResponseDto {
    private Long no;
    private String name;

    public BuildTypeResponseDto(BuildType buildType) {
        this.no = buildType.getNo();
        this.name = buildType.getName();
    }
}
