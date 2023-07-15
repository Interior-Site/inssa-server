package com.inssa.server.api.image.service;

import com.inssa.server.api.image.data.ImageRepository;
import com.inssa.server.api.image.model.Image;
import com.inssa.server.common.file.FileUploader;
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
    private final ImageRepository imageRepository;

    public List<Image> uploadAndSave(List<MultipartFile> files) {
        List images = new ArrayList();

        for (MultipartFile file : files) {
            Resource resource = file.getResource();
            String uploadPath = fileUploader.upload(resource);

            Image image = saveImageInfo(uploadPath, resource.getFilename());
            images.add(image);
        }

        return images;
    }

    private Image saveImageInfo(String uploadPath, String filename) {
        return imageRepository.save(new Image(uploadPath, filename));
    }
}
