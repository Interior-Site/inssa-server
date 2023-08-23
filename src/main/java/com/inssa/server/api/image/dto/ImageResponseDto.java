package com.inssa.server.api.image.dto;

import com.inssa.server.api.image.model.Image;
import lombok.Getter;

@Getter
public class ImageResponseDto {
    private final Long no;
    private final String imgUrl;
    private final String originFileName;

    public ImageResponseDto(Image image) {
        this.no = image.getId();
        this.imgUrl = image.getImgUrl();
        this.originFileName = image.getOriginFileName();
    }
}
