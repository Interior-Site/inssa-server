package com.inssa.server.api.image.mapper;

import com.inssa.server.api.image.dto.ImageDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ImageMapper {
    int saveImage(ImageDto image);
}
