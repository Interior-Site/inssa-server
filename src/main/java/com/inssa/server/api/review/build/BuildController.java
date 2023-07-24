package com.inssa.server.api.review.build;

import com.inssa.server.api.review.build.dto.BuildDto;
import com.inssa.server.api.review.build.dto.BuildUpdateDto;
import com.inssa.server.api.review.build.service.BuildService;
import com.inssa.server.api.user.model.AuthUser;
import com.inssa.server.common.response.ApiResponse;
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
    @GetMapping("/buildList")
    //@GetMapping(value = "/selectList/{boardNo}")
    public InssaApiResponse buildList(){

        return InssaApiResponse.ok(buildService.selectList());
    }

    @Operation(summary = "시공후기 상세조회 API", tags = "buildReview")
    @GetMapping("/buildDetail/{buildNo}")
    public ApiResponse buildDetail(@RequestParam int buildNo){
        return buildService.selectDetail(buildNo);
    }


    @Operation(summary = "시공후기 작성 API", tags = "buildReview")
    @PutMapping("/buildInsert")
    public ApiResponse insertBuild(@RequestBody BuildDto build){

        return buildService.insertBuild(build);
    }

    @Operation(summary = "시공후기 수정 API", tags = "buildReview")
    @PostMapping("/buildUpdate")
    public ApiResponse updateBuild(@RequestBody BuildUpdateDto buildUpdateDto, @AuthenticationPrincipal AuthUser user){

        return buildService.updateBuild(buildUpdateDto, Long.parseLong(user.getUsername()));
    }

    @Operation(summary = "시공후기 삭제 API", tags = "buildReview")
    @PostMapping("/buildDelete")
    public ApiResponse deleteBuild(@RequestParam int buildNo, @AuthenticationPrincipal AuthUser user){

        return buildService.deleteBuild(buildNo, Long.parseLong(user.getUsername()));
    }
}
