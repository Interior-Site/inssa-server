package com.inssa.server.api.image.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ImageResponseDto {
    private final Long no;
    private final String imgUrl;
    private final String originFileName;
}
