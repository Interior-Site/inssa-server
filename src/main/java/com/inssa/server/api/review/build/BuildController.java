package com.inssa.server.api.review.build;

import com.inssa.server.api.review.build.dto.BuildRequestDto;
import com.inssa.server.api.review.build.dto.BuildUpdateRequestDto;
import com.inssa.server.api.review.build.service.BuildService;
import com.inssa.server.api.user.model.AuthUser;
import com.inssa.server.common.annotation.PreAuthorizeLogInUser;
import com.inssa.server.common.response.InssaApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/review")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "buildReview", description = "시공후기 API")
public class BuildController {

    private final BuildService buildService;

    @Operation(summary = "시공후기 목록조회 API", tags = "buildReview")
    @GetMapping("/builds")
    public InssaApiResponse buildList(){

        return InssaApiResponse.ok(buildService.selectList());
    }

    @Operation(summary = "시공후기 상세조회 API", tags = "buildReview")
    @GetMapping("/build")
    public InssaApiResponse buildDetail(@RequestParam int buildNo){

        return InssaApiResponse.ok(buildService.selectDetail(buildNo));
    }


    @Operation(summary = "시공후기 작성 API", tags = "buildReview")
    @PreAuthorizeLogInUser
    @PostMapping("/build")
    public InssaApiResponse insertBuild(@RequestBody BuildRequestDto request, @AuthenticationPrincipal AuthUser user){

        return InssaApiResponse.ok(buildService.insertBuild(request, user.getUserNo()));
    }

    @Operation(summary = "시공후기 수정 API", tags = "buildReview")
    @PutMapping("/build")
    public InssaApiResponse updateBuild(@RequestBody BuildUpdateRequestDto buildUpdateDto, @AuthenticationPrincipal AuthUser user){

        return InssaApiResponse.ok(buildService.updateBuild(buildUpdateDto, user.getUserNo()));
    }

    @Operation(summary = "시공후기 삭제 API", tags = "buildReview")
    @PutMapping("/build/{buildNo}")
    public InssaApiResponse deleteBuild(@PathVariable int buildNo, @AuthenticationPrincipal AuthUser user){

        return InssaApiResponse.ok(buildService.deleteBuild(buildNo, user.getUserNo()));
    }
}
