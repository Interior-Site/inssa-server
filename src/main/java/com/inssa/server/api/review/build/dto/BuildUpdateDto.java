package com.inssa.server.api.review.build.dto;

import lombok.Data;

@Data
public class BuildUpdateDto {
    public int buildNo;
    public String title;
    public String content;

}
