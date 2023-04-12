package com.inssa.server.api.image.service;

import com.inssa.server.api.image.dao.ImageDao;
import com.inssa.server.api.image.dto.ImageDto;
import com.inssa.server.common.FileUploader;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service("ImageService")
@RequiredArgsConstructor
public class ImageService {

    private final FileUploader fileUploader;
    private final ImageDao imageDao;

    public List<ImageDto> uploadAndSave(List<MultipartFile> files) {
        List images = new ArrayList();

        for (MultipartFile file : files) {
            Resource resource = file.getResource();
            String uploadPath = fileUploader.upload(resource);
            ImageDto image = new ImageDto(uploadPath);
            imageDao.saveImage(image);
            images.add(image);
        }

        return images;
    }
}
