package com.inssa.server.api.review.build.controller;

import com.inssa.server.api.review.build.service.BuildService;
import com.inssa.server.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/review")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "buildReview", description = "시공후기 API")
public class BuildController {

    private final BuildService buildService;

    @Operation(summary = "buildList", description = "시공후기 목록 조회 API") // 스웨거
    @GetMapping("/buildList")
    //@GetMapping(value = "/selectList/{boardNo}")
    public ApiResponse buildList(){
        return buildService.selectList();
    }

}
