package com.inssa.server.api.review.build.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BuildListResponseDto {

    private Long buildNo;
    private String title;
    private String content;
    private int viewCount;
    private Long userNo;
}
