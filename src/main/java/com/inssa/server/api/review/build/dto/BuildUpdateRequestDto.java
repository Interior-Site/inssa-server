package com.inssa.server.api.review.build.dto;

import lombok.Data;

@Data
public class BuildUpdateRequestDto {
    private int buildNo;
    private String title;
    private String content;

}
