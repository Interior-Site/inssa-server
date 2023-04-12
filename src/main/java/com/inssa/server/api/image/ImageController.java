package com.inssa.server.api.image;

import com.inssa.server.api.image.dto.ImageDto;
import com.inssa.server.api.image.service.ImageService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/image")
@RequiredArgsConstructor
@Slf4j
public class ImageController {
    private final ImageService imageService;

    @Operation(summary = "upload", description = "이미지 업로드 API")
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE) // 들어오는 데이터 타입 지정
    public List<ImageDto> uploadImage(@RequestPart(name = "file")List<MultipartFile> files) {
        return imageService.uploadAndSave(files);
    }
}
