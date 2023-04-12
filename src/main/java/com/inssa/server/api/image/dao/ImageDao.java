package com.inssa.server.api.image.dao;

import com.inssa.server.api.image.dto.ImageDto;
import com.inssa.server.api.image.mapper.ImageMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ImageDao {
    private final ImageMapper imageMapper;

    public int saveImage(ImageDto image) {
        return imageMapper.saveImage(image);
    }
}
