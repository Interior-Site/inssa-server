package com.inssa.server.api.review.build.dto;

import lombok.Data;

@Data
public class BuildUpdateDto {
    private int buildNo;
    private String title;
    private String content;

}
