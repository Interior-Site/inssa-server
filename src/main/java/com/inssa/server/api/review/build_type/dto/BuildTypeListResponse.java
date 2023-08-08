package com.inssa.server.api.review.build_type.dto;

import com.inssa.server.api.review.build_type.model.BuildType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class BuildTypeListResponse {
    private final Long no;
    private final String name;

    public BuildTypeListResponse(BuildType buildType) {
        this.no = buildType.getNo();
        this.name = buildType.getName();
    }
}
