package com.inssa.server.api.image;

import com.inssa.server.api.image.model.Image;
import com.inssa.server.api.image.service.ImageService;
import com.inssa.server.common.response.InssaApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "image", description = "이미지 API")
@RequestMapping("/api/v1/image")
@RequiredArgsConstructor
@Slf4j
public class ImageController {
    private final ImageService imageService;

    @Operation(summary = "이미지 업로드 API", tags = "image")
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE) // 들어오는 데이터 타입 지정
    public InssaApiResponse<List<Image>> uploadImage(@RequestPart(name = "file")List<MultipartFile> files) {
        return InssaApiResponse.ok(imageService.uploadAndSave(files));
    }
}
