package com.inssa.server.api.review.build;

import com.inssa.server.api.review.build.dto.BuildDto;
import com.inssa.server.api.review.build.dto.BuildUpdateDto;
import com.inssa.server.api.review.build.service.BuildService;
import com.inssa.server.api.user.model.AuthUser;
import com.inssa.server.common.exception.InssaException;
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
    public InssaApiResponse buildDetail(@RequestParam int buildNo){

        return InssaApiResponse.ok(buildService.selectDetail(buildNo));
    }


    @Operation(summary = "시공후기 작성 API", tags = "buildReview")
    @PutMapping("/buildInsert")
    public InssaApiResponse insertBuild(@RequestBody BuildDto request, @AuthenticationPrincipal AuthUser user){
      //  if(user == null){
      //      throw new InssaException("로그인 후 이용 가능합니다.");
      //  }
        return InssaApiResponse.ok(buildService.insertBuild(request, Long.parseLong(user.getUsername())));
    }

    @Operation(summary = "시공후기 수정 API", tags = "buildReview")
    @PostMapping("/buildUpdate")
    public InssaApiResponse updateBuild(@RequestBody BuildUpdateDto buildUpdateDto, @AuthenticationPrincipal AuthUser user){

        return InssaApiResponse.ok(buildService.updateBuild(buildUpdateDto, Long.parseLong(user.getUsername())));
    }

    @Operation(summary = "시공후기 삭제 API", tags = "buildReview")
    @PostMapping("/buildDelete")
    public InssaApiResponse deleteBuild(@RequestParam int buildNo, @AuthenticationPrincipal AuthUser user){

        return InssaApiResponse.ok(buildService.deleteBuild(buildNo, Long.parseLong(user.getUsername())));
    }
}
