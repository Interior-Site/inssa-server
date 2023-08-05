package com.inssa.server.api.review.build.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "시공후기 등록 API request")
public class BuildCreateRequestDto {
    private Long buildType;
    private Long categoryNo;
    private String title;
    private String content;
}
